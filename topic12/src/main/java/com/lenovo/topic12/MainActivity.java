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
import android.widget.Toast;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic12.bean.PartBean;
import com.lenovo.topic12.bean.PartStoreBean;
import com.lenovo.topic12.bean.ProductionLineBean;
import com.lenovo.topic12.bean.ResultMessage_Material;

import java.util.ArrayList;
import java.util.List;

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
    private List<PartStoreBean.DataBean> partStoreBeans;
    private CustomerAdapter customerAdapter;
    private ArrayList<ProductionLineBean.DataBean> productionLineBeans;
    private List<PartBean.DataBean> partBeans;

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                replenishment(0);
                break;
            case R.id.btn_two:
                replenishment(1);
                break;
            case R.id.btn_three:
                replenishment(2);
                break;
            case R.id.btn_four:
                replenishment(3);
                break;
        }
    }

    /**
     * 初始话数据
     */
    @Override
    protected void initData() {
        // 实例化ApiService接口
        remote = Network.remote(ApiService.class);

        // 初始化列表数据源的集合
        partStoreBeans = new ArrayList<>();

        // 初始化4条生茶线的集合
        productionLineBeans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            productionLineBeans.add(new ProductionLineBean.DataBean(i));
        }

        // 初始化数据适配器
        customerAdapter = new CustomerAdapter();
        lv_list.setAdapter(customerAdapter);

        // 获取所有生产线
        requestAllProductionLine();
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
                            // 将有的生产线塞到对应的位置
                            for (int i = 0; i < productionLineBeans.size(); i++) {
                                if (productionLineBeans.get(i).getPosition() == o.getPosition()) {
                                    productionLineBeans.set(i, o);
                                }
                            }
                        }
                        return productionLineBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ProductionLineBean.DataBean>>() {
                    @Override
                    public void accept(List<ProductionLineBean.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "处理成功：" + productionLineBeans.toString());
                        // 查询全部原材料
                        getAllPart();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 查询全部原材料
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
                        // 查询仓库中的所有原材料
                        getAllPartStore();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误 ：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 获取仓库中所有原材料信息
     */
    @SuppressLint("CheckResult")
    private void getAllPartStore() {
        remote.getPartStore()
                // 绑定生命周期
                .compose(this.bindToLifecycle())
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 处理请求成功的数据
                .map(new Function<PartStoreBean, List<PartStoreBean.DataBean>>() {
                    @Override
                    public List<PartStoreBean.DataBean> apply(PartStoreBean partStoreBean) throws Exception {
                        for (PartStoreBean.DataBean o : partStoreBean.getData()) {
                            for (PartBean.DataBean parent : partBeans) {
                                if (parent.getId() == o.getPartId()) {
                                    o.setName(parent.getPartName());
                                    for (ProductionLineBean.DataBean productionLineBean : productionLineBeans) {
                                        if (o.getUserProductionLineId() == productionLineBean.getId()) {
                                            o.setProductionLineName(productionLineBean.getProductionLineId());
                                        }
                                    }
                                }
                            }
                        }
                        return partStoreBean.getData();
                    }
                })
                // 切换到主线程刷新UI展示数据
                .observeOn(AndroidSchedulers.mainThread())
                // 回调
                .subscribe(new Consumer<List<PartStoreBean.DataBean>>() {
                    @Override
                    public void accept(List<PartStoreBean.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "哈哈：" + dataBeans.toString());
                        // 将数据复制到集合中
                        partStoreBeans = dataBeans;
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

    /**
     * 补货
     *
     * @param postion 生产线位置
     */
    @SuppressLint("CheckResult")
    private void replenishment(int postion) {
        remote.getResultMessage_Material(productionLineBeans.get(postion).getId())
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(ResultMessage_Material::getMessage)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "请求成功：" + s);
                        if (s.equals("仓库材料不足")) {
                            s = "仓库材料不足";
                        } else if (s.equals("该生产线不属于本工厂，无法操作")) {
                            s = "该车间没有生产线，无法补货";
                        } else if (s.equals("补充材料成功")) {
                            getAllPartStore();
                        }
                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "出现错误：" + throwable.getMessage());
                    }
                });
    }

    class CustomerAdapter extends BaseAdapter {
        private TextView tvId;
        private TextView tvName;
        private TextView tvNumber;
        private TextView tvRawTeaLine;
        private String productionLine = "";

        @Override
        public int getCount() {
            return partStoreBeans.size();
        }

        @Override
        public PartStoreBean.DataBean getItem(int position) {
            return partStoreBeans.get(position);
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

            tvId.setText(getItem(position).getPartId() + "");
            tvName.setText(getItem(position).getName());
            tvNumber.setText(getItem(position).getNum() + "");
            tvRawTeaLine.setText(getItem(position).getProductionLineName());
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
