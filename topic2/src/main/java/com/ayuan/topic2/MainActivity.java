package com.ayuan.topic2;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayuan.topic2.bean.FactoryEnvironment;
import com.ayuan.topic2.bean.UpdateAcOnOff;
import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;

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

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

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
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateAcOnOff>() {
                    @Override
                    public void accept(UpdateAcOnOff updateAcOnOff) throws Exception {
                        Log.i(TAG, "哈哈：" + updateAcOnOff.getMessage());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "哈哈:出现错误" + throwable.getMessage());
                    }
                });
        disposables.add(subscribe);
    }

    /**
     * 获取当前工厂环境的网络请求方法
     * 5秒刷新一次
     */
    private void factoryEnvironment() {
        Disposable factory = Observable.interval(0, 5, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {

                    private Disposable subscribe;

                    @Override
                    public void accept(Long aLong) throws Exception {
                        // 把之前的取消订阅
                        if (subscribe != null && !subscribe.isDisposed()) {
                            subscribe.dispose();
                        }
                        subscribe = remote.getFactoryEnvironment(1)
                                .subscribeOn(Schedulers.io())
                                .map(new Function<FactoryEnvironment, List<FactoryEnvironment.DataBeanList>>() {
                                    @Override
                                    public List<FactoryEnvironment.DataBeanList> apply(FactoryEnvironment factoryEnvironment) throws Exception {
                                        return factoryEnvironment.getData();
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<List<FactoryEnvironment.DataBeanList>>() {
                                    @Override
                                    public void accept(List<FactoryEnvironment.DataBeanList> dataBeanLists) throws Exception {
                                        tvEnvironment.setText(dataBeanLists.get(0).getOutTemp());
                                        tvWorkshop.setText(dataBeanLists.get(0).getWorkshopTemp());
                                        acOnOff = dataBeanLists.get(0).getAcOnOff();
                                        replaceImage();
                                    }
                                }, new Consumer<Throwable>() {
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
        disposables.add(factory);
    }

    /**
     * 切换状态图片
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
