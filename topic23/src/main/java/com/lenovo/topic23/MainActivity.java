package com.lenovo.topic23;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 题库第23题
 */
public class MainActivity extends BaseActivity {
    private TextView mTvOutMoney;
    private TextView mTvInMoney;
    private TextView mTvDate;
    private Button mTvQuery;
    private ApiService apiService;
    private Map<String, Integer> chartDatas;
    private SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfFormate = new SimpleDateFormat("HH");
    private BarChart mBarChart;
    private List<String> xValues;
    private List<BarEntry> yValues;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        //显示支出总额TextView控件
        mTvOutMoney = (TextView) findViewById(R.id.tv_out_money);
        //显示收入总额TextView控件
        mTvInMoney = (TextView) findViewById(R.id.tv_in_money);
        //显示日期的TextView控件
        mTvDate = (TextView) findViewById(R.id.tv_date);
        //查询按钮的Button控件
        mTvQuery = (Button) findViewById(R.id.tv_query);
        //图表
        mBarChart = (BarChart) findViewById(R.id.barChart);
    }

    @Override
    protected void initEvent() {
        //显示日期的控件被点击的监听，弹出日期选择框
        mTvDate.setOnClickListener(v -> selectDate());
        //查询按钮被点击，显示当前日期的支出收入的总额
        mTvQuery.setOnClickListener(v -> queryMoney(mTvDate.getText().toString()));
    }

    /**
     * 查询收支的金额
     *
     * @param dateStr 查询条件，日期
     */
    private void queryMoney(String dateStr) {
        chartDatas.clear();
        //收入数据处理
        inMoney(dateStr);
        outMoney(dateStr);
    }


    /**
     * 支出数据处理
     */
    private void outMoney(String dateStr) {
        Observable<List<UserOutPriceLog.DataBean>> observable = apiService.getUserOutPriceLog()
                .map(UserOutPriceLog::getData)
                .map(dataBeans -> {
                    //过滤掉非当前日期收入记录
                    List<UserOutPriceLog.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < dataBeans.size(); i++) {
                        if (dataBeans.get(i).getTime().contains(dateStr))
                            data.add(dataBeans.get(i));
                    }
                    return data;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        //计算总支出并显示
        observable.map(dataBeans -> {
            int totalMoney = 0;
            for (int i = 0; i < dataBeans.size(); i++) {
                totalMoney += dataBeans.get(i).getPrice();
            }
            return totalMoney;
        }).subscribe(integer -> mTvOutMoney.setText(String.valueOf(integer)),
                throwable -> Log.e(TAG, "-----------:throwable" + throwable.getMessage()));


        observable.subscribe(dataBeans -> {
            for (int i = 0; i < dataBeans.size(); i++) {
                UserOutPriceLog.DataBean dataBean = dataBeans.get(i);
                //Map的key
                String formatDate = sdfFormate.format(sdfParse.parse(dataBean.getTime()));
                chartDatas.put(formatDate, chartDatas.get(formatDate) == null ? 0 : chartDatas.get(formatDate) + dataBean.getPrice() * -1);
            }
            //更新图表数据
            updateChart();
        }, throwable -> Log.e(TAG, "--------------:throwable" + throwable.getMessage()));
    }

    /**
     * 收入数据处理
     */
    private void inMoney(String dateStr) {
        //***********收入**************
        Observable<List<UserInPriceLog.DataBean>> observable = apiService
                .getUserInPriceLog()
                .map(UserInPriceLog::getData)
                .map(dataBeans -> {
                    //过滤掉非当前日期收入记录
                    List<UserInPriceLog.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < dataBeans.size(); i++) {
                        if (dataBeans.get(i).getTime().contains(dateStr))
                            data.add(dataBeans.get(i));
                    }
                    return data;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        //计算总收入并显示
        observable.map(dataBeans -> {
            //计算收入总和并显示
            int totalMoney = 0;
            int size = dataBeans.size();
            for (int i = 0; i < size; i++) {
                totalMoney += dataBeans.get(i).getPrice();
            }
            return totalMoney;
        }).subscribe(
                integer -> mTvInMoney.setText(String.valueOf(integer)),
                throwable -> Log.i(TAG, "--------------" + throwable.getMessage()));

        //汇总图表需要的数据
        observable.subscribe(dataBeans -> {
            for (int i = 0; i < dataBeans.size(); i++) {
                UserInPriceLog.DataBean dataBean = dataBeans.get(i);
                String formatDate = sdfFormate.format(sdfParse.parse(dataBean.getTime()));
                chartDatas.put(formatDate, chartDatas.get(formatDate) == null ? 0 : chartDatas.get(formatDate) + dataBean.getPrice());
            }
            //更新图表数据
            updateChart();
        }, throwable -> Log.e(TAG, "--------------throwable:" + throwable.getMessage()));
    }


    /**
     * 弹出日期选择框
     */
    private void selectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mTvDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                datePickerDialog.dismiss();
            }
        });
        datePickerDialog.show();
        datePickerDialog.updateDate(2019, 11, 18);
    }

    @Override
    protected void initData() {
        //创建网络请求服务
        apiService = Network.remote(ApiService.class);
        //创建图表数据集合
        chartDatas = new HashMap<>();
        //查询默认时间的收支
        String dateStr = mTvDate.getText().toString();
        queryMoney(dateStr);
        //初始化图表数据
        initChart();
    }

    //初始化部分设置
    private void initChart() {
        xValues = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i <= 9) {
                xValues.add(0 + "" + i + ":00");
            } else {
                xValues.add(i + ":00");
            }

        }
        yValues = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            yValues.add(new BarEntry(i, 0));
        }
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelRotationAngle(-45);
        //设置x轴数据的最小间隔
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(24);
        xAxis.setDrawGridLines(false);
        // 设置x轴数据显示
        xAxis.setValueFormatter((value, axis) -> {
            int key = (int) value;
            if (key <= xValues.size() || key >= 0) {
                return xValues.get(key);
            }
            return null;
        });
        mBarChart.getDescription().setText("");
        mBarChart.setBackgroundColor(Color.WHITE);
        mBarChart.getLegend().setEnabled(false);
    }

    /**
     * 更新图表信息
     */
    private void updateChart() {
        //清空y轴数据
        yValues.clear();
        //重新构造x轴数据
        //Map，按日期排序
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(chartDatas.entrySet());
        Collections.sort(entries, (o1, o2) -> {
            int value = 0;
            try {
                if (sdfFormate.parse(o1.getKey()).getTime() - sdfFormate.parse(o2.getKey()).getTime() > 0) {
                    value = 1;
                } else if (sdfFormate.parse(o1.getKey()).getTime() - sdfFormate.parse(o2.getKey()).getTime() < 0) {
                    value = -1;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return value;
        });

        for (int i = 0; i < entries.size(); i++) {
            Map.Entry<String, Integer> entry = entries.get(i);
            Integer value = entry.getValue();
            Log.e(TAG, "------------------key:" + entry.getKey());
            yValues.add(new BarEntry(Integer.valueOf(entry.getKey()), value));
        }
        //**********************************添加两条条测试数据，避免全部都是支出，全部都是收入，看不到0界限上下的柱状图
        yValues.add(new BarEntry(23, -1000));
        yValues.add(new BarEntry(00, 1000));
        //*************************************************
        //设置柱状图颜色
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < yValues.size(); i++) {
            BarEntry barEntry = yValues.get(i);
            if (barEntry.getY() > 0) {
                colors.add(Color.parseColor("#E15071"));
            } else {
                colors.add(Color.parseColor("#10A9AF"));
            }
        }
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        barDataSet.setColors(colors);
        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);
        mBarChart.invalidate();
    }
}
