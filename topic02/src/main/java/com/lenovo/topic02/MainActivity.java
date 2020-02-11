package com.lenovo.topic02;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic02.bean.FactoryEnvironment;
import com.lenovo.topic02.bean.UpdateAcOnOff;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ImageView ivBack;
    private TextView tvEnvironment;
    private TextView tvWorkshop;
    private ImageView ivCold;
    private ImageView ivHot;
    private Button btnColdWind;
    private Button btnHotAir;
    private ImageButton ivbtnClose;
    private ApiService remote;
    private ArrayList<Disposable> disposables;
    private String acOnOff;

    /**
     * 获取布局
     *
     * @return 返回布局文件的ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化界面控件
     */
    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvEnvironment = (TextView) findViewById(R.id.tv_environment);
        tvWorkshop = (TextView) findViewById(R.id.tv_workshop);
        ivCold = (ImageView) findViewById(R.id.iv_cold);
        ivHot = (ImageView) findViewById(R.id.iv_hot);
        btnColdWind = (Button) findViewById(R.id.btn_cold_wind);
        btnHotAir = (Button) findViewById(R.id.btn__hot_air);
        ivbtnClose = (ImageButton) findViewById(R.id.ivbtn_close);
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initEvent() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.closeActivity();
            }
        });

        btnColdWind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acOnOff = "1";
                replaceImage();
                acControl();
            }
        });

        btnHotAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acOnOff = "2";
                replaceImage();
                acControl();
            }
        });

        ivbtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acOnOff = "0";
                replaceImage();
                acControl();
            }
        });
    }

    /**
     * 初始化需要使用的类
     */
    @Override
    protected void initData() {
        // 将Api接口实例化
        remote = Network.remote(ApiService.class);
        // 创建一个订阅列表
        disposables = new ArrayList<>();
        // 通过接口访问当前工厂内外的环境
        factoryEnvironment();
    }

    /**
     * 控制空调的网络请求方法
     */
    private void acControl() {
        Disposable subscribe = remote.getFactoryEnvironment(1, acOnOff)
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 切换到主线程对结果进行处理
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅网络请求的成功和失败
                .subscribe(new Consumer<UpdateAcOnOff>() {// 请求成功
                    @Override
                    public void accept(UpdateAcOnOff updateAcOnOff) throws Exception {
                        Log.i(TAG, "哈哈：" + updateAcOnOff.getMessage());
                    }
                }, new Consumer<Throwable>() {// 请求失败
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "哈哈:出现错误" + throwable.getMessage());
                    }
                });
        // 将订阅添加到订阅集合
        disposables.add(subscribe);
    }

    /**
     * 获取当前工厂环境的网络请求方法
     * 5秒刷新一次
     */
    private void factoryEnvironment() {
        // 使用RxJava提供的方法急性周期循环
        // @field1：延时X秒之后执行；@field2：表示循环周期的间隔时间；@field3：表示计时单位
        Disposable factory = Observable.interval(0, 5, TimeUnit.SECONDS)
                // 周期循环所执行的方法
                .doOnNext(new Consumer<Long>() {

                    /**
                     * 获取空调状态的订阅
                     */
                    private Disposable subscribe;

                    /**
                     * @param aLong 循环次数
                     * @throws Exception 抛出的异常
                     */
                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 把之前的取消订阅
                        if (subscribe != null && !subscribe.isDisposed()) {
                            subscribe.dispose();
                        }
                        subscribe = remote.getFactoryEnvironment(1)
                                // 切换到子线程进行网络请求
                                .subscribeOn(Schedulers.io())
                                // 对请求成功的数据进行变换
                                .map(new Function<FactoryEnvironment, List<FactoryEnvironment.DataBeanList>>() {
                                    /**
                                     * 拿到JavaBean中的集合
                                     * @param factoryEnvironment 请求成功后接收数据的javaBean
                                     * @return 返回当前JavaBean中的数据集合
                                     * @throws Exception 抛出异常
                                     */
                                    @Override
                                    public List<FactoryEnvironment.DataBeanList> apply(FactoryEnvironment factoryEnvironment) throws Exception {
                                        return factoryEnvironment.getData();
                                    }
                                })
                                // 切换到主线程更新界面
                                .observeOn(AndroidSchedulers.mainThread())
                                // 订阅网络请求的状态
                                .subscribe(new Consumer<List<FactoryEnvironment.DataBeanList>>() {// 请求成功
                                    @Override
                                    public void accept(List<FactoryEnvironment.DataBeanList> dataBeanLists) throws Exception {
                                        Log.i(TAG, "哈哈：" + dataBeanLists.toString());
                                        // 将数据填充到控件上
                                        tvEnvironment.setText(dataBeanLists.get(0).getOutTemp());
                                        tvWorkshop.setText(dataBeanLists.get(0).getWorkshopTemp());
                                        // 每次刷新同步空调状态
                                        acOnOff = dataBeanLists.get(0).getAcOnOff();
                                        replaceImage();
                                    }
                                }, new Consumer<Throwable>() {// 请求失败
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.i(TAG, "连接出现错误");
                                    }
                                });
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
        // 将订阅添加到订阅集合，界面销毁时一起取消订阅
        disposables.add(factory);
    }

    /**
     * 切换点击按钮之后遥控器上图标状态图片
     */
    private void replaceImage() {
        switch (acOnOff) {
            case "0":
                ivCold.setImageResource(R.drawable.cold0002);
                ivHot.setImageResource(R.drawable.hot0001);
                break;
            case "1":
                ivCold.setImageResource(R.drawable.cold0001);
                ivHot.setImageResource(R.drawable.hot0001);
                break;
            case "2":
                ivCold.setImageResource(R.drawable.cold0002);
                ivHot.setImageResource(R.drawable.hot0002);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 取消订阅获取工厂环境的请求
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
                Log.i(TAG, "onDestroy: 哈哈");
            }
        }
    }
}
