package com.lenovo.topic6;

import com.lenovo.topic6.bean.AllProductionLineBean;
import com.lenovo.topic6.bean.ResultMessageBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic6
 * @ClassName: ApiService
 * @Author: AYuan
 * @CreateDate: 2020/1/19 10:38
 */
public interface ApiService {
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<AllProductionLineBean> getAllProductionLine();

    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ResultMessageBean> getResult(@Field("lineId") int lineId, @Field("pos") int position);
}
