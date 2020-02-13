package com.lenovo.basic.utils;

import com.lenovo.basic.BasicApplication;

import java.util.concurrent.TimeUnit;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 封装了Retrofit的网络请求工具类
 */
public class Network {
    private static Retrofit retrofit;
    //网络请求读写时长
    private static final int REQUEST_TIME = 30;
    //智能制造URL地址101.201.112.95,192.168.1.104
    public final static String BASE_URL = "http://192.168.1.104:8085/";
    public static String ip = "http://192.168.1.104";

    // 构建一个Retrofit
    private static Retrofit getRetrofit() {
        if (retrofit != null)
            return retrofit;
        OkHttpClient client = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                .readTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIME, TimeUnit.SECONDS)
                .build();

//        OkHttpClient client = new OkHttpClient.Builder()
//                .readTimeout(REQUEST_TIME, TimeUnit.SECONDS)
//                .connectTimeout(REQUEST_TIME, TimeUnit.SECONDS)
//                .build();

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(BasicApplication.getmGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;

    }

    /**
     * 返回一个请求代理
     *
     * @return
     */
    public static <T> T remote(Class<T> clazz) {
        return Network.getRetrofit().create(clazz);
    }
}
