package com.lenovo.topic04;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic04.bean.FactoryEnvironment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic4
 * @ClassName: MainActivity
 * @CreateDate: 2020/1/18 12:06
 */
public class MainActivity extends BaseActivity {
    private BarChart barchart;
    private ImageView iv_back;
    private TextView tv_consumption;
    private TextView tv_supply;
    private ArrayList<BarEntry> yValsLeft;
    private ArrayList<BarEntry> yValsRight;

    /**
     * 实例化的ApiService
     */
    private ApiService remote;

    /**
     * Observable循环的订阅
     */
    private Disposable subscribe;

    /**
     * 电力消耗
     */
    private ArrayList<Float> consumptionList;

    /**
     * 电力供应
     */
    private ArrayList<Float> supplyList;
    private BarDataSet barDataSetLeft;
    private BarDataSet barDataSetRight;
    private BarData barData;

    /**
     * 获取布局文件
     *
     * @return 返回布局文件的ID
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
        barchart = (BarChart) findViewById(R.id.barchart);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_supply = (TextView) findViewById(R.id.tv_supply);
        tv_consumption = (TextView) findViewById(R.id.tv_consumption);
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭当前界面
                MainActivity.this.closeActivity();
                Log.i(TAG, "onClick: 当前页面已关闭");
            }
        });
    }

    /**
     * 初始化需要使用的类
     */
    @Override
    protected void initData() {
        // 将ApiService实例化
        remote = Network.remote(ApiService.class);
        // 创建两个集合，用来存储最近24条数据
        consumptionList = new ArrayList<>();
        supplyList = new ArrayList<>();

        // 初始化图表
        initBarChart();
        // 请求获取工厂环境数据
        getFactoryEnvironment();
    }

    /**
     * 初始化图表
     */
    private void initBarChart() {
        yValsLeft = new ArrayList<>();
        yValsRight = new ArrayList<>();

        // 初始化图表数据
        for (int i = 0; i < 24; i++) {
            yValsLeft.add(new BarEntry(i, 0));
            yValsRight.add(new BarEntry((float) (i + 0.3), 0));
        }

        barDataSetLeft = new BarDataSet(yValsLeft, "A");
        barDataSetRight = new BarDataSet(yValsRight, "B");

        barDataSetLeft.setColor(Color.RED);
        barDataSetRight.setColor(Color.BLUE);

        barData = new BarData();
        barData.addDataSet(barDataSetLeft);
        barData.addDataSet(barDataSetRight);
        barData.setBarWidth(0.3f);
        barData.setDrawValues(false);

        XAxis xAxis = barchart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(16);
        // 设置X轴的坐标格式
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return (int) value + ":00";
            }
        });
        YAxis axisLeft = barchart.getAxisLeft();
        // 设置Y轴的坐标单位
        axisLeft.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        barchart.getAxisRight().setEnabled(false);
        barchart.getDescription().setEnabled(false);
        barchart.setBackgroundColor(Color.WHITE);
        barchart.setTouchEnabled(false);
        barchart.setData(barData);
        barchart.invalidate();
    }

    /**
     * 刷新图表
     */
    private void refreshBarChart() {
        yValsRight.clear();
        yValsLeft.clear();
        for (int i = 0; i < 24; i++) {
            if (i < supplyList.size()) {
                // 电力消耗
                yValsLeft.add(new BarEntry(i, consumptionList.get(i)));
                // 电力供应
                yValsRight.add(new BarEntry((float) (i + 0.3), supplyList.get(i)));
            } else {
                yValsLeft.add(new BarEntry(i, 0));
                yValsRight.add(new BarEntry((float) (i + 0.3), 0));
            }
        }
        barDataSetLeft.notifyDataSetChanged();
        barDataSetRight.notifyDataSetChanged();
        barData.notifyDataChanged();
        barchart.notifyDataSetChanged();
        barchart.invalidate();
    }

    /**
     * 获取工厂环境数据的方法
     */
    private void getFactoryEnvironment() {
        // 使用RxJava提供的定时方式来替代Timer
        // @field1：周期开始时的延时；@field2：周期时间；@field3：计时单位
        subscribe = Observable.interval(0, 5, TimeUnit.SECONDS)
                // 每个周期所执行的方法
                .doOnNext(new Consumer<Long>() {

                    /**
                     * 获取工厂用电量的订阅
                     */
                    private Disposable subscribe;

                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 取消上次执行的订阅
                        unsubscribe(subscribe);
                        subscribe = remote.getFactoryEnvironment(1)
                                // 切换到子线程进行网络请求
                                .subscribeOn(Schedulers.io())
                                // 对数据进行转换
                                .map(new Function<FactoryEnvironment, FactoryEnvironment.DataBeanList>() {
                                    @Override
                                    public FactoryEnvironment.DataBeanList apply(FactoryEnvironment factoryEnvironment) throws Exception {
                                        // 提取出需要使用的数据共下面的方法使用
                                        return factoryEnvironment.getData().get(0);
                                    }
                                })
                                // 切换到主线程执行
                                .observeOn(AndroidSchedulers.mainThread())
                                // 订阅网络请求状态
                                .subscribe(new Consumer<FactoryEnvironment.DataBeanList>() {// 请求成功
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void accept(FactoryEnvironment.DataBeanList dataBeanList) throws Exception {
                                        // 判断集合中元素的个数是否大于24个，如果大于｜等于24个则删除第一个
                                        if (supplyList.size() >= 24) {
                                            supplyList.remove(0);
                                            consumptionList.remove(0);
                                        }
                                        // 电力供应
                                        String supply = dataBeanList.getPower();
                                        // 电力消耗 = 电力供应 / 1.1
                                        float consumption = (float) (Float.parseFloat(supply) / 1.1);
                                        // 将电力供应和电力消耗添加到集合中
                                        consumptionList.add(consumption);
                                        supplyList.add(Float.parseFloat(supply));

                                        tv_consumption.setText(consumption + "");
                                        tv_supply.setText(supply + "");

                                        // 刷新图表
                                        refreshBarChart();
                                    }
                                }, new Consumer<Throwable>() {// 请求失败
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.i(TAG, "accept: 内层网络请求发生错误：" + throwable.getMessage());
                                    }
                                });
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: 定时出现错误：" + throwable.getMessage());
                    }
                });
    }


    /**
     * 取消订阅的方法
     *
     * @param subscribe 需要取消的订阅
     */
    private void unsubscribe(Disposable subscribe) {
        if (subscribe != null && !subscribe.isDisposed()) {
            subscribe.dispose();
            Log.i(TAG, "unsubscribe: 已取消订阅" + subscribe);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁定时器的订阅
        unsubscribe(subscribe);
    }
}
