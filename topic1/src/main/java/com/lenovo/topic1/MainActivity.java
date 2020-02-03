package com.lenovo.topic1;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic1.bean.UpdateLightSwitch;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private Button btn_open;
    private Button btn_close;
    private Disposable subscribe;
    private ApiService remote;

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_close = (Button) findViewById(R.id.btn_close);
    }

    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 关闭当前Activity
                MainActivity.this.closeActivity();
            }
        });

        // 开灯的按钮
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightControl(1);
            }
        });

        // 关灯的按钮
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightControl(0);
            }
        });
    }

    @Override
    protected void initData() {
        // 实例化ApiService接口
        remote = Network.remote(ApiService.class);
    }

    /**
     * 开关工厂灯的网络请求方法
     *
     * @param lightSwitch 0表示关闭,1表示开启
     */
    private void lightControl(int lightSwitch) {
        // 判断订阅是否为空，是否已经订阅
        if (subscribe != null && subscribe.isDisposed()) {
            // 将之前已经存在的订阅销毁
            subscribe.dispose();
        }
        // 调用指定的接口（接口已经包含了api地址以及所需要的参数类型
        subscribe = remote.getUpdateLightSwitch(1, lightSwitch)
                // 切换到子线程
                .subscribeOn(Schedulers.io())
                // 切换到主线程
                .observeOn(AndroidSchedulers.mainThread())
                // 订阅状态
                .subscribe(new Consumer<UpdateLightSwitch>() {// 订阅成功的状态
                    /**
                     * @param updateLightSwitch JavaBean的实例
                     * @throws Exception 抛出的异常
                     */
                    @Override
                    public void accept(UpdateLightSwitch updateLightSwitch) throws Exception {
                        // 判断数据石佛修改成功，如果所要修改的数据和当亲数据真实数一致的话，则返回的是"本次修改没有数据变化"
                        if (updateLightSwitch.getMessage().equals("本次修改没有数据变化")) {
                            Log.i(TAG, "本次修改没有数据变化");
                            return;
                        }
                        // 修改成会返回SUCCESS的字符
                        Log.i(TAG, "哈哈：" + (updateLightSwitch.getMessage().equals("SUCCESS") ? (lightSwitch == 1 ? "开启" : "关闭") + "成功" : (lightSwitch == 1 ? "开启" : "关闭") + "修改失败"));
                    }
                }, new Consumer<Throwable>() {// 订阅失败的状态
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 提示错误信息，方便找出问题所在
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "哈哈：" + throwable.getMessage());
                    }
                });
    }
}
