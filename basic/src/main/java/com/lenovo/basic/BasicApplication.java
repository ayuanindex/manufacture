package com.lenovo.basic;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;

public class BasicApplication extends Application {

    private static Gson mGson;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mGson = new Gson();
        mContext = this.getApplicationContext();
    }

    public static Gson getmGson() {
        return mGson;
    }


    public static Context getmContext() {
        return mContext;
    }
}
