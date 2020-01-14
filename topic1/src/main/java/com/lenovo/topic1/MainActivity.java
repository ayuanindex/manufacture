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
                lightControl(1, 1);
            }
        });

        // 关灯的按钮
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lightControl(1, 0);
            }
        });
    }

    @Override
    protected void initData() {

    }

    /**
     * 开关工厂灯的网络请求方法
     *
     * @param id          灯的ID
     * @param lightSwitch 0表示关闭,1表示开启
     */
    private void lightControl(int id, int lightSwitch) {
        if (subscribe != null && subscribe.isDisposed()) {
            subscribe.dispose();
        }
        ApiService remote = Network.remote(ApiService.class);
        subscribe = remote.getUpdateLightSwitch(id, lightSwitch)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UpdateLightSwitch>() {
                    @Override
                    public void accept(UpdateLightSwitch updateLightSwitch) throws Exception {
                        if (updateLightSwitch.getMessage().equals("本次修改没有数据变化")) {
                            Log.i(TAG, "本次修改没有数据变化");
                            return;
                        }
                        Log.i(TAG, "哈哈：" + (updateLightSwitch.getMessage().equals("SUCCESS") ? (lightSwitch == 1 ? "开启" : "关闭") + "成功" : (lightSwitch == 1 ? "开启" : "关闭") + "修改失败"));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "哈哈：" + throwable.getMessage());
                    }
                });
    }
}
