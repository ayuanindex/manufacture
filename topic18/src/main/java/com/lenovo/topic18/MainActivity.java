package com.lenovo.topic18;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private TextView mTvPrice;
    private ListView mLv;

    private ApiSevices apiSevices;
    private int workId = 1;//默认的工厂id
    private int workMoney = 0;//工厂资金
    private Map<Integer, Bean> data = new HashMap<>();

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTvPrice = (TextView) findViewById(R.id.tv_price);
        mLv = (ListView) findViewById(R.id.lv);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initData() {
        //创建车辆型号
        data.put(1, new Bean(1, "轿车汽车", "轿车汽车标准型", 2000));
        data.put(2, new Bean(2, "MPV汽车", "MPV汽车标准型", 3000));
        data.put(3, new Bean(3, "SUV汽车", "SUV汽车标准型", 4000));
        apiSevices = Network.remote(ApiSevices.class);
        //查询工厂金币
        queryUserWorkInfo();
        //查询全部维修车辆
        queryAllCar();
    }

    //查询全部维修车辆
    @SuppressWarnings("CheckResult")
    private void queryAllCar() {
        apiSevices.getAllUserRepairCarStore()
                .map(UserRepairCarStoreGetAll::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    //设置列表适配器
                    mLv.setAdapter(new MyAdapter(dataBeans));
                }, throwable -> throwable.printStackTrace());
    }


    //查询工厂金币
    @SuppressWarnings("CheckResult")
    private void queryUserWorkInfo() {
        apiSevices.getInfoUserWorkInfo(workId)
                .map(UserWorkInfo::getData)
                .map(dataBeans -> {
                    //获取工厂金币
                    if (dataBeans != null && dataBeans.size() > 0) {
                        return dataBeans.get(0).getPrice();
                    }
                    return -1;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    workMoney = integer;
                    mTvPrice.setText("工厂资金：" + integer);
                }, throwable -> throwable.printStackTrace());
    }


    private class MyAdapter extends BaseAdapter {

        private List<UserRepairCarStoreGetAll.DataBean> dataBeans;

        public MyAdapter(List<UserRepairCarStoreGetAll.DataBean> dataBeans) {
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
            UserRepairCarStoreGetAll.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(data.get(dataBean.getCarId()).getCarName());
            holder.mTv3.setText(dataBean.getNum() + "");
            holder.mTv4.setText("维修");
            //进行卖出的操作
            holder.mTv5.setOnClickListener(v -> {
                //根据id删除对应的车辆，卖出操作
                apiSevices.deleteUserRepairCarStore(dataBean.getId())
                        .map(DeleteUserRepairCarStore::getData)
                        //修改金额
                        .flatMap(dataBeans -> apiSevices.updatePriceUserWorkInfo(workId, workMoney + data.get(dataBean.getCarId()).getGold() * dataBean.getNum()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(userWorkInfoUpdatePrice -> {
                            //重新查询工厂金额
                            queryUserWorkInfo();
                            //更新列表数据
                            dataBeans.remove(dataBean);
                            notifyDataSetChanged();
                        }, throwable -> throwable.printStackTrace());
            });
            return convertView;
        }

        class ViewHolder {
            TextView mTv1;
            TextView mTv2;
            TextView mTv3;
            TextView mTv4;
            Button mTv5;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);
                mTv4 = (TextView) view.findViewById(R.id.tv4);
                mTv5 = (Button) view.findViewById(R.id.btn);
            }
        }
    }
}
