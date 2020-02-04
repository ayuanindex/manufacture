package com.lenovo.topic23;


import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiService {

    @POST("dataInterface/UserInPriceLog/getAll")
    Observable<UserInPriceLog> getUserInPriceLog();

    @POST("dataInterface/UserOutPriceLog/getAll")
    Observable<UserOutPriceLog> getUserOutPriceLog();
}
