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
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic11.bean.Material;
import com.lenovo.topic11.bean.ResultMessage_CreateProductionLine;
import com.lenovo.topic11.bean.ResultMessage_Store;
import com.lenovo.topic11.bean.SearchProductionLineBean;
import com.lenovo.topic11.bean.WorkInfoBean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Scheduler;
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
    private int productionLineId;
    private int workPrice;

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

        //-------------------获取数据------------------
        // 获取指定位置的生产线
        getProductionLine();
        // 获取车间的资金
        getWorkInfo();
        // 获取原材料详情
        getMaterial();
    }

    /**
     * 获取指定位置的生产线
     */
    @SuppressLint("CheckResult")
    private void getProductionLine() {
        remote.getProductionLine(3)
                // 绑定生命周期
                .compose(this.bindToLifecycle())
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 类型转换处理数据
                .map(SearchProductionLineBean::getData)
                // 切换到主线程对数据进行展示
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅网络请求状态
                .subscribe(new Consumer<List<SearchProductionLineBean.DataBean>>() {
                    @Override
                    public void accept(List<SearchProductionLineBean.DataBean> dataBeans) throws Exception {
                        if (dataBeans.size() == 0) {
                            Log.i(TAG, "生产线不存在");
                            // 创建生产线
                            createProductionLine();
                        } else {
                            // 拿到生产线ID
                            productionLineId = dataBeans.get(0).getId();
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
     * 创建生产线
     */
    @SuppressLint("CheckResult")
    private void createProductionLine() {
        remote.createProduction(3, 3)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<ResultMessage_CreateProductionLine, String>() {
                    @Override
                    public String apply(ResultMessage_CreateProductionLine resultMessage_createProductionLine) throws Exception {
                        return resultMessage_createProductionLine.getMessage();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (s.equals("创建学生生产线成功") || s.equals("该位置已存在生产线")) {
                            // 重新获取当前生产线ID
                            getProductionLine();
                        } else {
                            // 重新创建生产线
                            createProductionLine();
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
     * 获取工厂信息
     */
    @SuppressLint("CheckResult")
    private void getWorkInfo() {
        remote.getWorkInfo(1)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<WorkInfoBean, WorkInfoBean.DataBean>() {
                    @Override
                    public WorkInfoBean.DataBean apply(WorkInfoBean workInfoBean) throws Exception {
                        return workInfoBean.getData().get(0);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WorkInfoBean.DataBean>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(WorkInfoBean.DataBean dataBean) throws Exception {
                        workPrice = dataBean.getPrice();
                        tv_funds.setText("工厂资金：" + workPrice + "");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
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
                    // 购入原材料
                    addMaterialStore(getItem(position));
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

        @SuppressLint("CheckResult")
        private void addMaterialStore(Material.DataBean item) {
            if (item.getSize() * item.getPrice() > workPrice) {
                Toast.makeText(MainActivity.this, "资金不足", Toast.LENGTH_SHORT).show();
                return;
            }
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("userLineId", productionLineId);
            hashMap.put("num", item.getSize());
            hashMap.put("supplyListId", item.getSupplyListId());
            remote.addMaterialStore(hashMap)
                    .compose(MainActivity.this.bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .map(ResultMessage_Store::getMessage)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<String>() {
                        @Override
                        public void accept(String s) throws Exception {
                            Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                            // 重新查询工厂资金
                            workPrice = workPrice - (item.getSize() * item.getPrice());
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Log.i(TAG, "出现错误：" + throwable.getMessage());
                        }
                    });
        }
    }
}
