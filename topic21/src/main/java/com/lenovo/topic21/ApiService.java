package com.lenovo.topic21;


import io.reactivex.Observable;
import retrofit2.http.POST;


public interface ApiService {

    @POST("dataInterface/UserInPriceLog/getAll")
    Observable<UserPriceLog> getUserInPriceLog();

    @POST("dataInterface/UserOutPriceLog/getAll")
    Observable<UserPriceLog> getUserOutPriceLog();
}
