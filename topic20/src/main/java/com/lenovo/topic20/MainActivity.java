package com.lenovo.topic20;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.ImageUtils;
import com.lenovo.basic.utils.Network;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private RadioButton mRb1;
    private RadioButton mRb2;
    private RadioButton mRb3;
    private RadioButton mRb4;
    private ApiService apiService;
    private ListView mLv;
    private MyAdapter mAdapter;
    private String ip = Network.ip;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mLv = (ListView) findViewById(R.id.lv);
        mRb1 = (RadioButton) findViewById(R.id.rb1);
        mRb2 = (RadioButton) findViewById(R.id.rb2);
        mRb3 = (RadioButton) findViewById(R.id.rb3);
        mRb4 = (RadioButton) findViewById(R.id.rb4);

    }

    @Override
    protected void initEvent() {
        mRb1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            queryInformation(0);
        });

        mRb2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            queryInformation(1);

        });
        mRb3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            queryInformation(2);

        });
        mRb4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            queryInformation(3);
        });
    }

    @Override
    protected void initData() {
        apiService = Network.remote(ApiService.class);
        //默认显示推荐资讯
        queryInformation(0);
    }

    //按类型查询资讯
    private void queryInformation(int status) {
        apiService.searchInformation(status)
                .map(Information::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(dataBeans -> {
                    //数据显示
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, throwable -> throwable.printStackTrace());
    }


    /**
     * 资讯列表适配器
     */
    private class MyAdapter extends BaseAdapter {
        private List<Information.DataBean> dataBeans;

        public MyAdapter(List<Information.DataBean> datas) {
            this.dataBeans = datas;
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
            Information.DataBean dataBean = dataBeans.get(position);
            holder.mTvTitle.setText(dataBean.getInformationName());
            holder.mTvContent.setText(dataBean.getWords());
            Date date = new Date(dataBean.getTime());
            holder.mTvDate.setText(sdf.format(date));
            String iconPath = dataBean.getIcon();
            if (!TextUtils.isEmpty(iconPath)) {
                holder.mIvIcon.setVisibility(View.VISIBLE);
                ImageUtils.setBitmapCenterCrop(getApplicationContext(), ip + iconPath, holder.mIvIcon);
            } else {
                holder.mIvIcon.setVisibility(View.GONE);
            }

            return convertView;
        }

        class ViewHolder {
            TextView mTvTitle;
            TextView mTvContent;
            ImageView mIvIcon;
            TextView mTvDate;

            public ViewHolder(View view) {
                mTvTitle = (TextView) view.findViewById(R.id.tv_title);
                mTvContent = (TextView) view.findViewById(R.id.tv_content);
                mIvIcon = (ImageView) view.findViewById(R.id.iv_icon);
                mTvDate = (TextView) view.findViewById(R.id.tv_date);
            }
        }
    }

}
