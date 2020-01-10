package com.lenovo.basic.base.act;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;

import com.lenovo.basic.base.frag.BaseFragment;

import io.reactivex.annotations.NonNull;

/**
 * 所有Fragment依附的Activity的基类
 */
public abstract class BaseFragmentActivity extends BaseActivity {
    private FragmentManager mFragmentManager;

    /**
     * 打开一个Fragment,并将其添加到回退栈
     *
     * @param resId    被替换的FragmentLayout布局
     * @param fragment
     */
    public void startFragmentAddToBackStack(@IdRes int resId, @NonNull BaseFragment fragment) {
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .add(resId, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
    }

    /**
     * 切换Fragment
     *
     * @param resId
     * @param fragment
     */
    public void startFragmentWithReplace(@IdRes int resId, @NonNull BaseFragment fragment) {
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(resId, fragment)
                .commit();
    }


    /**
     * Fragment回退
     */
    public void popBackStack() {
        if (mFragmentManager == null)
            mFragmentManager = getSupportFragmentManager();
        mFragmentManager.popBackStack();
    }
}
