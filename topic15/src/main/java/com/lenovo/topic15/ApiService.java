package com.lenovo.topic15;

import com.lenovo.topic15.bean.AllPlStepBean;
import com.lenovo.topic15.bean.ProductionLineBean;
import com.lenovo.topic15.bean.ProductionLineResultMessage;
import com.lenovo.topic15.bean.ProductionLineStepBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic15
 * @ClassName: ApiService
 * @CreateDate: 2020/2/16 13:30
 */
public interface ApiService {
    /**
     * 获取指定位置的生产线
     *
     * @param position 生产线位置
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLineBean> getProductionLineIsPosition(@Field("position") int position);

    /**
     * 创建生产线
     *
     * @param lineId 生产线类型
     * @param pos    生产线位置
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ProductionLineResultMessage> createProductionLine(@Field("lineId1") int lineId, @Field("pos") int pos);

    /**
     * 获取所有生产环节信息
     */
    @POST("dataInterface/UserPlStepInfo/getAll")
    Observable<AllPlStepBean> getAllStepInfo();

    /**
     * 获取当前生产线的生产环节
     *
     * @param userProductionLineId 生产线ID
     */
    @POST("dataInterface/UserPlStep/search")
    @FormUrlEncoded
    Observable<ProductionLineStepBean> getProductionLineStep(@Field("userProductionLineId") int userProductionLineId);
}
