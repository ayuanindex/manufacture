package com.lenovo.topic15;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic15.bean.AllPlStepBean;
import com.lenovo.topic15.bean.CustomerBean;
import com.lenovo.topic15.bean.ProductionLineBean;
import com.lenovo.topic15.bean.ProductionLineResultMessage;
import com.lenovo.topic15.bean.ProductionLineStepBean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_back;
    private ImageButton btn_previous;
    private ImageButton btn_next;
    private TextView tv_step_name;
    private ImageView iv_step_icon;
    private ApiService remote;
    private ProductionLineBean.DataBean productionLineBean;
    private List<AllPlStepBean.DataBean> allStepInfoBeans;
    private ArrayList<CustomerBean> customerBeans;
    private int currentPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * @return 获取资源文件ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化界面中的布局
     */
    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_previous = (ImageButton) findViewById(R.id.btn_previous);
        btn_next = (ImageButton) findViewById(R.id.btn_next);
        tv_step_name = (TextView) findViewById(R.id.tv_step_name);
        iv_step_icon = (ImageView) findViewById(R.id.iv_step_icon);
    }

    /**
     * 初始化布局控价的监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(this);
        btn_previous.setOnClickListener(this);
        btn_next.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        // 实例化ApiService
        remote = Network.remote(ApiService.class);

        // 初始化当前所在位置
        currentPosition = 0;

        customerBeans = new ArrayList<>();
        allStepInfoBeans = new ArrayList<>();

        // 获取生产线的信息
        getProductionLineBean();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                MainActivity.this.closeActivity();
                break;
            case R.id.btn_previous:
                switchOperation(false);
                break;
            case R.id.btn_next:
                switchOperation(true);
                break;
        }
    }

    /**
     * 获取指定位置的生产线
     */
    @SuppressLint("CheckResult")
    private void getProductionLineBean() {
        remote.getProductionLineIsPosition(3)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<ProductionLineBean, List<ProductionLineBean.DataBean>>() {
                    @Override
                    public List<ProductionLineBean.DataBean> apply(ProductionLineBean productionLineBean) throws Exception {
                        return productionLineBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ProductionLineBean.DataBean>>() {
                    @Override
                    public void accept(List<ProductionLineBean.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "生产线不存在");
                        if (dataBeans.size() == 0) {
                            Log.i(TAG, "重新创建生产线");
                            createProductionLine();
                        } else {
                            productionLineBean = dataBeans.get(0);
                            Log.i(TAG, "获取所欧生产环节信息");
                            getAllStepInfoBean();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误" + throwable.getMessage());
                    }
                });
    }

    /**
     * 获取所有生产环节信息
     */
    @SuppressLint("CheckResult")
    private void getAllStepInfoBean() {
        remote.getAllStepInfo()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(AllPlStepBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AllPlStepBean.DataBean>>() {
                    @Override
                    public void accept(List<AllPlStepBean.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "获取数据成功");
                        allStepInfoBeans = dataBeans;
                        Log.i(TAG, "获取当前说生产线的环节数据");
                        getProductionLineStep();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 获取当前生产线的生产环节数据
     */
    @SuppressLint("CheckResult")
    private void getProductionLineStep() {
        remote.getProductionLineStep(productionLineBean.getId())
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<ProductionLineStepBean, List<CustomerBean>>() {
                    @Override
                    public List<CustomerBean> apply(ProductionLineStepBean productionLineStepBean) throws Exception {
                        for (int i = productionLineStepBean.getData().size() - 1; i >= 0; i--) {
                            for (AllPlStepBean.DataBean allStepInfoBean : allStepInfoBeans) {
                                if (allStepInfoBean.getId() == productionLineStepBean.getData().get(i).getId()) {
                                    CustomerBean e = new CustomerBean();
                                    e.setStepName(allStepInfoBean.getPlStepName());
                                    customerBeans.add(e);
                                    break;
                                }
                            }
                        }
                        for (int i = 0; i < customerBeans.size(); i++) {
                            if (i >= 10) {
                                customerBeans.get(i).setIcon("line0" + productionLineBean.getProductionLineId() + "_" + (i + 1));
                            } else {
                                customerBeans.get(i).setIcon("line0" + productionLineBean.getProductionLineId() + "_0" + (i + 1));
                            }
                        }
                        return customerBeans;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CustomerBean>>() {
                    @Override
                    public void accept(List<CustomerBean> customerBeans) throws Exception {
                        currentPosition = -1;
                        switchOperation(true);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 创建生产线
     */
    @SuppressLint("CheckResult")
    private void createProductionLine() {
        remote.createProductionLine(3, 3)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ProductionLineResultMessage::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, s);
                        getProductionLineBean();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误" + throwable.getMessage());
                    }
                });
    }

    /**
     * 切换显示
     *
     * @param b true表示下一步false表示上一步
     */
    private void switchOperation(boolean b) {
        if (customerBeans.size() == 0) {
            Toast.makeText(this, "正在初始化数据", Toast.LENGTH_SHORT).show();
            return;
        }
        String name = "";
        String icon = "";
        if (b) {
            ++currentPosition;
            currentPosition = Math.min(currentPosition, 19);
            name = customerBeans.get(currentPosition).getStepName();
            icon = customerBeans.get(currentPosition).getIcon();
        } else {
            --currentPosition;
            currentPosition = Math.max(currentPosition, 0);
            name = customerBeans.get(currentPosition).getStepName();
            icon = customerBeans.get(currentPosition).getIcon();
        }
        tv_step_name.setText(name);
        iv_step_icon.setImageResource(getResources().getIdentifier(icon, "drawable", "com.lenovo.topic15"));
        Log.i(TAG, icon);
    }
}
