package com.lenovo.topic4;

import com.lenovo.topic4.bean.FactoryEnvironment;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * 查看工厂状态
     *
     * @param factoryId 工厂ID
     */
    @POST("dataInterface/UserWorkEnvironmental/getInfo")
    @FormUrlEncoded
    Observable<FactoryEnvironment> getFactoryEnvironment(@Field("id") int factoryId);
}
