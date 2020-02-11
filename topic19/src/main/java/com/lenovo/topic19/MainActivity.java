package com.lenovo.topic19;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private TextView mTvStartDate;
    private TextView mTvEndDate;
    private Button mTvQuery;
    private ListView mLv;
    private APIService apiService;
    private SimpleDateFormat sdfParse2 = new SimpleDateFormat("yyyy-MM-dd");
    //一天的毫秒数
    private static Long dayOfTimeMillin = 24 * 60 * 60 * 1000L;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvStartDate = (TextView) findViewById(R.id.tv_start_date);
        mTvEndDate = (TextView) findViewById(R.id.tv_end_date);
        mTvQuery = (Button) findViewById(R.id.tv_query);
        mLv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void initEvent() {
        //设置日期选择
        mTvStartDate.setOnClickListener(v -> selectDate(mTvStartDate));
        mTvEndDate.setOnClickListener(v -> selectDate(mTvEndDate));
        mTvQuery.setOnClickListener(v -> queryOuAndIn(mTvStartDate.getText().toString(), mTvEndDate.getText().toString()));
    }

    @Override
    protected void initData() {
        apiService = Network.remote(APIService.class);
        //默认查询
        queryOuAndIn("1970-01-01", "2020-05-01");
    }


    /**
     * 数据处理
     */
    private Observable<List<Bean.DataBean>> map(Observable<Bean> observable, String type, String startDate, String endDate) {
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
        return observable.map(Bean::getData)
                .map(dataBeans -> {
                    //过滤非当前时间数据
                    List<Bean.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < dataBeans.size(); i++) {
                        long tempTime = Long.valueOf(dataBeans.get(i).getLastUpdateTime() + "000");
                        if (tempTime >= finalStartTime && tempTime < finalEndTiem + dayOfTimeMillin) {
                            Bean.DataBean dataBean = dataBeans.get(i);
                            //添加数据，即格式化好的时间字符串
                            dataBean.setTimeStr(sdfParse2.format(new Date(tempTime)));
                            data.add(dataBean);
                        }
                    }
                    return data;
                })
                .map(dataBeans -> {
                    //数据改造，增加出/入库属性
                    for (int i = 0; i < dataBeans.size(); i++) {
                        dataBeans.get(i).setType(type);
                    }
                    return dataBeans;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 查询出入库
     */
    @SuppressLint("CheckResult")
    private void queryOuAndIn(String startDate, String endDate) {
        Observable<List<Bean.DataBean>> in = map(apiService.getIn(), "入库", startDate, endDate);
        Observable<List<Bean.DataBean>> out = map(apiService.getOut(), "出库", startDate, endDate);
        Observable
                .zip(in, out, ((dataBeans, dataBeans2) -> {
                            dataBeans.addAll(dataBeans2);
                            return dataBeans;
                        })
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> mLv.setAdapter(new MyAdapter(dataBeans)), throwable -> throwable.printStackTrace());
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
        datePickerDialog.updateDate(1970, 0, 1);
    }

    /**
     * 列表适配器
     */
    private class MyAdapter extends BaseAdapter {

        private List<Bean.DataBean> dataBeans;

        public MyAdapter(List<Bean.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        @Override
        public int getCount() {
            return dataBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Bean.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getMaterialName());
            holder.mTv2.setText(dataBean.getType());
            holder.mTv3.setText(dataBean.getTimeStr());
            holder.mTv4.setText(dataBean.getType());
            holder.mTv5.setText(dataBean.getNum() + "");
            return convertView;
        }

        class ViewHolder {
            TextView mTv1;
            TextView mTv2;
            TextView mTv3;
            TextView mTv4;
            TextView mTv5;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);
                mTv4 = (TextView) view.findViewById(R.id.tv4);
                mTv5 = (TextView) view.findViewById(R.id.tv5);
            }
        }
    }
}
