package com.lenovo.topic05;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic05.bean.AllPerson;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic5
 * @ClassName: MainActivity
 * @CreateDate: 2020/1/18 16:57
 */
public class MainActivity extends BaseActivity {
    private ImageView iv_back;
    private CheckBox cb_class_sort;
    private CheckBox cb_money_sort;
    private ListView lv_person;
    private ApiService remote;
    private PersonAdapter personAdapter;
    private DecimalFormat decimalFormat;

    /**
     * 获取布局文件
     *
     * @return 返回一个布局文件的ID
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
        iv_back = findViewById(R.id.iv_back);
        lv_person = findViewById(R.id.lv_person);
        cb_class_sort = findViewById(R.id.cb_class_sort);
        cb_money_sort = findViewById(R.id.cb_money_sort);
    }

    /**
     * 初始化控件的监听
     */
    @Override
    protected void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.closeActivity();
            }
        });

        cb_class_sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sort(0, isChecked);
            }
        });

        cb_money_sort.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sort(1, isChecked);
            }
        });
    }

    /**
     * 初始化需要使用到的类
     */
    @Override
    protected void initData() {
        // 获取ApiService的实例
        remote = Network.remote(ApiService.class);
        // 数字格式化
        decimalFormat = new DecimalFormat("#,###");
        // 设置数据适配器
        personAdapter = new PersonAdapter(new ArrayList<>());
        lv_person.setAdapter(personAdapter);
        // 获取所有员工信息
        getAllPerson();
    }

    /**
     * 获取所有人员信息的网络请求
     */
    @SuppressLint("CheckResult")
    private void getAllPerson() {
        remote.getAllPerson()
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 数据转换，提取到需要到数据
                .map(new Function<AllPerson, List<AllPerson.PersonBean>>() {
                    @Override
                    public List<AllPerson.PersonBean> apply(AllPerson allPerson) throws Exception {
                        return allPerson.getData();
                    }
                })
                // 切换到主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                // 绑定声明周期
                .compose(this.bindToLifecycle())
                // 订阅网络请求状态
                .subscribe(new Consumer<List<AllPerson.PersonBean>>() {// 请求成功的订阅
                    @Override
                    public void accept(List<AllPerson.PersonBean> personBeans) throws Exception {
                        // 将需要展示的集合放进数据适配器中
                        personAdapter.setPersonBeans(personBeans);
                        // 刷新数据适配器以展示列表
                        personAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {// 请求失败
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "accept: 网络发生错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 排序方法（基于Collections类进行排序）
     *
     * @param select    排序类型
     * @param sortModel 升序或降序
     */
    private void sort(int select, boolean sortModel) {
        List<AllPerson.PersonBean> personBeans = personAdapter.getPersonBeans();
        // 对集合进行判空
        if (personBeans.size() <= 0) {
            Log.i(TAG, "sort: 当前暂无数据");
            return;
        }
        // 进行排序
        Collections.sort(personBeans, new Comparator<AllPerson.PersonBean>() {
            @Override
            public int compare(AllPerson.PersonBean o1, AllPerson.PersonBean o2) {
                // 选择类型进行排序
                switch (select) {
                    case 0:
                        // 对升序或者降序进行判断
                        if (sortModel) {
                            return o1.getStatus() - o2.getStatus();
                        } else {
                            return o2.getStatus() - o1.getStatus();
                        }
                    case 1:
                        if (sortModel) {
                            return o1.getGold() - o2.getGold();
                        } else {
                            return o2.getGold() - o1.getGold();
                        }
                }
                return 0;
            }
        });
        personAdapter.notifyDataSetChanged();
    }

    /**
     * 列表的数据适配器
     */
    class PersonAdapter extends BaseAdapter {
        private TextView tvName;
        private TextView tvClass;
        private TextView tvMoney;

        /**
         * 所有员工的数据集合
         */
        private List<AllPerson.PersonBean> personBeans;

        public PersonAdapter(List<AllPerson.PersonBean> personBeans) {
            this.personBeans = personBeans;
        }

        @Override
        public int getCount() {
            return personBeans.size();
        }

        @Override
        public AllPerson.PersonBean getItem(int position) {
            return personBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_person, null);
            } else {
                view = convertView;
            }
            // 初始化布局
            initView(view);
            // 将内容填充至控件
            tvName.setText(getItem(position).getPeopleName());
            tvMoney.setText(decimalFormat.format(getItem(position).getGold()) + "");
            switch (getItem(position).getStatus()) {
                case 0:
                    tvClass.setText("工程师");
                    break;
                case 1:
                    tvClass.setText("工人");
                    break;
                case 2:
                    tvClass.setText("技术人员");
                    break;
                case 3:
                    tvClass.setText("检测人员");
                    break;
            }
            return view;
        }

        private void initView(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvClass = (TextView) view.findViewById(R.id.tv_class);
            tvMoney = (TextView) view.findViewById(R.id.tv_money);
        }

        public List<AllPerson.PersonBean> getPersonBeans() {
            return personBeans;
        }

        public void setPersonBeans(List<AllPerson.PersonBean> personBeans) {
            this.personBeans = personBeans;
        }
    }
}
