package com.lenovo.topic16;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiSevices {

    //查询全部学生问题车辆仓库
    @POST("dataInterface/UserQuestion/getAll")
    Observable<CarBean> getAllUserQuestion();

    //查询全部维修车辆成品仓库
    @POST("dataInterface/UserRepairCarStore/getAll")
    Observable<CarBean> getAllUserRepairCarStore();

    //查询全部正常车辆成品仓库
    @POST("dataInterface/UserNormalCarStore/getAll")
    Observable<CarBean> getAllUserNormalCarStore();

    //车辆维修
    @POST("Interface/index/repairCar")
    @FormUrlEncoded
    Observable<CarBean> repairCar(@Field("userQualityId") int id);

    //获取车辆信息
    @POST("dataInterface/CarInfo/getInfo")
    @FormUrlEncoded
    Observable<CarInfo> getCarInfo(@Field("id") int id);

}
