package com.lenovo.topic13;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;

public class MainActivity extends BaseActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * @return 资源文件ID
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

    }

    /**
     * 初始话控件的监听
     */
    @Override
    protected void initEvent() {

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
}
