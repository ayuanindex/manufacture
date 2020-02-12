package com.lenovo.topic14;

import android.widget.ImageView;
import android.widget.ListView;

import com.lenovo.basic.base.act.BaseActivity;

public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private ListView lv_left;
    private ListView lv_right;

    /**
     * @return 获取资源文件ID
     */
    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    /**
     * 初始化控件
     */
    @Override
    protected void initView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv_left = (ListView) findViewById(R.id.lv_left);
        lv_right = (ListView) findViewById(R.id.lv_right);
    }

    /**
     * 初始化控件的监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(v -> MainActivity.this.closeActivity());
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {

    }
}
