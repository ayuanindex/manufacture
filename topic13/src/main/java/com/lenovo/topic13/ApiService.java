package com.lenovo.topic13;

import com.lenovo.topic13.bean.PartBean;
import com.lenovo.topic13.bean.ProductionLineBean;
import com.lenovo.topic13.bean.ResultMessageIsCreateProduction;
import com.lenovo.topic13.bean.UserPart;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * 获取指定位置的生产线
     *
     * @param position 位置
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLineBean> getProductionLineIsPosition(@Field("position") int position);

    /**
     * 在指定位置创建生产线
     *
     * @param lineId 生产线类型
     * @param pos    生产线位置
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ResultMessageIsCreateProduction> createProductionLine(@Field("lineId") int lineId, @Field("pos") int pos);

    /**
     * 查询全部原料的资料
     */
    @POST("dataInterface/Part/getAll")
    Observable<PartBean> getAllPart();

    /**
     * 获取指定你生产线的备料
     *
     * @param userProductionLineId 生产线的ID
     */
    @POST("dataInterface/UserParts/search")
    @FormUrlEncoded
    Observable<UserPart> getUserPart(@Field("userProductionLineId") int userProductionLineId);
}