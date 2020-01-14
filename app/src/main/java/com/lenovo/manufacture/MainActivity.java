package com.lenovo.manufacture;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.manufacture.Bean.MaterialBean;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 这是测试
 */
public class MainActivity extends BaseActivity {


    private GridView gv_menu;
    private GridViewAdater gridViewAdater;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected int getLayoutIdRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        gv_menu = (GridView) findViewById(R.id.gv_menu);
    }

    @Override
    protected void initEvent() {
        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    protected void initData() {
        // 给GridView设置数据适配器
        gridViewAdater = new GridViewAdater();
        gv_menu.setAdapter(gridViewAdater);

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

    class GridViewAdater extends BaseAdapter {
        private ImageView ivIcon;
        private TextView tvTitle;

        @Override
        public int getCount() {
            return 23;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_menu, null);
            } else {
                view = convertView;
            }
            initView(view);
            tvTitle.setText(position + 1 + "");
            return view;
        }

        private void initView(View view) {
            ivIcon = (ImageView) view.findViewById(R.id.iv_icon);
            tvTitle = (TextView) view.findViewById(R.id.tv_title);
        }
    }

}
