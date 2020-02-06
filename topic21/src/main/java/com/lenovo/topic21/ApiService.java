package com.lenovo.topic21;


import io.reactivex.Observable;
import retrofit2.http.POST;


public interface ApiService {
    //查询全部收入日志
    @POST("dataInterface/UserInPriceLog/getAll")
    Observable<UserPriceLog> getUserInPriceLog();

    //查询全部支出日志
    @POST("dataInterface/UserOutPriceLog/getAll")
    Observable<UserPriceLog> getUserOutPriceLog();
}
