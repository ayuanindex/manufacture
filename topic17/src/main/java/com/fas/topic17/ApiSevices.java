package com.fas.topic17;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiSevices {

    //查询一条学生工厂信息数据
    @POST("dataInterface/UserWorkInfo/getInfo")
    @FormUrlEncoded
    Observable<UserWorkInfo> getInfoUserWorkInfo(@Field("id") int id);

    //修改学生工厂信息金币
    @POST("dataInterface/UserWorkInfo/updatePrice")
    @FormUrlEncoded
    Observable<UserWorkInfoUpdatePrice> updatePriceUserWorkInfo(@Field("id") int id, @Field("price") int price);

    //查询一条车辆数据
    @POST("dataInterface/Car/getInfo")
    Observable<CarType> getAllCar();


    //查询全部正常车辆成品仓库
    @POST("dataInterface/UserNormalCarStore/getAll")
    Observable<GetUserNormalCarStore> getAllUserNormalCarStore();

    //删除正常车辆成品仓库
    @POST("dataInterface/UserNormalCarStore/delete")
    @FormUrlEncoded
    Observable<DeleteUserNormalCarStore> deleteUserNormalCarStore(@Field("id") int id);
}
