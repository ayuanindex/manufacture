package com.lenovo.topic6;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic6.bean.AllProductionLineBean;
import com.lenovo.topic6.bean.LineClassBean;
import com.lenovo.topic6.bean.ResultMessageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic6
 * @ClassName: MainActivity
 * @Author: AYuan
 * @CreateDate: 2020/1/18 18:34
 */
public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private ListView lv_production_line;
    private CustomerAdapter customerAdapter;
    private HashMap<String, Integer> hashMap;
    private ApiService remote;
    private ArrayList<Disposable> disposables;
    private ArrayList<LineClassBean> lineClassBeans;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_production_line = (ListView) findViewById(R.id.lv_production_line);
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭当前界面
                MainActivity.this.closeActivity();
                Log.i(TAG, "onClick: 当前页面已关闭");
            }
        });
    }

    @Override
    protected void initData() {
        // 实例化ApiService
        remote = Network.remote(ApiService.class);
        // 存储订阅的集合
        disposables = new ArrayList<>();
        // 初始化四条生产线模型数据
        lineClassBeans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            lineClassBeans.add(new LineClassBean(i));
        }
        // 创建数据适配器
        customerAdapter = new CustomerAdapter();
        // 将数据适配器设置进ListView中
        lv_production_line.setAdapter(customerAdapter);
        // 生产线类型对应ID的map集合
        hashMap = new HashMap<>();
        hashMap.put("轿车生产线", 1);
        hashMap.put("MPV生产线", 2);
        hashMap.put("SUV生产线", 3);

        // 获取所有已经存在的生产线
        getAllProductionLine();
    }

    /**
     * 获取所有生产线的网络请求方法
     */
    private void getAllProductionLine() {
        Disposable getAllLine = remote.getAllProductionLine()
                .subscribeOn(Schedulers.io())
                .map(new Function<AllProductionLineBean, List<AllProductionLineBean.LineBean>>() {
                    @Override
                    public List<AllProductionLineBean.LineBean> apply(AllProductionLineBean allProductionLineBean) throws Exception {
                        return allProductionLineBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AllProductionLineBean.LineBean>>() {
                    @Override
                    public void accept(List<AllProductionLineBean.LineBean> lineBeans) throws Exception {
                        Log.i(TAG, "accept: 请求成功" + lineBeans.size() + "lll" + lineBeans.toString());
                        for (AllProductionLineBean.LineBean lineBean : lineBeans) {
                            customerAdapter.getItem(lineBean.getPosition()).update(lineBean);
                            Log.i(TAG, "accept: 修改" + customerAdapter.getItem(lineBean.getPosition()).toString());
                        }
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: 网络出现错误" + throwable.getMessage());
                    }
                });
        disposables.add(getAllLine);
    }

    /**
     * 创建生产线的网络请求方法
     *
     * @param lineClass 生产线类型
     * @param position  新的生产线的目标位置
     */
    private void createProductionLine(Integer lineClass, Integer position) {
        Log.i(TAG, "createProductionLine: " + lineClass + "位置：" + position);
        Disposable subscribe = remote.getResult(lineClass, position)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultMessageBean>() {
                    @Override
                    public void accept(ResultMessageBean resultMessageBean) throws Exception {
                        String message = resultMessageBean.getMessage();
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "accept: 请求成功" + resultMessageBean.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: 网络请求发生错误" + throwable.getMessage());
                    }
                });
        disposables.add(subscribe);
    }

    /**
     * ListView的数据适配器
     */
    class CustomerAdapter extends BaseAdapter {
        private TextView tvMpv;
        private TextView tvCar;
        private TextView tvSuv;

        @Override
        public int getCount() {
            return lineClassBeans.size();
        }

        @Override
        public LineClassBean getItem(int position) {
            return lineClassBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_list, null);
            } else {
                view = convertView;
            }
            initView(view);
            // 将对应的颜色设置进去
            tvMpv.setBackgroundColor(Color.parseColor(getColor(getItem(position).isMpvIsSelect())));
            tvCar.setBackgroundColor(Color.parseColor(getColor(getItem(position).isCarIsSelect())));
            tvSuv.setBackgroundColor(Color.parseColor(getColor(getItem(position).isSuvIsSelect())));

            // 设置点击事件
            tvMpv.setOnClickListener(null);
            tvMpv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 判断当前位置的生产线类型是否创建
                    tvMpv.setBackgroundColor(Color.parseColor(getColor(getItem(position).isMpvIsSelect())));
                    createProductionLine(hashMap.get(tvMpv.getText().toString().trim()), getItem(position).getPosition());
                }
            });

            tvSuv.setOnClickListener(null);
            tvSuv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvSuv.setBackgroundColor(Color.parseColor(getColor(getItem(position).isSuvIsSelect())));
                    createProductionLine(hashMap.get(tvSuv.getText().toString().trim()), getItem(position).getPosition());
                }
            });

            tvCar.setOnClickListener(null);
            tvCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvCar.setBackgroundColor(Color.parseColor(getColor(getItem(position).isCarIsSelect())));
                    createProductionLine(hashMap.get(tvCar.getText().toString().trim()), getItem(position).getPosition());
                }
            });
            Log.i(TAG, "getView: " + getItem(position).toString());
            return view;
        }

        private void initView(View view) {
            tvMpv = (TextView) view.findViewById(R.id.tv_mpv);
            tvCar = (TextView) view.findViewById(R.id.tv_car);
            tvSuv = (TextView) view.findViewById(R.id.tv_suv);
        }

        private String getColor(boolean isSelect) {
            return isSelect ? "#FC4148" : "#6F96FE";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
