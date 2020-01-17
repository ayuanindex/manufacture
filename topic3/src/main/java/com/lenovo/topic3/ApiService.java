package com.lenovo.topic3;

import com.lenovo.topic3.bean.FactoryEnvironment;
import com.lenovo.topic3.bean.UpdatePower;

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

    /**
     * 修改工厂电力供应
     *
     * @param id    工厂ID
     * @param power 目标电力供应数值
     */
    @POST("dataInterface/UserWorkEnvironmental/updatePower")
    @FormUrlEncoded
    Observable<UpdatePower> setFactoryEnvironment(@Field("id") int id, @Field("power") int power);
}
