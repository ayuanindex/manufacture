package com.lenovo.topic11;

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
     * 获取资源文件ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化布局中的控件
     */
    @Override
    protected void initView() {

    }

    /**
     * 初始化控件的监听
     */
    @Override
    protected void initEvent() {

    }

    /**
     * 初始化大致逻辑
     */
    @Override
    protected void initData() {

    }
}
