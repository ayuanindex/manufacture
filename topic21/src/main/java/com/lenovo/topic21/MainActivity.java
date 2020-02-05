package com.lenovo.topic21;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private TextView mTvOutMoney;
    private TextView mTvInMoney;
    private TextView mTvStartDate;
    private TextView mTvEndDate;
    private Button mTvQuery;
    private LineChart mLineChart;
    private ApiService apiService;
    //一天的毫秒数
    private static Long dayOfTimeMillin = 24 * 60 * 60 * 1000L;
    //日期解析
    private SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfParse2 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfParse3 = new SimpleDateFormat("MM-dd");

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvOutMoney = (TextView) findViewById(R.id.tv_out_money);
        mTvInMoney = (TextView) findViewById(R.id.tv_in_money);
        mTvStartDate = (TextView) findViewById(R.id.tv_start_date);
        mTvEndDate = (TextView) findViewById(R.id.tv_end_date);
        mTvQuery = (Button) findViewById(R.id.tv_query);
        mLineChart = (LineChart) findViewById(R.id.lineChart);

    }

    @Override
    protected void initEvent() {
        //设置日期选择
        mTvStartDate.setOnClickListener(v -> selectDate(mTvStartDate));
        mTvEndDate.setOnClickListener(v -> selectDate(mTvEndDate));
        mTvQuery.setOnClickListener(v -> queryMoney(mTvStartDate.getText().toString(), mTvEndDate.getText().toString()));
    }

    /**
     * 弹出日期选择框
     */
    private void selectDate(TextView mTvDate) {
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
        //初始化图表
        initLineChart();
        //查询默认时间的收支
        String startDateStr = mTvStartDate.getText().toString();
        String endDateStr = mTvEndDate.getText().toString();
        //进行支出收入的金额查询
        queryMoney(startDateStr, endDateStr);
    }

    //初始化折线图
    private void initLineChart() {
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.getDescription().setText("");
    }


    /**
     * 过滤非当前日期区间的收支记录
     */
    private Observable<List<UserPriceLog.DataBean>> map(Observable<UserPriceLog> userPriceLogObservable, String startDate, String endDate) {
        //开始时间
        long startTime = 0;
        //结束时间
        long endTiem = 0;
        try {
            startTime = sdfParse2.parse(startDate).getTime();
            endTiem = sdfParse2.parse(endDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long finalStartTime = startTime;
        long finalEndTiem = endTiem;
        //过滤掉非当前时间范围的数据
        return userPriceLogObservable.map(UserPriceLog::getData)
                .map(dataBeans -> {
                    List<UserPriceLog.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < dataBeans.size(); i++) {
                        String time = dataBeans.get(i).getTime();
                        long tempTime = sdfParse.parse(time).getTime();
                        if (tempTime >= finalStartTime && tempTime < finalEndTiem + dayOfTimeMillin) {
                            data.add(dataBeans.get(i));
                        }
                    }
                    return data;
                });
    }

    /**
     * 查询收支的金额
     */
    private void queryMoney(String startDate, String endDate) {
        try {
            if (sdfParse2.parse(startDate).getTime() >= sdfParse2.parse(endDate).getTime()) {
                Toast.makeText(this, "结束时间必须大于开始时间", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*支出数据*/
        Observable<List<UserPriceLog.DataBean>> observableOut = map(apiService.getUserOutPriceLog(), startDate, endDate);
        /*收入数据*/
        Observable<List<UserPriceLog.DataBean>> observableIn = map(apiService.getUserInPriceLog(), startDate, endDate);
        //收入支出数据整合
        Observable.zip(observableOut, observableIn,
                (dataBeans, dataBeans2) -> {
                    ArrayList<UserPriceLog.DataBean> datas = new ArrayList<>(dataBeans);
                    datas.addAll(dataBeans2);
                    return datas;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    //计算总收入，总支出
                    int outMoney = 0, inMoney = 0;
                    for (int i = 0; i < dataBeans.size(); i++) {
                        UserPriceLog.DataBean dataBean = dataBeans.get(i);
                        if (dataBean.getType() == 5) {
                            //只有5售出，是收入，其他的均是支出
                            inMoney += dataBeans.get(i).getPrice();
                        } else {
                            outMoney += dataBeans.get(i).getPrice();
                        }
                    }
                    //设置金额显示
                    mTvOutMoney.setText(String.valueOf(outMoney));
                    mTvInMoney.setText(String.valueOf(inMoney));
                    Log.e(TAG, "------------------dataBeans:" + dataBeans.size());
                    //进行绘图
                    drawLineChart(dataBeans);
                }, throwable -> throwable.printStackTrace());
    }


    /**
     * 绘制折线图
     *
     * @param dataBeans
     */
    private void drawLineChart(ArrayList<UserPriceLog.DataBean> dataBeans) throws ParseException {
        //计算出两个日期间相差的天数
        long endTime = sdfParse2.parse(mTvEndDate.getText().toString()).getTime();
        long startTime = sdfParse2.parse(mTvStartDate.getText().toString()).getTime();
        int day = (int) ((endTime - startTime) / (24 * 60 * 60 * 1000) + 1);
        Log.e(TAG, "--------------------day:" + day);
        //创建x轴数据
        List<String> xValues = new ArrayList<>();
        Date date = new Date(startTime);
        for (int i = 0; i < day; i++) {
            xValues.add(sdfParse3.format(date));
        }


        //设置x轴的数量
        mLineChart.getXAxis().setLabelCount(xValues.size());

        LineData lineData = new LineData();
        mLineChart.setData(lineData);
        mLineChart.invalidate();


    }

}
