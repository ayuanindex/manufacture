package com.lenovo.topic01;

import com.lenovo.topic01.bean.UpdateLightSwitch;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * @param id          工厂ID
     * @param lightSwitch 灯的状态
     * @return
     */
    @POST("dataInterface/UserWorkEnvironmental/updateLightSwitch")// 注解请求方式
    @FormUrlEncoded // 注解表示请求体是一个Form表单
    Observable<UpdateLightSwitch> getUpdateLightSwitch(@Field("id") int id, @Field("lightSwitch") int lightSwitch);
}
