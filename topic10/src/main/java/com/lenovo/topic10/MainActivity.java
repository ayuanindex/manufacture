package com.lenovo.topic10;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic10.bean.MaterialBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private ListView lv_list;
    private ApiService remote;
    private List<MaterialBean.DataBean> materialBeans;
    private CustomerAdapter customerAdapter;
    private DecimalFormat decimalFormat;

    /**
     * @return 返回资源文件ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化布局中的控件
     */
    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_list = (ListView) findViewById(R.id.lv_list);
    }

    /**
     * 初始化控件的监听
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
     * 数据初始执行方法
     */
    @Override
    protected void initData() {
        // 实例化ApiService接口类，获取设置好的接口
        remote = Network.remote(ApiService.class);

        // 数字格式化
        decimalFormat = new DecimalFormat("#,###");

        // 初始化填充ListView的集合
        materialBeans = new ArrayList<>();

        // 初始化ListView
        customerAdapter = new CustomerAdapter();
        lv_list.setAdapter(customerAdapter);

        // 获取原材料详情
        getMaterial();
    }

    @SuppressLint("CheckResult")
    private void getMaterial() {
        remote.getMaterial()
                // 绑定声明周期
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<MaterialBean, List<MaterialBean.DataBean>>() {
                    @Override
                    public List<MaterialBean.DataBean> apply(MaterialBean materialBean) throws Exception {
                        // 算出商品单价
                        for (MaterialBean.DataBean bean : materialBean.getData()) {
                            bean.setUnitPrice(bean.getPrice() / bean.getSize());
                        }
                        return materialBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<MaterialBean.DataBean>>() {
                    @Override
                    public void accept(List<MaterialBean.DataBean> dataBeans) throws Exception {
                        // 将数据填充到适配器所用的集合中
                        materialBeans = dataBeans;
                        // 刷新列表
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView commodity;
        private TextView tvPrice;
        private TextView tvNumber;
        private TextView tvUnitprice;
        private TextView tvSupplier;

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
                view = View.inflate(MainActivity.this, R.layout.item_list, null);
            } else {
                view = convertView;
            }
            initView(view);
            commodity.setText(getItem(position).getMaterialName());
            tvPrice.setText(decimalFormat.format(getItem(position).getPrice()) + "");
            tvNumber.setText(getItem(position).getSize() + "");
            tvUnitprice.setText(decimalFormat.format(getItem(position).getUnitPrice()));
            tvSupplier.setText(getItem(position).getContent());
            return view;
        }

        private void initView(View view) {
            commodity = (TextView) view.findViewById(R.id.tv_commodity);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvNumber = (TextView) view.findViewById(R.id.tv_number);
            tvUnitprice = (TextView) view.findViewById(R.id.tv_unitprice);
            tvSupplier = (TextView) view.findViewById(R.id.tv_supplier);
        }
    }
}
