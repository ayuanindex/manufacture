package com.lenovo.topic03;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic03.bean.FactoryEnvironment;
import com.lenovo.topic03.bean.UpdatePower;
import com.lenovo.topic03.customerSeekBar.RangeSeekBar;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    /**
     * seekBar当前值
     */
    private int currentProgress;
    private ImageView iv_back;
    private TextView tv_electricity;
    private RangeSeekBar rsb_ruler;
    private TextView tv_power_consumption;
    private SeekBar sb_changeValue;
    private ApiService remote;
    private ArrayList<Disposable> disposables;

    /**
     * 获取布局文件
     *
     * @return 返回布局文件的ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件
     */
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_electricity = (TextView) findViewById(R.id.tv_electricity);
        rsb_ruler = (RangeSeekBar) findViewById(R.id.rsb_ruler);
        tv_power_consumption = (TextView) findViewById(R.id.tv_power_consumption);
        sb_changeValue = (SeekBar) findViewById(R.id.sb_changeValue);
    }

    /**
     * 初始化监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭当前页面
                MainActivity.this.closeActivity();
            }
        });

        sb_changeValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * seekBar的值改变时执行
             *
             * @param progress 当前值
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MainActivity.this.currentProgress = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            /**
             * 当停止改变时执行
             */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                setFactoryPowerConsumption();
            }
        });
    }

    /**
     * 声明实例化需要使用的工具类
     */
    @Override
    protected void initData() {
        // 实例话ApiService接口
        remote = Network.remote(ApiService.class);
        // 创建一个订阅集合
        disposables = new ArrayList<>();
        // 获取当前工厂耗电情况呢
        getFactoryPowerConsumption();
    }

    /**
     * 修改电力供应
     */
    private void setFactoryPowerConsumption() {
        Disposable subscribe = remote.setFactoryEnvironment(1, currentProgress)
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                .compose(this.bindToLifecycle())
                // 切换到主线程对整理好的数据进行展示
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅网络请求的状体
                .subscribe(new Consumer<UpdatePower>() {// 请求成功
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(UpdatePower updatePower) throws Exception {
                        // 请求成功时将数据同步到控件上
                        // 修改界面中相关控件的值
                        tv_electricity.setText(updatePower.getData().getPower() + "kw/h");
                        tv_power_consumption.setText(updatePower.getData().getPower() + "kw/h");
                        rsb_ruler.setCurrentProgress(Integer.parseInt(updatePower.getData().getPower()));
                    }
                }, new Consumer<Throwable>() {// 请求失败
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "哈哈：" + throwable.getMessage());
                    }
                });
        // 将订阅天较大订阅集合中，待到界面销毁时随界面一起销毁
        disposables.add(subscribe);
    }

    /**
     * 获取工厂用电量的网络请请求方法
     */
    private void getFactoryPowerConsumption() {
        // 定时获取工厂用电量
        // @field1：第一次执行的延时；@field2：循环周期的间隔时间；@field3：时间的计时单位
        Disposable subscribe = Observable.interval(0, 5, TimeUnit.SECONDS)
                // 周期循环时所执行的方法
                .doOnNext(new Consumer<Long>() {

                    /**
                     * 获取工厂用电量的订阅
                     */
                    private Disposable subscribe;

                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 判断订阅是否存在以及是否已经处于订阅状态，如果是的话则将订阅取消
                        if (subscribe != null && !subscribe.isDisposed()) {
                            Log.i(TAG, "哈哈：" + aLong);
                            subscribe.dispose();
                        }
                        subscribe = remote.getFactoryEnvironment(1)
                                // 切换到子线程有运行
                                .compose(MainActivity.this.bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                // 对获取到的数据进行变换，拿到自己想要的数据在接下来的方法中呢继续使用
                                .map(new Function<FactoryEnvironment, FactoryEnvironment.DataBeanList>() {
                                    @Override
                                    public FactoryEnvironment.DataBeanList apply(FactoryEnvironment factoryEnvironment) throws Exception {
                                        return factoryEnvironment.getData().get(0);
                                    }
                                })
                                // 切换到主线程运行
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<FactoryEnvironment.DataBeanList>() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void accept(FactoryEnvironment.DataBeanList dataBeanList) throws Exception {
                                        // 修改界面中相关控件的值
                                        tv_electricity.setText(dataBeanList.getPower() + "kw/h");
                                        tv_power_consumption.setText(dataBeanList.getPower() + "kw/h");
                                        rsb_ruler.setCurrentProgress(Integer.parseInt(dataBeanList.getPower()));
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        Log.i(TAG, "哈哈：" + throwable.getMessage());
                                    }
                                });
                    }
                })
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i(TAG, "哈哈：" + aLong);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "哈哈：" + throwable.getMessage());
                    }
                });
        disposables.add(subscribe);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}