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
import com.lenovo.topic15.bean.AllStageBean;
import com.lenovo.topic15.bean.CustomerBean;
import com.lenovo.topic15.bean.ProductionLineBean;
import com.lenovo.topic15.bean.ProductionLineResultMessage;
import com.lenovo.topic15.bean.ProductionLineStepBean;

import java.util.ArrayList;
import java.util.List;

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
    private List<AllStageBean.DataBean> allStageBeans;
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
        allStageBeans = new ArrayList<>();

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
                // 绑定生命周期
                .compose(this.bindToLifecycle())
                // 切换到子线程进行网路请求
                .subscribeOn(Schedulers.io())
                // 转换提取数据
                .map(new Function<ProductionLineBean, List<ProductionLineBean.DataBean>>() {
                    @Override
                    public List<ProductionLineBean.DataBean> apply(ProductionLineBean productionLineBean) throws Exception {
                        return productionLineBean.getData();
                    }
                })
                // 切换到主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅得到回调方法
                .subscribe(new Consumer<List<ProductionLineBean.DataBean>>() {
                    @Override
                    public void accept(List<ProductionLineBean.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "生产线不存在");
                        if (dataBeans.size() == 0) {
                            Log.i(TAG, "重新创建生产线");
                            createProductionLine();
                        } else {
                            productionLineBean = dataBeans.get(0);
                            Log.i(TAG, "获取所有生产工序信息");
                            getAllStage();
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
     * 获取所有生产工序信息
     */
    @SuppressLint("CheckResult")
    private void getAllStage() {
        remote.getAllStage()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(AllStageBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AllStageBean.DataBean>>() {
                    @Override
                    public void accept(List<AllStageBean.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "获取数据成功");
                        allStageBeans = dataBeans;
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
                        List<ProductionLineStepBean.DataBean> data = productionLineStepBean.getData();
                        int step = data.get(data.size() - 1).getNextUserPlStepId() - 1;
                        for (int i = step; i <= step + 19; i++) {
                            for (AllStageBean.DataBean allStageBean : allStageBeans) {
                                if (allStageBean.getPlStepId() == i) {
                                    CustomerBean e = new CustomerBean();
                                    e.setStepName(allStageBean.getContent());
                                    customerBeans.add(e);
                                }
                            }
                        }
                        for (int i = 0; i < customerBeans.size(); i++) {
                            if (i + 1 >= 10) {
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
                        for (CustomerBean customerBean : customerBeans) {
                            Log.i(TAG, "accept: " + customerBean.toString());
                        }
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
    @SuppressLint("SetTextI18n")
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
        tv_step_name.setText("第" + (currentPosition + 1) + "步" + "-" + name);
        iv_step_icon.setImageResource(getResources().getIdentifier(icon, "drawable", "com.lenovo.topic15"));
        Log.i(TAG, icon);
    }
}
