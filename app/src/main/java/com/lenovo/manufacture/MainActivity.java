package com.lenovo.manufacture;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.manufacture.Bean.MaterialBean;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    @Override
    protected void initEvent() {

    }


    @Override
    protected void initData() {
        ApiService apiService = Network.remote(ApiService.class);
        Disposable disposable = apiService.getMaterial()
                .subscribeOn(Schedulers.io())//耗时线程
                //map操作符，对象转换
                .map(new Function<MaterialBean, List<MaterialBean.DataBean>>() {
                    @Override
                    public List<MaterialBean.DataBean> apply(MaterialBean materialBean) throws Exception {
                        return materialBean.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//切换到主线程
                .subscribe(new Consumer<List<MaterialBean.DataBean>>() {
                    @Override
                    public void accept(List<MaterialBean.DataBean> dataBeans) throws Exception {
                        Log.e(TAG, "------------------------" + dataBeans.size());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        //取消订阅
        //disposable.dispose();
    }

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    private TextView mTv;

    @Override
    protected void initView() {

        mTv = (TextView) findViewById(R.id.tv);


    }
}
