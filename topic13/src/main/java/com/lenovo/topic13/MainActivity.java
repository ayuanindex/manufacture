package com.lenovo.topic13;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic13.bean.Material;
import com.lenovo.topic13.bean.PartBean;
import com.lenovo.topic13.bean.ProductionLineBean;
import com.lenovo.topic13.bean.ResultMessageIsCreateProduction;
import com.lenovo.topic13.bean.UserPart;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_land_occupation;
    private GridView gv_list;
    private ApiService remote;
    private TextView tv_status;
    private List<PartBean.DataBean> partBeans;
    private int productionLineId;
    private ArrayList<Material> materials;
    private CustomerAdapter customerAdapter;

    /**
     * @return 资源文件ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化布局控件
     */
    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_land_occupation = (TextView) findViewById(R.id.tv_land_occupation);
        gv_list = (GridView) findViewById(R.id.gv_list);
        tv_status = (TextView) findViewById(R.id.tv_status);
    }

    /**
     * 初始话控件的监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.closeActivity();
            }
        });
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        // 实例化ApiService
        remote = Network.remote(ApiService.class);

        // 用于填充GridView的集合
        materials = new ArrayList<>();

        // 初始化GridView
        customerAdapter = new CustomerAdapter();
        gv_list.setAdapter(customerAdapter);

        // 查询生产线
        getProductionIsPosition(3);
    }

    /**
     * 获取指定位置的生产线
     *
     * @param position
     */
    @SuppressLint("CheckResult")
    private void getProductionIsPosition(int position) {
        remote.getProductionLineIsPosition(position)
                // 将请求订阅和生命周期绑定起来
                .compose(this.bindToLifecycle())
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 提取转换处理自己想要的数据
                .map(new Function<ProductionLineBean, List<ProductionLineBean.DataBean>>() {
                    @Override
                    public List<ProductionLineBean.DataBean> apply(ProductionLineBean productionLineBean) throws Exception {
                        return productionLineBean.getData();
                    }
                })
                // 切换到主线程操作
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ProductionLineBean.DataBean>>() {
                    @Override
                    public void accept(List<ProductionLineBean.DataBean> dataBeans) throws Exception {
                        // 判断4号生产线是否存在
                        if (dataBeans.size() == 0) {
                            // 生产线不存在，需要重新创建
                            createProductionLine();
                            setStatus(true);
                        } else {
                            // 获取4号生产线的ID
                            productionLineId = dataBeans.get(0).getId();
                            setStatus(false);
                            // 获取所有原料的信息
                            getAllPart();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 查询全部原料信息
     */
    @SuppressLint("CheckResult")
    private void getAllPart() {
        remote.getAllPart()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(PartBean::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PartBean.DataBean>>() {
                    @Override
                    public void accept(List<PartBean.DataBean> dataBeans) throws Exception {
                        partBeans = dataBeans;
                        // 获取当前生产线的备料
                        getProductionPart();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 获取当前生产线的备料
     */
    @SuppressLint("CheckResult")
    private void getProductionPart() {
        remote.getUserPart(productionLineId)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<UserPart, List<Material>>() {
                    @Override
                    public List<Material> apply(UserPart userPart) throws Exception {
                        // 将数据整理成为自己想要的数据
                        for (UserPart.DataBean datum : userPart.getData()) {
                            for (PartBean.DataBean partBean : partBeans) {
                                if (datum.getPartId() == partBean.getId()) {
                                    if (!find(datum, partBean)) {
                                        materials.add(new Material(
                                                getResources().getIdentifier(partBean.getIcon().toLowerCase(), "drawable", "com.lenovo.topic13"),
                                                partBean.getIcon(),
                                                partBean.getPartName(),
                                                datum.getNum(),
                                                (partBean.getArea() * datum.getNum()),
                                                datum
                                        ));
                                    }
                                    break;
                                }
                            }
                        }
                        return materials;
                    }

                    /**
                     * 检查同样的原料是否已经存在
                     * @param datum
                     * @param partBean
                     * @return
                     */
                    private boolean find(UserPart.DataBean datum, PartBean.DataBean partBean) {
                        for (Material material : materials) {
                            if (material.getUserPart().equals(datum)) {
                                // 如果存在，则叠加
                                int sum = material.getSum() + datum.getNum();
                                material.setSum(sum);
                                material.setArea(sum * partBean.getArea());
                                return true;
                            }
                        }
                        return false;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Material>>() {
                    @Override
                    public void accept(List<Material> materials) throws Exception {
                        // 统计总占地
                        int sum = 0;
                        for (Material material : materials) {
                            sum += material.getArea();
                        }
                        tv_land_occupation.setText("占地：" + sum);
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 在指定位置创建生产线
     */
    @SuppressLint("CheckResult")
    private void createProductionLine() {
        remote.createProductionLine(1, 3)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ResultMessageIsCreateProduction::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                        // 这里因为不知道返回数据长什么样所有只能用这个字符串查询的方法来替代
                        if (Pattern.compile("成功").matcher(s).find()) {
                            // 再次获取当前的生产线
                            getProductionIsPosition(3);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 设置状态
     *
     * @param b true表示没有备料
     */
    private void setStatus(boolean b) {
        if (b) {
            gv_list.setVisibility(View.GONE);
            tv_status.setVisibility(View.VISIBLE);
        } else {
            gv_list.setVisibility(View.VISIBLE);
            tv_status.setVisibility(View.GONE);
        }
    }

    class CustomerAdapter extends BaseAdapter {
        private ImageView ivLogo;
        private TextView tvName;
        private TextView tvNum;

        @Override
        public int getCount() {
            return materials.size();
        }

        @Override
        public Material getItem(int position) {
            return materials.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item, null);
            } else {
                view = convertView;
            }

            initView(view);
            ivLogo.setImageResource(getItem(position).getIcon());
            tvName.setText(getItem(position).getName());
            tvNum.setText("X" + getItem(position).getSum());
            return view;
        }

        private void initView(View view) {
            ivLogo = (ImageView) view.findViewById(R.id.iv_logo);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvNum = (TextView) view.findViewById(R.id.tv_num);
        }
    }

}
