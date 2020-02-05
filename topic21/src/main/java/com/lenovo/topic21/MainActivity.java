package com.lenovo.topic21;

import android.app.DatePickerDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private TextView mTvOutMoney;
    private TextView mTvInMoney;
    private TextView mTvStartDate;
    private TextView mTvEndDate;
    private Button mTvQuery;
    private LineChart mBarChart;
    private ApiService apiService;
    //一天的毫秒数
    private static Long dayOfTimeMillin = 24 * 60 * 60 * 1000L;
    private SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sdfParse2 = new SimpleDateFormat("yyyy-MM-dd");

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
        mBarChart = (LineChart) findViewById(R.id.barChart);
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
        //创建图表数据集合
        //查询默认时间的收支
        String startDateStr = mTvStartDate.getText().toString();
        String endDateStr = mTvEndDate.getText().toString();
        queryMoney(startDateStr, endDateStr);
    }


    /**
     * 过滤非当前日期区间的收支记录
     */
    private Observable<List<UserPriceLog.DataBean>> map(Observable<UserPriceLog> userPriceLogObservable, String startDate, String endDate) {
        long startTime = 0;
        long endTiem = 0;
        try {
            startTime = sdfParse2.parse(startDate).getTime();
            endTiem = sdfParse2.parse(endDate).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long finalStartTime = startTime;
        long finalEndTiem = endTiem;
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
//        //收入支出数据整合
//        Disposable disposable = Observable.merge(
//                map(apiService.getUserOutPriceLog(), startDate, endDate)/*支出数据*/,
//                map(apiService.getUserInPriceLog(), startDate, endDate)/*收入数据*/
//        )
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(dataBeans -> {
//                    //进行绘图
//                    Log.e(TAG, "------------------dataBeans:" + dataBeans.size());
//                }, throwable -> throwable.printStackTrace());

        apiService.getUserOutPriceLog().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserPriceLog>() {
            @Override
            public void accept(UserPriceLog userPriceLog) throws Exception {
                Log.e(TAG, "-----------------------*:" + userPriceLog.getData().size());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
        apiService.getUserInPriceLog().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserPriceLog>() {
            @Override
            public void accept(UserPriceLog userPriceLog) throws Exception {
                Log.e(TAG, "-----------------------*:" + userPriceLog.getData().size());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                throwable.printStackTrace();
            }
        });
    }

}
