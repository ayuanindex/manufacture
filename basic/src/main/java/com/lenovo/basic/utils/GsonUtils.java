package com.lenovo.basic.utils;

import androidx.annotation.Nullable;

import com.lenovo.basic.BasicApplication;


/**
 * Gson解析Json数据
 */
public class GsonUtils {
    /**
     * 解析Json数据
     *
     * @param json  需要解析的Json数据，string类型
     * @param clazz 解析类
     * @param <T>   对象
     * @return 解析后的对象
     */
    public static <T> T parseJson(@Nullable String json, Class<T> clazz) {
        return BasicApplication.getmGson().fromJson(json, clazz);
    }
}
