package com.lenovo.topic1;

import com.lenovo.topic1.bean.UpdateLightSwitch;

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
    @POST("dataInterface/UserWorkEnvironmental/updateLightSwitch")
    @FormUrlEncoded
    Observable<UpdateLightSwitch> getUpdateLightSwitch(@Field("id") int id, @Field("lightSwitch") int lightSwitch);
}
