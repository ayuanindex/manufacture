package com.lenovo.topic09;

import com.lenovo.topic09.bean.AllPeopleBean;
import com.lenovo.topic09.bean.ProductionLineBean;
import com.lenovo.topic09.bean.ProductionResultMessage;
import com.lenovo.topic09.bean.UserPeopleBean;
import com.lenovo.topic09.bean.UserWorkEnvironmentalBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic09
 * @ClassName: ApiService
 * @CreateDate: 2020/2/12 16:44
 */
public interface ApiService {
    /**
     * 获取指定位置的生产线
     *
     * @param position 位置
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<ProductionLineBean> getProductionIsPosition(@Field("position") int position);

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
     * 查询当前生产线的学生员工呢
     *
     * @param userProductionLineId 生产线ID
     */
    @POST("dataInterface/UserPeople/search")
    @FormUrlEncoded
    Observable<UserPeopleBean> getUserPeople(@Field("userProductionLineId") int userProductionLineId);

    /**
     * 获取所有人员信息
     */
    @POST("dataInterface/People/getAll")
    Observable<AllPeopleBean> getAllPeople();

    /**
     * 获取当前工厂环境信息
     *
     * @param id 工厂ID
     */
    @POST("dataInterface/UserWorkEnvironmental/getInfo")
    @FormUrlEncoded
    Observable<UserWorkEnvironmentalBean> getUserWorkEnvironmental(@Field("id") int id);
}
