package com.lenovo.topic12;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic12.bean.MaterialBean;
import com.lenovo.topic12.bean.ProductionLineBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_back;
    private ListView lv_list;
    private Button btn_one;
    private Button btn_two;
    private Button btn_three;
    private Button btn_four;
    private ApiService remote;
    private List<MaterialBean.DataBean> materialBeans;
    private CustomerAdapter customerAdapter;

    /**
     * 获取资源ID
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
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_list = (ListView) findViewById(R.id.lv_list);
        btn_one = (Button) findViewById(R.id.btn_one);
        btn_two = (Button) findViewById(R.id.btn_two);
        btn_three = (Button) findViewById(R.id.btn_three);
        btn_four = (Button) findViewById(R.id.btn_four);
    }

    /**
     * 初始化控件的监听
     */
    @Override
    protected void initEvent() {
        btn_one.setOnClickListener(this);
        btn_two.setOnClickListener(this);
        btn_three.setOnClickListener(this);
        btn_four.setOnClickListener(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.closeActivity();
            }
        });
    }

    /**
     * 初始话数据
     */
    @Override
    protected void initData() {
        // 实例化ApiService接口
        remote = Network.remote(ApiService.class);

        // 初始化列表数据源的集合
        materialBeans = new ArrayList<>();

        // 初始化4条生茶线的集合

        // 初始化数据适配器
        customerAdapter = new CustomerAdapter();
        lv_list.setAdapter(customerAdapter);

        // 获取所有生产线
        requestAllProductionLine();

        // 请求获取原材料详情
        requestMaterial();
    }

    /**
     * 获取所有生产线
     */
    @SuppressLint("CheckResult")
    private void requestAllProductionLine() {
        remote.getAllProductionLine()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<ProductionLineBean, List<ProductionLineBean.DataBean>>() {
                    @Override
                    public List<ProductionLineBean.DataBean> apply(ProductionLineBean productionLineBean) throws Exception {
                        for (ProductionLineBean.DataBean o : productionLineBean.getData()) {
                            switch (o.getProductionLineId()) {
                                case 1:
                                    o.setProductionLineName("轿车生产线");
                                    break;
                                case 2:
                                    o.setProductionLineName("MPV生产线");
                                    break;
                                case 3:
                                    o.setProductionLineName("SUV生产线");
                                    break;
                            }
                        }
                        return productionLineBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ProductionLineBean.DataBean>>() {
                    @Override
                    public void accept(List<ProductionLineBean.DataBean> dataBeans) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    /**
     * 获取所有原材料信息
     */
    @SuppressLint("CheckResult")
    private void requestMaterial() {
        remote.getMaterial()
                // 绑定生命周期
                .compose(this.bindToLifecycle())
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 处理请求成功的数据
                .map(new Function<MaterialBean, List<MaterialBean.DataBean>>() {
                    @Override
                    public List<MaterialBean.DataBean> apply(MaterialBean materialBean) throws Exception {
                        // 匹配适合的生产线
                        for (MaterialBean.DataBean o : materialBean.getData()) {
                            // 匹配字符串中是否存在指定字符
                            if (Pattern.compile("轿车").matcher(o.getMaterialName()).find()) {
                                o.setProductionLineClass(1);
                            } else if (Pattern.compile("MPV").matcher(o.getMaterialName()).find()) {
                                o.setProductionLineClass(2);
                            } else if (Pattern.compile("SUV").matcher(o.getMaterialName()).find()) {
                                o.setProductionLineClass(3);
                            }
                        }
                        return materialBean.getData();
                    }
                })
                // 切换到主线程刷新UI展示数据
                .observeOn(AndroidSchedulers.mainThread())
                // 回调
                .subscribe(new Consumer<List<MaterialBean.DataBean>>() {
                    @Override
                    public void accept(List<MaterialBean.DataBean> dataBeans) throws Exception {
                        // 将数据复制到集合中
                        materialBeans = dataBeans;
                        // 刷新数据适配器
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:

                break;
            case R.id.btn_two:

                break;
            case R.id.btn_three:

                break;
            case R.id.btn_four:

                break;
        }
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvId;
        private TextView tvName;
        private TextView tvNumber;
        private TextView tvRawTeaLine;
        private String productionLine = "";

        @Override
        public int getCount() {
            return materialBeans.size();
        }

        @Override
        public MaterialBean.DataBean getItem(int position) {
            return materialBeans.get(position);
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
            // 匹配生产线
            switch (getItem(position).getProductionLineClass()) {
                case 1:
                    productionLine = "轿车生产线";
                    break;
                case 2:
                    productionLine = "MPV生产线";
                    break;
                case 3:
                    productionLine = "SUV生产线";
                    break;
            }

            tvId.setText(getItem(position).getSupplyListId() + "");
            tvName.setText(getItem(position).getMaterialName());
            tvNumber.setText(getItem(position).getSize() + "");
            tvRawTeaLine.setText(productionLine);
            return view;
        }

        private void initView(View view) {
            tvId = (TextView) view.findViewById(R.id.tv_id);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvNumber = (TextView) view.findViewById(R.id.tv_number);
            tvRawTeaLine = (TextView) view.findViewById(R.id.tv_raw_tea_line);
        }
    }

}
