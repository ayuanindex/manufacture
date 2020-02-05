package com.lenovo.topic22;


import io.reactivex.Observable;
import retrofit2.http.POST;


public interface ApiService {

    @POST("dataInterface/UserPriceLog/getAll")
    Observable<UserPriceLog> getUserInPriceLog();

    @POST("dataInterface/UserOutPriceLog/getAll")
    Observable<UserPriceLog> getUserOutPriceLog();
}
