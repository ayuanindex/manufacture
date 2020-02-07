package com.lenovo.topic8;

import com.lenovo.topic8.bean.AllPeople;
import com.lenovo.topic8.bean.LineToPeople;
import com.lenovo.topic8.bean.ProductionLine;
import com.lenovo.topic8.bean.ResultMessageBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic5
 * @ClassName: ApiService
 * @CreateDate: 2020/1/18 17:28
 */
public interface ApiService {
    /**
     * 查询指定位置的生产线
     *
     * @param position 位置
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLine> getProductionLine(@Field("position") int position);

    /**
     * 获取指定生产线的学生员工
     *
     * @param userProductionLineId 生产线ID
     */
    @POST("dataInterface/UserPeople/search")
    @FormUrlEncoded
    Observable<LineToPeople> getLineToPeople(@Field("userProductionLineId") int userProductionLineId);

    /**
     * 在指定位置创建生产线你
     *
     * @param lineId   生产线ID
     * @param position 生产线的目标位置
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ResultMessageBean> createProduction(@Field("lineId") int lineId, @Field("pos") int position);

    @POST("dataInterface/People/getAll")
    Observable<AllPeople> getAllPeople();
}
