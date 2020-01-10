package com.lenovo.basic.base.act;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lenovo.basic.base.frag.BaseFragment;

import java.util.List;

/**
 * 所有Activity的基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * Log标记
     */
    public final String TAG = this.getClass().getSimpleName();

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeSetContentView();
        setContentView(getLayoutIdRes());
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 在setContentView之前，设置窗口等
     */
    protected void initBeforeSetContentView() {
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();


    /**
     * 获取布局资源文件id
     *
     * @return 布局资源文件id
     */
    protected abstract @LayoutRes
    int getLayoutIdRes();


    /**
     * 初始化布局部分
     */
    protected abstract void initView();


    /**
     * 跳转Activity,并finish当前Activity
     *
     * @param activity 当前Activity
     * @param cls      目标Activity
     * @param b        是否关闭当前页面，true则关闭当前页面
     */
    public void startActivity(Activity activity, Class<? extends Activity> cls, boolean b) {
        Intent intent = new Intent(activity, cls);
        startActivity(intent);
        if (b) {
            finish();
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    public void startActivity(Intent intent, boolean b) {
        startActivity(intent);
        if (b) {
            finish();
        }
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 关闭当前页面
     *
     * @param activity 被关闭的页面
     */
    public void closeActivity(Activity activity) {
        activity.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    /**
     * 关闭当前页面
     */
    public void closeActivity() {
        this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    /**
     * 返回按键的处理
     * 1.监听输入法的状态,如果按下返回时,输入法显示则隐藏
     * 2.监听Fragment是否拦截了返回按钮,如果拦截了,则交给Fragment处理
     * 3.执行正常的"返回"逻辑
     */
    @Override
    public void onBackPressed() {
        // 判断Fragment是否拦截了返回键,得到当前Activity下的所有Fragment
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            for (Fragment fragment : fragments) {
                // 判断是否为我们能够处理的Fragment类型
                if (fragment instanceof BaseFragment && fragment.isVisible()) {
                    // 判断是否拦截了返回按钮
                    if (((BaseFragment) fragment).onBackPressed()) {
                        // 如果有直接Return
                        return;
                    }
                }
            }
        }
        //执行正常的逻辑
        closeActivity();
    }

}
