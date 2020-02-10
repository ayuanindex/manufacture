package com.lenovo.topic11;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic11.bean.Material;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private TextView tv_funds;
    private ListView lv_list;
    private ApiService remote;
    private List<Material.DataBean> materialBeans;
    private DecimalFormat decimalFormat;
    private CustomerAdapter customerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * 获取资源文件ID
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
        tv_funds = (TextView) findViewById(R.id.tv_funds);
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
     * 初始化大致逻辑
     */
    @Override
    protected void initData() {
        // 实例化ApiService
        remote = Network.remote(ApiService.class);

        // 数字格式化类
        decimalFormat = new DecimalFormat("##,###");

        // 初始化ListView的数据集合
        materialBeans = new ArrayList<>();

        // 给ListView设置数据适配器
        customerAdapter = new CustomerAdapter();
        lv_list.setAdapter(customerAdapter);

        // 获取原材料详情
        getMaterial();
    }

    /**
     * 获取原料详情
     */
    @SuppressLint("CheckResult")
    private void getMaterial() {
        remote.getMaterial()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<Material, List<Material.DataBean>>() {
                    @Override
                    public List<Material.DataBean> apply(Material material) throws Exception {
                        return material.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Material.DataBean>>() {
                    @Override
                    public void accept(List<Material.DataBean> dataBeans) throws Exception {
                        materialBeans = dataBeans;
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
        private TextView tvCommodity;
        private TextView tvPrice;
        private TextView tvNumber;
        private TextView tvSupplier;
        private Button btnBuy;

        @Override
        public int getCount() {
            return materialBeans.size();
        }

        @Override
        public Material.DataBean getItem(int position) {
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

            tvCommodity.setText(getItem(position).getMaterialName());
            tvPrice.setText(decimalFormat.format(getItem(position).getPrice()) + "");
            tvNumber.setText(getItem(position).getSize() + "");
            tvSupplier.setText(getItem(position).getContent());

            btnBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2020-02-10
                    Log.i(TAG, "购买");
                }
            });
            return view;
        }

        private void initView(View view) {
            tvCommodity = (TextView) view.findViewById(R.id.tv_commodity);
            tvPrice = (TextView) view.findViewById(R.id.tv_price);
            tvNumber = (TextView) view.findViewById(R.id.tv_number);
            tvSupplier = (TextView) view.findViewById(R.id.tv_supplier);
            btnBuy = (Button) view.findViewById(R.id.btn_buy);
        }
    }

}
