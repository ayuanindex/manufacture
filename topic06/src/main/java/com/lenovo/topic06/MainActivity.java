package com.lenovo.topic06;

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
import com.lenovo.topic06.bean.AllProductionLineBean;
import com.lenovo.topic06.bean.LineClassBean;
import com.lenovo.topic06.bean.ResultMessageBean;

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

    /**
     * @return 返回布局文件的ID
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
        lv_production_line = (ListView) findViewById(R.id.lv_production_line);
    }

    /**
     * 初始化控件监听
     */
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

    /**
     * 初始化需要用到的类
     */
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

        // 获取所有已经存在的生产线，并同步到控件中
        getAllProductionLine();
    }

    /**
     * 获取所有生产线的网络请求方法
     */
    private void getAllProductionLine() {
        Disposable getAllLine = remote.getAllProductionLine()
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 将需要的数据集合提取出来
                .compose(this.bindToLifecycle())
                .map(new Function<AllProductionLineBean, List<AllProductionLineBean.LineBean>>() {
                    @Override
                    public List<AllProductionLineBean.LineBean> apply(AllProductionLineBean allProductionLineBean) throws Exception {
                        return allProductionLineBean.getData();
                    }
                })
                // 切换到主线程将获取到的数据填充到集合中年
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅网络请求状态
                .subscribe(new Consumer<List<AllProductionLineBean.LineBean>>() {// 请求成功
                    @Override
                    public void accept(List<AllProductionLineBean.LineBean> lineBeans) throws Exception {
                        // 循环找到对应位置的对象设置显示或者隐藏
                        for (AllProductionLineBean.LineBean lineBean : lineBeans) {
                            // 拿到指定位置的对象，对对象的属性进行更新
                            customerAdapter.getItem(lineBean.getPosition()).update(lineBean);
                        }
                        // 刷新集合
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {// 请求失败
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: 网络出现错误" + throwable.getMessage());
                    }
                });
        // 将订阅添加到订阅集合中
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
                .compose(this.bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultMessageBean>() {
                    @Override
                    public void accept(ResultMessageBean resultMessageBean) throws Exception {
                        // 获取请求接口返回的消息
                        String message = resultMessageBean.getMessage();
                        // 弹出Toast提示用户
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (!resultMessageBean.getMessage().equals("该位置已存在生产线")) {
                            switch (lineClass) {
                                case 1:
                                    customerAdapter.getItem(position).setCarIsSelect(true);
                                    break;
                                case 2:
                                    customerAdapter.getItem(position).setMpvIsSelect(true);
                                    break;
                                case 3:
                                    customerAdapter.getItem(position).setSuvIsSelect(true);
                                    break;
                            }
                        }
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: 网络请求发生错误" + throwable.getMessage());
                    }
                });
        // 将订阅添加到订阅集合中，在页面关闭是一起销毁
        disposables.add(subscribe);
    }

    /**
     * 获取需要显示的颜色
     *
     * @param isSelect true表示红色，false表示蓝色（两种选择状态）
     * @return 返回16进制的颜色值
     */
    private String getColor(boolean isSelect) {
        return isSelect ? "#FC4148" : "#6F96FE";
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
            tvMpv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createProductionLine(hashMap.get(tvMpv.getText().toString().trim()), position);
                }
            });

            tvSuv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createProductionLine(hashMap.get(tvSuv.getText().toString().trim()), position);
                }
            });

            tvCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createProductionLine(hashMap.get(tvCar.getText().toString().trim()), position);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消订阅
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
    }
}
