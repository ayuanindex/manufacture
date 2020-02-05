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
 * @CreateDate: 2020/1/19 10:38
 */
public interface ApiService {
    /**
     * 获取所有已经创建的生产线
     */
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<AllProductionLineBean> getAllProductionLine();

    /**
     * 在指定位置创建生产线你
     *
     * @param lineId   生产线ID
     * @param position 生产线的目标位置
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ResultMessageBean> getResult(@Field("lineId") int lineId, @Field("pos") int position);
}
