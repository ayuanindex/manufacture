package com.lenovo.topic16;

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

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ListView mLv1;
    private ListView mLv2;
    private ApiSevices apiSevices;
    private Map<Integer, Bean> data = new HashMap<>();

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mLv1 = (ListView) findViewById(R.id.lv1);
        mLv2 = (ListView) findViewById(R.id.lv2);
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
        //查询问题车辆和成品车辆
        queryCar();

    }

    @SuppressWarnings("CheckResult")
    private void queryCar() {
        //全部问题车辆，显示在左侧列表
        apiSevices.getAllUserQuestion()
                .map(CarBean::getData)
                .map(dataBeans -> {
                    for (int i = 0; i < dataBeans.size(); i++) {
                        CarBean.DataBean dataBean = dataBeans.get(i);
                        int carId = dataBean.getCarId();
                        String carName = data.get(carId).getCarName();
                        //设置车型
                        dataBean.setType(carName);
                        dataBean.setDesc("维修");
                    }
                    return dataBeans;
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    mLv1.setAdapter(new MyAdapterUserQuestion(dataBeans));
                }, throwable -> throwable.printStackTrace());

        //全部维修车辆
        Observable<List<CarBean.DataBean>> repairCarStoreObservable = apiSevices.getAllUserRepairCarStore()
                .map(CarBean::getData)
                .map(dataBeans -> {
                    for (int i = 0; i < dataBeans.size(); i++) {
                        CarBean.DataBean dataBean = dataBeans.get(i);
                        int carId = dataBean.getCarId();
                        String carName = data.get(carId).getCarName();
                        //设置车型
                        dataBean.setType(carName);
                        dataBean.setDesc("维修");
                    }
                    return dataBeans;
                });
        //全部正常车辆
        Observable<List<CarBean.DataBean>> normalCarStoreObservable = apiSevices.getAllUserNormalCarStore().map(CarBean::getData)
                .map(dataBeans -> {
                    for (int i = 0; i < dataBeans.size(); i++) {
                        CarBean.DataBean dataBean = dataBeans.get(i);
                        int carId = dataBean.getCarId();
                        String carName = data.get(carId).getCarName();
                        //设置车型
                        dataBean.setType(carName);
                        dataBean.setDesc("正常");
                    }
                    return dataBeans;
                });

        //全部维修车辆和正常车辆，显示在右侧列表
        Observable
                .zip(repairCarStoreObservable, normalCarStoreObservable, ((dataBeans, dataBeans2) -> {
                    dataBeans.addAll(dataBeans2);
                    return dataBeans;
                }))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBeans -> {
                    mLv2.setAdapter(new MyAdapterAllCar(dataBeans));
                }, throwable -> throwable.printStackTrace())
        ;
    }

    private class MyAdapterUserQuestion extends BaseAdapter {

        private List<CarBean.DataBean> dataBeans;
        //记录查询过的维修费用，检查查询次数
        private HashMap<Integer, Integer> dataPrice = new HashMap<>();

        public MyAdapterUserQuestion(List<CarBean.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        public List<CarBean.DataBean> getDataBeans() {
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
                convertView = View.inflate(MainActivity.this, R.layout.item1, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CarBean.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getId() + "");
            holder.mTv2.setText(dataBean.getType() + "");
            Integer price = dataPrice.get(dataBean.getId());
            if (price == null) {
                apiSevices.getCarInfo(dataBean.getCarId())
                        .map(CarInfo::getData)
                        .map(dataBeans1 -> dataBeans1.get(0))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(bean -> {
                            holder.mTv3.setText(bean.getGold() + "");
                            dataPrice.put(dataBean.getCarId(), bean.getGold());
                        })
                ;
            } else {
                //维修费用
                holder.mTv3.setText(dataPrice.get(dataBean.getCarId()) + "");
            }
            //维修按钮
            holder.mBtn.setOnClickListener(v -> {
                apiSevices.repairCar(dataBean.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(carBean -> {
                            //维修成功更新两个列表
                            queryCar();
                        }, throwable -> throwable.printStackTrace());
            });
            return convertView;
        }

        private class ViewHolder {
            private TextView mTv1;
            private TextView mTv2;
            private TextView mTv3;
            private Button mBtn;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);
                mBtn = (Button) view.findViewById(R.id.btn);
            }
        }

    }

    private class MyAdapterAllCar extends BaseAdapter {

        private List<CarBean.DataBean> dataBeans;

        public MyAdapterAllCar(List<CarBean.DataBean> dataBeans) {
            this.dataBeans = dataBeans;
        }

        public List<CarBean.DataBean> getDataBeans() {
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
                convertView = View.inflate(MainActivity.this, R.layout.item2, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            CarBean.DataBean dataBean = dataBeans.get(position);
            holder.mTv1.setText(dataBean.getType() + "");
            holder.mTv2.setText(dataBean.getNum() + "");
            holder.mTv3.setText(dataBean.getDesc() + "");
            return convertView;
        }

        private class ViewHolder {
            private TextView mTv1;
            private TextView mTv2;
            private TextView mTv3;

            public ViewHolder(View view) {
                mTv1 = (TextView) view.findViewById(R.id.tv1);
                mTv2 = (TextView) view.findViewById(R.id.tv2);
                mTv3 = (TextView) view.findViewById(R.id.tv3);

            }
        }
    }


}
