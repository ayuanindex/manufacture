package com.lenovo.topic7;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lenovo.basic.base.act.BaseActivity;
import com.lenovo.basic.utils.Network;
import com.lenovo.topic7.bean.AllPersonBean;
import com.lenovo.topic7.bean.AllProductioinLine;
import com.lenovo.topic7.bean.ResultMessage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private ImageView iv_back;
    private RadioButton rbtn_one;
    private RadioButton rbtn_two;
    private RadioButton rbtn_three;
    private RadioButton rbtn_four;
    private CheckBox cb_class_sort;
    private CheckBox cb_money_sort;
    private ListView lv_person;
    private ApiService remote;
    private CustomerAdapter customerAdapter;
    private int currentLineId;
    /**
     * 存放订阅的集合
     */
    private ArrayList<Disposable> disposables;
    private List<AllPersonBean.DataBean> dataBeanList;
    private DecimalFormat decimalFormat;
    private RadioGroup radioGroup;
    private HashMap<Integer, Integer> hashMap;

    /**
     * @return 返回布局的ID
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
        rbtn_one = (RadioButton) findViewById(R.id.rbtn_one);
        rbtn_two = (RadioButton) findViewById(R.id.rbtn_two);
        rbtn_three = (RadioButton) findViewById(R.id.rbtn_three);
        rbtn_four = (RadioButton) findViewById(R.id.rbtn_four);
        cb_class_sort = (CheckBox) findViewById(R.id.cb_class_sort);
        cb_money_sort = (CheckBox) findViewById(R.id.cb_money_sort);
        lv_person = (ListView) findViewById(R.id.lv_person);
        radioGroup = (RadioGroup) findViewById(R.id.rbtngroup);
    }

    /**
     * 初始化控件你监听
     */
    @Override
    protected void initEvent() {
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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // 设置当前的生产线ID
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.rbtn_one:
                        currentLineId = hashMap.get(0);
                        break;
                    case R.id.rbtn_two:
                        currentLineId = hashMap.get(1);
                        break;
                    case R.id.rbtn_three:
                        currentLineId = hashMap.get(2);
                        break;
                    case R.id.rbtn_four:
                        currentLineId = hashMap.get(3);
                        break;
                }
                Log.i(TAG, "onCheckedChanged: " + currentLineId);
            }
        });
    }

    /**
     * 初始化需要用到的类
     */
    @SuppressLint("UseSparseArrays")
    @Override
    protected void initData() {
        // 实例化ApiService类
        remote = Network.remote(ApiService.class);

        // 创建一个集合用于填充数据适配器
        dataBeanList = new ArrayList<>();

        // 数字格式化
        decimalFormat = new DecimalFormat("#,###");

        hashMap = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            hashMap.put(i, 0);
        }

        // 设置数据适配器
        customerAdapter = new CustomerAdapter();
        lv_person.setAdapter(customerAdapter);

        // 获取所有人员
        getAllPerson();
        // 获取所有生产线
        getAllProductionLine();
    }

    /**
     * 请求全部人员的数据
     */
    @SuppressLint("CheckResult")
    private void getAllPerson() {
        remote.getAllPerson()
                // 切换到子线程进行网络请求
                .subscribeOn(Schedulers.io())
                // 对拿到的数据进行处理得到想要的数据用于展示
                .map(new Function<AllPersonBean, List<AllPersonBean.DataBean>>() {
                    @Override
                    public List<AllPersonBean.DataBean> apply(AllPersonBean allPersonBean) throws Exception {
                        dataBeanList.addAll(allPersonBean.getData());
                        return dataBeanList;
                    }
                })
                // 切换到主线程运行
                .observeOn(AndroidSchedulers.mainThread())
                // 绑定生命周期
                .compose(this.bindToLifecycle())
                // 订阅家网络请求的状态
                .subscribe(new Consumer<List<AllPersonBean.DataBean>>() {// 请求成功
                    @Override
                    public void accept(List<AllPersonBean.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "请求成功：" + dataBeans.toString());
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {// 请求失败
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络请求发生错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 获取所有生产线
     */
    @SuppressLint("CheckResult")
    private void getAllProductionLine() {
        remote.getAllProductionLine()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .map(new Function<AllProductioinLine, List<AllProductioinLine.DataBean>>() {
                    @Override
                    public List<AllProductioinLine.DataBean> apply(AllProductioinLine allProductioinLine) throws Exception {
                        return allProductioinLine.getData();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<AllProductioinLine.DataBean>>() {
                    @Override
                    public void accept(List<AllProductioinLine.DataBean> dataBeans) throws Exception {
                        Log.i(TAG, "请求成功：" + dataBeans.toString());
                        // 将对应位置的生产线简化成所需要的map集合
                        for (AllProductioinLine.DataBean dataBean : dataBeans) {
                            hashMap.put(dataBean.getPosition(), dataBean.getId());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络请求发生错误：" + throwable.getMessage());
                    }
                });
    }

    /**
     * 新增一个学生员工
     *
     * @param item 人员对象
     */
    @SuppressLint("CheckResult")
    private void createStudentPerson(AllPersonBean.DataBean item) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("userWorkId", 1);// 学生工厂
        map.put("power", item.getHp()); // 员工体力
        map.put("peopleId", item.getId());// 人员ID
        map.put("userProductionLineId", currentLineId);// 生产线ID
        map.put("workPostId", item.getStatus());// 岗位
        remote.createStudentPerson(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.bindToLifecycle())
                .subscribe(new Consumer<ResultMessage>() {
                    @Override
                    public void accept(ResultMessage resultMessage) throws Exception {
                        if (resultMessage.getMessage().equals("SUCCESS")) {
                            Toast.makeText(MainActivity.this, "招募成功", Toast.LENGTH_SHORT).show();
                        }
                        Log.i(TAG, "哈哈：" + resultMessage.getData().toString());
                        // 删除已经招聘的人员
                        dataBeanList.remove(item);
                        customerAdapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "网络请求出现问题" + throwable.getMessage());
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
        // 对集合进行判空
        if (dataBeanList.size() <= 0) {
            Log.i(TAG, "sort: 当前暂无数据");
            return;
        }
        // 进行排序
        Collections.sort(dataBeanList, new Comparator<AllPersonBean.DataBean>() {
            @Override
            public int compare(AllPersonBean.DataBean o1, AllPersonBean.DataBean o2) {
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
        customerAdapter.notifyDataSetChanged();
    }


    class CustomerAdapter extends BaseAdapter {
        private TextView tvName;
        private TextView tvClass;
        private TextView tvMoney;
        private Button btnRecruitment;

        @Override
        public int getCount() {
            return dataBeanList.size();
        }

        @Override
        public AllPersonBean.DataBean getItem(int position) {
            return dataBeanList.get(position);
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
            initView(view);
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
            btnRecruitment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createStudentPerson(getItem(position));
                }
            });
            return view;
        }

        private void initView(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvClass = (TextView) view.findViewById(R.id.tv_class);
            tvMoney = (TextView) view.findViewById(R.id.tv_money);
            btnRecruitment = (Button) view.findViewById(R.id.btn_recruitment);
        }
    }
}
