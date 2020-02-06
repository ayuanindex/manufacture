package com.lenovo.topic22;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.ImageUtils;
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

public class MainActivity extends BaseActivity {
    private TextView mTvDate;
    private Button mTvQuery;
    private ListView mLv;
    private ApiService apiService;
    private CheckBox mRbPrice;
    private CheckBox mRbTime;
    private CheckBox mRbEndPrice;
    private MyAdapter mAdapter;
    private SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvDate = (TextView) findViewById(R.id.tv_date);
        mTvQuery = (Button) findViewById(R.id.tv_query);
        mLv = (ListView) findViewById(R.id.lv);
        mRbPrice = (CheckBox) findViewById(R.id.rb_price);
        mRbTime = (CheckBox) findViewById(R.id.rb_time);
        mRbEndPrice = (CheckBox) findViewById(R.id.rb_end_price);
        ImageUtils.setDrawableSize(mRbPrice);
        ImageUtils.setDrawableSize(mRbTime);
        ImageUtils.setDrawableSize(mRbEndPrice);
    }

    @Override
    protected void initEvent() {
        //显示日期的控件被点击的监听，弹出日期选择框
        mTvDate.setOnClickListener(v -> selectDate());
        //查询按钮被点击，显示当前日期的支出收入的总额
        mTvQuery.setOnClickListener(v -> queryMoney(mTvDate.getText().toString()));

        //排序
        mRbPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) sort(mRbPrice);
        });
        mRbTime.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) sort(mRbTime);
        });
        mRbEndPrice.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (buttonView.isPressed()) sort(mRbEndPrice);
        });
    }


    private void sort(CheckBox cb) {
        boolean isChecked = cb.isChecked();
        mRbPrice.setChecked(false);
        mRbTime.setChecked(false);
        mRbEndPrice.setChecked(false);
        cb.setChecked(isChecked);
        if (mAdapter != null) {
            List<UserPriceLog.DataBean> dataBeans = mAdapter.getDataBeans();
            //进行排序
            if (cb == mRbPrice && mRbPrice.isChecked()) {
                //金额升序
                Collections.sort(dataBeans, (o1, o2) -> o1.getPrice() - o2.getPrice());
            } else if (cb == mRbTime && mRbTime.isChecked()) {
                //时间升序
                Collections.sort(dataBeans, (o1, o2) -> {
                    int result = 0;
                    try {
                        long value = sdfParse.parse(o1.getTime()).getTime() - sdfParse.parse(o2.getTime()).getTime();
                        if (value > 0) {
                            result = 1;
                        } else if (value < 0) {
                            result = -1;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return result;
                });
            } else if (cb == mRbEndPrice && mRbEndPrice.isChecked()) {
                //剩余金额升序
                Collections.sort(dataBeans, (o1, o2) -> o1.getEndPrice() - o2.getEndPrice());
            } else if (cb == mRbPrice && !mRbPrice.isChecked()) {
                //金额降序
                Collections.sort(dataBeans, (o1, o2) -> o2.getPrice() - o1.getPrice());
            } else if (cb == mRbTime && !mRbTime.isChecked()) {
                //时间降序
                Collections.sort(dataBeans, (o1, o2) -> {
                    int result = 0;
                    try {
                        long value = sdfParse.parse(o2.getTime()).getTime() - sdfParse.parse(o1.getTime()).getTime();
                        if (value > 0) {
                            result = 1;
                        } else if (value < 0) {
                            result = -1;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return result;
                });
            } else if (cb == mRbEndPrice && !mRbEndPrice.isChecked()) {
                //剩余金额降序
                Collections.sort(dataBeans, (o1, o2) -> o2.getEndPrice() - o1.getEndPrice());
            }
            //刷新列表
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void initData() {
        //创建网络请求服务
        apiService = Network.remote(ApiService.class);
        //创建图表数据集合
        //查询默认时间的收支
        String dateStr = mTvDate.getText().toString();
        queryMoney(dateStr);
    }


    /**
     * 过滤非当前日期收支记录
     */
    private Observable<List<UserPriceLog.DataBean>> map(Observable<UserPriceLog> userPriceLogObservable, String dateStr) {
        return userPriceLogObservable.map(UserPriceLog::getData)
                .map(dataBeans -> {
                    List<UserPriceLog.DataBean> data = new ArrayList<>();
                    for (int i = 0; i < dataBeans.size(); i++) {
                        if (dataBeans.get(i).getTime().contains(dateStr))
                            data.add(dataBeans.get(i));
                    }
                    return data;
                });
    }

    /**
     * 查询收支的金额
     *
     * @param dateStr 查询条件，日期
     */
    private void queryMoney(String dateStr) {
        //收入支出数据整合
        Observable.zip(map(apiService.getUserOutPriceLog(), dateStr)/*支出数据*/,
                map(apiService.getUserInPriceLog(), dateStr)/*收入数据*/,
                (dataBeans, dataBeans2) -> {
                    dataBeans.addAll(dataBeans2);
                    return dataBeans;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())//绑定生命周期
                .subscribe(dataBeans -> {
                    //给listview设置数据
                    mLv.setAdapter(mAdapter = new MyAdapter(dataBeans));
                    //进行默认排序
                    mRbPrice.setChecked(true);
                    sort(mRbPrice);
                }, throwable -> Log.e(TAG, "----------------------throwable:" + throwable.getMessage()));
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


    private class MyAdapter extends BaseAdapter {

        private List<UserPriceLog.DataBean> dataBeans;
        //(0、原材料，1、人员，2、生产线，3、维修生产环节，4、维修车辆，5、售出)
        private Map<Integer, String> types = new HashMap<>();

        public MyAdapter(List<UserPriceLog.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
            types.put(0, "原材料");
            types.put(1, "人员");
            types.put(2, "生产线");
            types.put(3, "维修生产环节");
            types.put(4, "维修车辆");
            types.put(5, "售出");
        }

        public List<UserPriceLog.DataBean> getDataBeans() {
            return dataBeans;
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

            UserPriceLog.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getPrice() + "");
            holder.mTv2.setText(types.get(dataBean.getType()) + "");
            holder.mTv4.setText(dataBean.getTime() + "");
            holder.mTv5.setText(dataBean.getEndPrice() + "");
            return convertView;
        }


        class ViewHolder {
            protected TextView mTv1;
            protected TextView mTv2;
            protected TextView mTv4;
            protected TextView mTv5;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv_1);
                mTv2 = (TextView) view.findViewById(R.id.tv_2);
                mTv4 = (TextView) view.findViewById(R.id.tv_4);
                mTv5 = (TextView) view.findViewById(R.id.tv_5);

            }
        }
    }

}
