package com.lenovo.topic14;

import com.lenovo.topic14.bean.AllStepBean;
import com.lenovo.topic14.bean.ProductionLineBean;
import com.lenovo.topic14.bean.ProductionResultMessage;
import com.lenovo.topic14.bean.UserPlStepBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic14
 * @ClassName: ApiService
 * @Author: AYuan
 * @CreateDate: 2020/2/12 14:30
 */
public interface ApiService {
    /**
     * 查询指定位置的生产线你
     *
     * @param position 位置
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLineBean> getProductionLine(@Field("position") int position);

    /**
     * 创建生产线
     *
     * @param lineId 生产线类型
     * @param pos    生产线位置
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ProductionResultMessage> createProductionLine(@Field("lineId") int lineId, @Field("pos") int pos);

    /**
     * 查询全部生产环节
     */
    @POST("dataInterface/UserPlStepInfo/getAll")
    Observable<AllStepBean> getAllStep();

    /**
     * 按学生生产线查询生产环节
     *
     * @param userProductionLineId 生产线ID
     */
    @POST("dataInterface/UserPlStep/search")
    @FormUrlEncoded
    Observable<UserPlStepBean> getUserPlStep(@Field("userProductionLineId") int userProductionLineId);
}
