package com.lenovo.topic09;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic09.bean.AllPeopleBean;
import com.lenovo.topic09.bean.CustomerBean;
import com.lenovo.topic09.bean.ProductionLineBean;
import com.lenovo.topic09.bean.ProductionResultMessage;
import com.lenovo.topic09.bean.UserPeopleBean;
import com.lenovo.topic09.bean.UserWorkEnvironmentalBean;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ImageView iv_back;
    private ListView lv_rest;
    private TextView tv_time;
    private ListView lv_post;
    private ApiService remote;
    private ProductionLineBean.DataBean productionLineBean;
    private List<UserPeopleBean.DataBean> peopleBeans;
    private ArrayList<CustomerBean> customerBeans;
    private ArrayList<CustomerBean> leftBeans;
    private ArrayList<CustomerBean> rightBeans;
    private List<AllPeopleBean.DataBean> allPeopleBeans;
    private CustomerAdapter resetAdapter;
    private CustomerAdapter postAdapter;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_rest = (ListView) findViewById(R.id.lv_rest);
        tv_time = (TextView) findViewById(R.id.tv_time);
        lv_post = (ListView) findViewById(R.id.lv_post);
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(v -> MainActivity.this.closeActivity());
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void initData() {
        // ?????????ApiService
        remote = Network.remote(ApiService.class);

        // ??????????????????
        simpleDateFormat = new SimpleDateFormat("HH:mm");

        peopleBeans = new ArrayList<>();
        allPeopleBeans = new ArrayList<>();

        // ????????????????????????????????????
        customerBeans = new ArrayList<>();
        leftBeans = new ArrayList<>();
        rightBeans = new ArrayList<>();

        // ???????????????
        resetAdapter = new CustomerAdapter();
        lv_rest.setAdapter(resetAdapter);
        postAdapter = new CustomerAdapter();
        lv_post.setAdapter(postAdapter);

        // ????????????????????????
        getWorkEnvironment();

        // ?????????????????????
        getProductionLine();
    }

    /**
     * ?????????????????????????????????
     */
    @SuppressLint("CheckResult")
    private void getWorkEnvironment() {
        Observable.interval(0, 5, TimeUnit.SECONDS)
                .compose(this.bindToLifecycle())
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "???????????????" + aLong);
                        remote.getUserWorkEnvironmental(1)
                                .compose(bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .map(new Function<UserWorkEnvironmentalBean, UserWorkEnvironmentalBean.DataBean>() {
                                    @Override
                                    public UserWorkEnvironmentalBean.DataBean apply(UserWorkEnvironmentalBean userWorkEnvironmentalBean) throws Exception {
                                        shift(userWorkEnvironmentalBean.getData().get(0).getTime());
                                        return userWorkEnvironmentalBean.getData().get(0);
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<UserWorkEnvironmentalBean.DataBean>() {
                                    @Override
                                    public void accept(UserWorkEnvironmentalBean.DataBean dataBean) throws Exception {
                                        tv_time.setText(dataBean.getTime());
                                        resetAdapter.notifyDataSetChanged();
                                        postAdapter.notifyDataSetChanged();
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.i(TAG, "???????????????" + throwable.getMessage());
                                    }
                                });
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "?????????????????????" + aLong);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "???????????????" + throwable.getMessage());
                    }
                });
    }

    /**
     * ??????????????????
     *
     * @param time ????????????????????????????????????
     */
    private void shift(String time) {
        Date currentTime = simpleDateFormat.parse(time, new ParsePosition(0));
        Date $24 = simpleDateFormat.parse("24:59", new ParsePosition(0));
        Date $00 = simpleDateFormat.parse("00:00", new ParsePosition(0));
        Date $19 = simpleDateFormat.parse("19:00", new ParsePosition(0));
        Date $18 = simpleDateFormat.parse("18:00", new ParsePosition(0));
        Date $09 = simpleDateFormat.parse("09:00", new ParsePosition(0));
        //19:00-09:00(19:00 - 24:59~~~~00:00 - 09:00)
        if (currentTime.compareTo($19) >= 0 && currentTime.compareTo($24) < 0 || currentTime.compareTo($00) > 0 && currentTime.compareTo($09) < 0) {
            Log.i(TAG, "??????");
            // ???????????????????????????
            if (rightBeans.size() > 0) {
                // ??????????????????
                leftBeans.addAll(rightBeans);
                rightBeans.clear();
            }
        } else if (currentTime.compareTo($09) >= 0 && currentTime.compareTo($18) <= 0) {//09:00 - 18:00
            Log.i(TAG, "??????");
            // ??????????????????????????????
            if (rightBeans.size() > 0) {
                Log.i(TAG, "??????????????????");
                return;
            }
            // ??????????????????????????????
            if (leftBeans.size() >= 4) {
                for (int i = 0; i < 4; i++) {
                    CustomerBean customerBean = leftBeans.get(0);
                    rightBeans.add(customerBean);
                    leftBeans.remove(customerBean);
                }
            } else {
                rightBeans.addAll(leftBeans);
                leftBeans.clear();
            }
        }
    }

    /**
     * ??????????????????????????????
     */
    @SuppressLint("CheckResult")
    private void getProductionLine() {
        remote.getProductionIsPosition(3)
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
                        // ???????????????????????????
                        if (dataBeans.size() == 0) {
                            // ????????????????????????
                            createProductionLine();
                        } else {
                            // ?????????????????????????????????
                            productionLineBean = dataBeans.get(0);
                            // ????????????????????????????????????
                            getPeople();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "???????????????" + throwable.getMessage());
                    }
                });
    }

    /**
     * ???????????????????????????????????????
     */
    @SuppressLint("CheckResult")
    private void getPeople() {
        remote.getUserPeople(productionLineBean.getId())
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<UserPeopleBean, List<UserPeopleBean.DataBean>>() {
                    @Override
                    public List<UserPeopleBean.DataBean> apply(UserPeopleBean userPeopleBean) throws Exception {
                        return userPeopleBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<UserPeopleBean.DataBean>>() {
                    @Override
                    public void accept(List<UserPeopleBean.DataBean> dataBeans) throws Exception {
                        peopleBeans = dataBeans;
                        // ???????????????????????????????????????????????????????????????
                        if (allPeopleBeans.size() == 0) {
                            getAllPeople();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "???????????????" + throwable.getMessage());
                    }
                });
    }

    /**
     * ????????????????????????
     */
    @SuppressLint("CheckResult")
    private void getAllPeople() {
        remote.getAllPeople()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<AllPeopleBean, List<AllPeopleBean.DataBean>>() {
                    @Override
                    public List<AllPeopleBean.DataBean> apply(AllPeopleBean allPeopleBean) throws Exception {
                        // ????????????
                        merge(allPeopleBean);
                        return allPeopleBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AllPeopleBean.DataBean>>() {
                    @Override
                    public void accept(List<AllPeopleBean.DataBean> dataBeans) throws Exception {
                        allPeopleBeans = dataBeans;
                        resetAdapter.setBeans(leftBeans);
                        postAdapter.setBeans(rightBeans);
                        resetAdapter.notifyDataSetChanged();
                        postAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "???????????????" + throwable.getMessage());
                    }
                });
    }

    /**
     * ????????????
     *
     * @param allPeopleBean
     */
    private void merge(AllPeopleBean allPeopleBean) {
        // ????????????
        for (UserPeopleBean.DataBean peopleBean : peopleBeans) {
            CustomerBean customerBean = new CustomerBean();
            for (AllPeopleBean.DataBean datum : allPeopleBean.getData()) {
                if (peopleBean.getPeopleId() == datum.getId()) {
                    customerBean.setName(datum.getPeopleName());
                    customerBean.setHp(peopleBean.getPower());
                    customerBean.setProgress(peopleBean.getPower());
                }
            }
            customerBeans.add(customerBean);
        }
        if (customerBeans.size() > 4) {
            leftBeans.addAll(customerBeans);
            /*for (int i = 0; i < 4; i++) {
                CustomerBean e = leftBeans.get(0);
                rightBeans.add(e);
                leftBeans.remove(e);
            }*/
        } else {
            rightBeans.addAll(customerBeans);
        }
    }

    /**
     * ???????????????
     */
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
                        Log.i(TAG, "???????????????" + s);
                        if (s.equals("???????????????????????????")) {
                            // ????????????????????????ID
                            getProductionLine();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "???????????????" + throwable.getMessage());
                    }
                });
    }

    class CustomerAdapter extends BaseAdapter {
        private List<CustomerBean> beans = new ArrayList<>();
        private TextView tvName;
        private TextView tvProgressRate;
        private TextView tvHp;
        private RelativeLayout rlProgress;

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

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item, null);
            } else {
                view = convertView;
            }

            initView(view);
            tvName.setText(getItem(position).getName());
            tvHp.setText("?????????" + getItem(position).getHp());
            // ???????????????????????????????????????
            int percentage = getItem(position).getProgress() / 100;
            // ??????????????????????????????
            ViewGroup.LayoutParams rlProgressLayoutParams = rlProgress.getLayoutParams();
            // ??????????????????????????????????????????
            ViewGroup.LayoutParams tvProgressRateLayoutParams = tvProgressRate.getLayoutParams();
            // ??????????????????????????????????????????????????????????????????????????????????????????
            tvProgressRateLayoutParams.width = rlProgressLayoutParams.width * percentage;
            // ????????????????????????????????????
            tvProgressRate.setLayoutParams(tvProgressRateLayoutParams);
            return view;
        }

        public List<CustomerBean> getBeans() {
            return beans;
        }

        public void setBeans(List<CustomerBean> beans) {
            this.beans = beans;
        }

        private void initView(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvProgressRate = (TextView) view.findViewById(R.id.tv_progress_rate);
            tvHp = (TextView) view.findViewById(R.id.tv_hp);
            rlProgress = (RelativeLayout) view.findViewById(R.id.rl_progress);
        }
    }

}
