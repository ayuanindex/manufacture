package com.lenovo.topic08;

import com.lenovo.topic08.bean.AllPeople;
import com.lenovo.topic08.bean.LineToPeople;
import com.lenovo.topic08.bean.ProductionLine;
import com.lenovo.topic08.bean.ResultMessage;
import com.lenovo.topic08.bean.ResultMessageBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
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

    /**
     * 获取所有人员信息
     */
    @POST("dataInterface/People/getAll")
    Observable<AllPeople> getAllPeople();

    /**
     * 床架学生员工
     *
     * @param map 学生员工信息键值对
     */
    @POST("dataInterface/UserPeople/create")
    @FormUrlEncoded
    Observable<ResultMessage> createStudent(@FieldMap Map<String, Object> map);
}
