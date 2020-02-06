package com.lenovo.topic20;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    //查询所有类型的接口
    @POST("dataInterface/Information/getAll")
    Observable<Information> getAllInformation();

    //按类型查询接口
    @POST("dataInterface/Information/search")
    @FormUrlEncoded
    Observable<Information> searchInformation(@Field("status") int status);

}
