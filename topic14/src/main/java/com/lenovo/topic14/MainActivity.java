package com.lenovo.topic14;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic14.bean.AllStepBean;
import com.lenovo.topic14.bean.CustomerBean;
import com.lenovo.topic14.bean.ProductionLineBean;
import com.lenovo.topic14.bean.ProductionResultMessage;
import com.lenovo.topic14.bean.UserPlStepBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private ListView lv_left;
    private ListView lv_right;
    private ApiService remote;
    private ProductionLineBean.DataBean productionLineBean;
    private List<AllStepBean.DataBean> allStepBeans;
    private ArrayList<CustomerBean> customerBeans;
    private ArrayList<CustomerBean> customerLeftBean;
    private ArrayList<CustomerBean> customerRightBean;
    private CustomerAdapter leftAdapter;
    private CustomerAdapter rightAdapter;

    /**
     * @return 获取资源文件ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (ListView) findViewById(R.id.lv_right);
    }

    /**
     * 初始化控件的监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(v -> MainActivity.this.closeActivity());
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        // 实例化ApiService
        remote = Network.remote(ApiService.class);

        // 初始化生产线对象
        productionLineBean = new ProductionLineBean.DataBean();

        // 初始化填充数据的集合
        customerBeans = new ArrayList<>();
        customerLeftBean = new ArrayList<>();
        customerRightBean = new ArrayList<>();

        // 初始化ListView
        leftAdapter = new CustomerAdapter();
        lv_left.setAdapter(leftAdapter);
        rightAdapter = new CustomerAdapter();
        lv_right.setAdapter(rightAdapter);
        // 获取第四条生产线的数据
        getProductionLine();
    }

    /**
     * 获取指定位置的生产线
     */
    @SuppressLint("CheckResult")
    private void getProductionLine() {
        remote.getProductionLine(3)
                // 绑定生命周期
                .compose(this.bindToLifecycle())
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 数据转换处理
                .map(new Function<ProductionLineBean, List<ProductionLineBean.DataBean>>() {
                    @Override
                    public List<ProductionLineBean.DataBean> apply(ProductionLineBean productionLineBean) throws Exception {
                        return productionLineBean.getData();
                    }
                })
                // 切换到主线程填充数据刷新UI
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅回调
                .subscribe(new Consumer<List<ProductionLineBean.DataBean>>() {
                    @Override
                    public void accept(List<ProductionLineBean.DataBean> dataBeans) throws Exception {
                        // 判断生产线是否存在
                        if (dataBeans.size() == 0) {
                            // 重新创建生产线
                            createProductionLine();
                        } else {
                            // 将当前生产线的数据取出
                            productionLineBean = dataBeans.get(0);
                            // 获取所有生产环节信息
                            getAllStep();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 查询全部生产环节信息
     */
    @SuppressLint("CheckResult")
    private void getAllStep() {
        remote.getAllStep()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<AllStepBean, List<AllStepBean.DataBean>>() {
                    @Override
                    public List<AllStepBean.DataBean> apply(AllStepBean allStepBean) throws Exception {
                        return allStepBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AllStepBean.DataBean>>() {
                    @Override
                    public void accept(List<AllStepBean.DataBean> dataBeans) throws Exception {
                        allStepBeans = dataBeans;
                        // 获取当前生产线的生产环节信息
                        getUserPlStep();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 查询当前生产线的生产环节信息
     */
    @SuppressLint("CheckResult")
    private void getUserPlStep() {
        remote.getUserPlStep(productionLineBean.getId())
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<UserPlStepBean, List<CustomerBean>>() {
                    @Override
                    public List<CustomerBean> apply(UserPlStepBean userPlStepBean) throws Exception {
                        // 合并数据
                        for (UserPlStepBean.DataBean datum : userPlStepBean.getData()) {
                            CustomerBean customerBean = new CustomerBean();
                            // 判断生产线的环节是否是当前环节
                            if (productionLineBean.getStageId() + 1 == datum.getNextUserPlStepId()) {
                                customerBean.setBackground("#3692E9");
                            } else {
                                customerBean.setBackground("#FFFFFF");
                            }
                            for (AllStepBean.DataBean allStepBean : allStepBeans) {
                                if (datum.getId() == allStepBean.getId()) {
                                    // 名称
                                    customerBean.setPlName(allStepBean.getPlStepName().replace(allStepBean.getStep() + "", ""));
                                    // 耐久度
                                    int progress = allStepBean.getPower() - 15;
                                    customerBean.setProgress(progress);
                                    // hp
                                    customerBean.setHp("HP:" + progress + "/100");
                                }
                            }
                            customerBeans.add(customerBean);
                        }
                        for (int i = 0; i < 10; i++) {
                            customerLeftBean.add(customerBeans.get(i));
                            customerRightBean.add(customerBeans.get(i + 10));
                        }
                        return customerBeans;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<CustomerBean>>() {
                    @Override
                    public void accept(List<CustomerBean> customerBeans) throws Exception {
                        Log.i(TAG, "请求成功：" + customerBeans.toString());
                        leftAdapter.setBeans(customerLeftBean);
                        rightAdapter.setBeans(customerRightBean);
                        leftAdapter.notifyDataSetChanged();
                        rightAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void createProductionLine() {
        remote.createProductionLine(3, 3)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ProductionResultMessage::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "请求成功：" + s);
                        if (s.equals("创建学生生产线成功")) {
                            // 重新获取生产线的ID
                            getProductionLine();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    class CustomerAdapter extends BaseAdapter {
        private List<CustomerBean> beans = new ArrayList<>();
        private LinearLayout llBackground;
        private TextView tvLinkName;
        private RelativeLayout rlProgressBar;
        private TextView tvSchedule;
        private TextView tvPower;

        @Override
        public int getCount() {
            return beans.size();
        }

        @Override
        public CustomerBean getItem(int position) {
            return beans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item, null);
            } else {
                view = convertView;
            }

            initView(view);
            llBackground.setBackgroundColor(Color.parseColor(getItem(position).getBackground()));
            tvLinkName.setText(getItem(position).getPlName());
            tvPower.setText(getItem(position).getHp());
            // 设置控件的宽度
            ViewGroup.LayoutParams layoutParams = tvSchedule.getLayoutParams();
            layoutParams.width = (int) getItem(position).getProgress();
            tvSchedule.setLayoutParams(layoutParams);
            return view;
        }

        public List<CustomerBean> getBeans() {
            return beans;
        }

        public void setBeans(List<CustomerBean> beans) {
            this.beans = beans;
        }

        private void initView(View view) {
            llBackground = (LinearLayout) view.findViewById(R.id.ll_background);
            tvLinkName = (TextView) view.findViewById(R.id.tv_link_name);
            rlProgressBar = (RelativeLayout) view.findViewById(R.id.rl_progress_bar);
            tvSchedule = (TextView) view.findViewById(R.id.tv_schedule);
            tvPower = (TextView) view.findViewById(R.id.tv_power);
        }
    }

}
