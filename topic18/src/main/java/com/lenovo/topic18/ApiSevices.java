package com.lenovo.topic18;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiSevices {

    //查询全部维修车辆成品仓库
    @POST("dataInterface/UserRepairCarStore/getAll")
    Observable<UserRepairCarStoreGetAll> getAllUserRepairCarStore();

    //删除维修车辆成品仓库
    @POST("dataInterface/UserRepairCarStore/delete")
    @FormUrlEncoded
    Observable<DeleteUserRepairCarStore> deleteUserRepairCarStore(@Field("id") int id);

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

    //获取车辆信息
    @POST("dataInterface/CarInfo/getInfo")
    @FormUrlEncoded
    Observable<CarInfo> getCarInfo(@Field("id") int id);

}
