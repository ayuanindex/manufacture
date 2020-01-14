package com.ayuan.topic2;

import com.ayuan.topic2.bean.FactoryEnvironment;
import com.ayuan.topic2.bean.UpdateAcOnOff;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * @param factoryId 工厂ID
     */
    @POST("dataInterface/UserWorkEnvironmental/getInfo")
    @FormUrlEncoded
    Observable<FactoryEnvironment> getFactoryEnvironment(@Field("id") int factoryId);

    /**
     * @param factoryId 工厂ID
     * @param acOnOff   空调状态{0表示关闭、1表示冷风、2表示热风}
     */
    @POST("dataInterface/UserWorkEnvironmental/updateAcOnOff")
    @FormUrlEncoded
    Observable<UpdateAcOnOff> getFactoryEnvironment(@Field("id") int factoryId, @Field("acOnOff") String acOnOff);
}
