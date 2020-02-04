package com.lenovo.topic2;

import com.lenovo.topic2.bean.FactoryEnvironment;
import com.lenovo.topic2.bean.UpdateAcOnOff;

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
     * @param acOnOff   空调状态{0表示关闭、1表示冷风、2表示热风}11
     */
    @POST("dataInterface/UserWorkEnvironmental/updateAcOnOff")
    @FormUrlEncoded
    Observable<UpdateAcOnOff> getFactoryEnvironment(@Field("id") int factoryId, @Field("acOnOff") String acOnOff);
}
