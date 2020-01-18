package com.lenovo.topic3;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic3.bean.FactoryEnvironment;
import com.lenovo.topic3.bean.UpdatePower;
import com.lenovo.topic3.customerSeekBar.RangeSeekBar;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_electricity;
    private RangeSeekBar rsb_ruler;
    private TextView tv_power_consumption;
    private SeekBar sb_changeValue;
    /**
     * seekBar当前值
     */
    private int currentProgress;
    private ApiService remote;
    private ArrayList<Disposable> disposables;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_electricity = (TextView) findViewById(R.id.tv_electricity);
        rsb_ruler = (RangeSeekBar) findViewById(R.id.rsb_ruler);
        tv_power_consumption = (TextView) findViewById(R.id.tv_power_consumption);
        sb_changeValue = (SeekBar) findViewById(R.id.sb_changeValue);
    }

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdatePower>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void accept(UpdatePower updatePower) throws Exception {
                        // 请求成功时将数据同步到控件上
                        // 修改界面中相关控件的值
                        tv_electricity.setText(updatePower.getData().getPower() + "kw/h");
                        tv_power_consumption.setText(updatePower.getData().getPower() + "kw/h");
                        rsb_ruler.setCurrentProgress(Integer.parseInt(updatePower.getData().getPower()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "哈哈：" + throwable.getMessage());
                    }
                });
        disposables.add(subscribe);
    }

    /**
     * 获取工厂用电量的网络请请求方法
     */
    private void getFactoryPowerConsumption() {
        Disposable subscribe = Observable.interval(0, 5, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Disposable subscribe = remote.getFactoryEnvironment(1)
                                .subscribeOn(Schedulers.io())
                                .map(new Function<FactoryEnvironment, FactoryEnvironment.DataBeanList>() {
                                    @Override
                                    public FactoryEnvironment.DataBeanList apply(FactoryEnvironment factoryEnvironment) throws Exception {
                                        return factoryEnvironment.getData().get(0);
                                    }
                                })
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
                        // 将订阅添加到订阅集合
                        disposables.add(subscribe);
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
        for (Disposable disposable : disposables) {
            if (disposable != null && !disposable.isDisposed()) {
                disposable.dispose();
                Log.i(TAG, "哈哈");
            }
        }
    }
}