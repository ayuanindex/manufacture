package com.lenovo.topic8;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic8.bean.ProductionLine;
import com.lenovo.topic8.bean.ResultMessageBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ImageView iv_back;
    private TextView tv_gongcheng_name;
    private TextView tv_gongcheng_hp;
    private LinearLayout ll_gongcheng;
    private TextView tv_zhijian_name;
    private TextView tv_zhijian_hp;
    private LinearLayout ll_zhijian;
    private TextView tv_jishu_name;
    private TextView tv_jishu_hp;
    private LinearLayout ll_jishu;
    private TextView tv_caozuo_name;
    private TextView tv_caozuo_hp;
    private LinearLayout ll_caozuo;
    private ApiService remote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * @return 获取界面布局的资源ID
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
        tv_gongcheng_name = (TextView) findViewById(R.id.tv_gongcheng_name);
        tv_gongcheng_hp = (TextView) findViewById(R.id.tv_gongcheng_hp);
        ll_gongcheng = (LinearLayout) findViewById(R.id.ll_gongcheng);
        tv_zhijian_name = (TextView) findViewById(R.id.tv_zhijian_name);
        tv_zhijian_hp = (TextView) findViewById(R.id.tv_zhijian_hp);
        ll_zhijian = (LinearLayout) findViewById(R.id.ll_zhijian);
        tv_jishu_name = (TextView) findViewById(R.id.tv_jishu_name);
        tv_jishu_hp = (TextView) findViewById(R.id.tv_jishu_hp);
        ll_jishu = (LinearLayout) findViewById(R.id.ll_jishu);
        tv_caozuo_name = (TextView) findViewById(R.id.tv_caozuo_name);
        tv_caozuo_hp = (TextView) findViewById(R.id.tv_caozuo_hp);
        ll_caozuo = (LinearLayout) findViewById(R.id.ll_caozuo);
    }

    /**
     * 初始化控件监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });


    }

    /**
     * 初始化需要使用的工具类
     */
    @Override
    protected void initData() {
        // 实例化ApiService接口类
        remote = Network.remote(ApiService.class);
    }

    /**
     * 查询指定位置的生产线
     *
     * @param position 位置
     */
    @SuppressLint("CheckResult")
    private void getProductionLine(int position) {
        remote.getProductionLine(position)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ProductionLine>() {
                    @Override
                    public void accept(ProductionLine productionLine) throws Exception {
                        Log.i(TAG, "请求成功" + productionLine.toString());
                        // 如果第四条生产线不存在则自动创建，如果存在则查询在岗员工信息
                        if (productionLine.getData().size() == 0) {
                            // 床架第四条生产线
                            createProduction();
                        } else {

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络请求发生错误,生产线查询失败：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 创建生产线
     */
    @SuppressLint("CheckResult")
    private void createProduction() {
        remote.createProduction(1, 4)
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResultMessageBean>() {
                    @Override
                    public void accept(ResultMessageBean resultMessageBean) throws Exception {
                        if (!resultMessageBean.getMessage().equals("该位置已存在生产线")) {
                            Log.i(TAG, "生产线创建成功");
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网路请求出现问题，生产线创建失败：" + throwable.getMessage());
                    }
                });
    }
}
