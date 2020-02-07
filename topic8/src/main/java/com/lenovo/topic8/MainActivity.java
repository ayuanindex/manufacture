package com.lenovo.topic8;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic8.bean.AllPeople;
import com.lenovo.topic8.bean.LineToPeople;
import com.lenovo.topic8.bean.ProductionLine;
import com.lenovo.topic8.bean.ResultMessage;
import com.lenovo.topic8.bean.ResultMessageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_gongcheng_name;
    private TextView tv_gongcheng_hp;
    private LinearLayout ll_gongcheng;
    private TextView tv_zhijian_name;
    private TextView tv_zhijian_hp;
    private LinearLayout ll_zhijian;
    private TextView tv_jishu_name;
    private TextView tv_jishu_hp;
    private LinearLayout ll_jishu;
    private TextView tv_caozuo_name;
    private TextView tv_caozuo_hp;
    private LinearLayout ll_caozuo;
    private ApiService remote;
    private int productionClass;
    private int productionLineId;
    private List<AllPeople.DataBean> allPeoples;

    /**
     * @return 获取界面布局的资源ID
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
        tv_gongcheng_name = (TextView) findViewById(R.id.tv_gongcheng_name);
        tv_gongcheng_hp = (TextView) findViewById(R.id.tv_gongcheng_hp);
        ll_gongcheng = (LinearLayout) findViewById(R.id.ll_gongcheng);
        tv_zhijian_name = (TextView) findViewById(R.id.tv_zhijian_name);
        tv_zhijian_hp = (TextView) findViewById(R.id.tv_zhijian_hp);
        ll_zhijian = (LinearLayout) findViewById(R.id.ll_zhijian);
        tv_jishu_name = (TextView) findViewById(R.id.tv_jishu_name);
        tv_jishu_hp = (TextView) findViewById(R.id.tv_jishu_hp);
        ll_jishu = (LinearLayout) findViewById(R.id.ll_jishu);
        tv_caozuo_name = (TextView) findViewById(R.id.tv_caozuo_name);
        tv_caozuo_hp = (TextView) findViewById(R.id.tv_caozuo_hp);
        ll_caozuo = (LinearLayout) findViewById(R.id.ll_caozuo);
    }

    /**
     * 初始化控件监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        ll_gongcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distribution(0);
            }
        });

        ll_jishu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distribution(2);
            }
        });

        ll_caozuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distribution(1);
            }
        });

        ll_zhijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distribution(3);
            }
        });
    }

    /**
     * 初始化需要使用的工具类
     */
    @Override
    protected void initData() {
        // 实例化ApiService接口类
        remote = Network.remote(ApiService.class);

        allPeoples = new ArrayList<AllPeople.DataBean>();

        // 获取第四条生产线的数据
        getProductionLine(3);
    }

    /**
     * 查询指定位置的生产线
     *
     * @param position 位置
     */
    @SuppressLint("CheckResult")
    private void getProductionLine(int position) {
        remote.getProductionLine(position)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductionLine>() {
                    @Override
                    public void accept(ProductionLine productionLine) throws Exception {
                        Log.i(TAG, "请求成功" + productionLine.toString());
                        // 如果第四条生产线不存在则自动创建，如果存在则查询在岗员工信息
                        if (productionLine.getData().size() == 0) {
                            productionClass = 1;
                            // 创建第四条生产线，生产线类型为1
                            createProduction(productionClass);
                        } else {
                            // 如果已经存在第四条生产线，则获取第四条生产线的信息
                            // 生产线类别
                            productionClass = productionLine.getData().get(0).getProductionLineId();
                            // 拿到第四条生产线的ID
                            productionLineId = productionLine.getData().get(0).getId();
                            Log.i(TAG, "当前ID：" + productionLineId);
                            // 获取当前生产线的学生员工
                            getLineToPeople(productionLineId);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络请求发生错误,生产线查询失败：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 创建生产线
     *
     * @param productionClass
     */
    @SuppressLint("CheckResult")
    private void createProduction(int productionClass) {
        remote.createProduction(productionClass, 3)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultMessageBean>() {
                    @Override
                    public void accept(ResultMessageBean resultMessageBean) throws Exception {
                        if (!resultMessageBean.getMessage().equals("该位置已存在生产线")) {
                            Log.i(TAG, "生产线创建成功");
                            // 重新查询
                            getProductionLine(3);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网路请求出现问题，生产线创建失败：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 查询指定生产线所有员工信息
     *
     * @param productionLineId 生产线ID
     */
    @SuppressLint("CheckResult")
    private void getLineToPeople(int productionLineId) {
        remote.getLineToPeople(productionLineId)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LineToPeople>() {
                    @Override
                    public void accept(LineToPeople allStudentBean) throws Exception {
                        // 获取获取所有人员与学生员工进行比对，查出姓名
                        getAllPeople(allStudentBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络请求发生错误，获取学生员工失败：" + throwable.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void getAllPeople(LineToPeople allStudentBean) {
        remote.getAllPeople()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<AllPeople, List<AllPeople.DataBean>>() {
                    @Override
                    public List<AllPeople.DataBean> apply(AllPeople allPeople) throws Exception {
                        return allPeople.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AllPeople.DataBean>>() {
                    @Override
                    public void accept(List<AllPeople.DataBean> dataBeans) throws Exception {
                        allPeoples = dataBeans;
                        Log.i(TAG, "网络请求成功，所有人员信息" + dataBeans.toString());
                        Log.i(TAG, "查询指定生产线的员工信息：" + allStudentBean.toString());
                        for (LineToPeople.DataBean datum : allStudentBean.getData()) {
                            //0、工程师，1、工人，2、技术人员，3、检测人员)
                            switch ((datum.getWorkPostId() - 1 - ((productionClass - 1) * 4))) {
                                case 0:
                                    setValue(ll_caozuo, tv_caozuo_name, tv_caozuo_hp, datum);
                                    break;
                                case 1:
                                    setValue(ll_gongcheng, tv_gongcheng_name, tv_gongcheng_hp, datum);
                                    break;
                                case 2:
                                    setValue(ll_jishu, tv_jishu_name, tv_jishu_hp, datum);
                                    break;
                                case 3:
                                    setValue(ll_zhijian, tv_zhijian_name, tv_zhijian_hp, datum);
                                    break;
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络请求发生错误，获取所有人员信息失败：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 给控件设置值
     *
     * @param ll
     * @param tv_name
     * @param tv_hp
     * @param datum
     */
    @SuppressLint("SetTextI18n")
    private void setValue(LinearLayout ll, TextView tv_name, TextView tv_hp, LineToPeople.DataBean datum) {
        if (allPeoples.size() == 0) {
            Log.i(TAG, "所有人员信息为空");
            return;
        }
        tv_name.setVisibility(View.VISIBLE);
        tv_hp.setVisibility(View.VISIBLE);
        for (AllPeople.DataBean allPeople : allPeoples) {
            if (datum.getPeopleId() == allPeople.getId()) {
                tv_name.setText("姓名：" + allPeople.getPeopleName());
                tv_hp.setText("体力：" + datum.getPower() + "");
            }
        }
        ll.setBackgroundColor(Color.parseColor("#6F96FE"));
    }

    /**
     * 分配员工
     *
     * @param type 需要分配的岗位类型（ 0、工程师，1、工人，2、技术人员，3、检测人员)）
     */
    @SuppressLint("CheckResult")
    private void distribution(int type) {
        for (AllPeople.DataBean allPeople : allPeoples) {
            if (allPeople.getStatus() == type) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("userWorkId", 1);// 学生工厂
                map.put("power", allPeople.getHp()); // 员工体力
                map.put("peopleId", allPeople.getId());// 人员ID
                map.put("userProductionLineId", productionLineId);// 生产线ID
                map.put("workPostId", (allPeople.getStatus() + 1) + ((productionClass - 1) * 4));// 岗位
                remote.createStudent(map)
                        .compose(this.bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ResultMessage>() {
                            @Override
                            public void accept(ResultMessage resultMessage) throws Exception {
                                if (resultMessage.getMessage().equals("SUCCESS")) {
                                    Toast.makeText(MainActivity.this, "招募成功", Toast.LENGTH_SHORT).show();
                                }
                                getLineToPeople(productionLineId);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Log.i(TAG, "出现错误：创建学生员工失败：" + throwable.getMessage());
                            }
                        });
                break;
            }
        }
    }
}
