package com.lenovo.topic11;

import com.lenovo.topic11.bean.Material;
import com.lenovo.topic11.bean.ResultMessage_CreateProductionLine;
import com.lenovo.topic11.bean.ResultMessage_Store;
import com.lenovo.topic11.bean.SearchProductionLineBean;
import com.lenovo.topic11.bean.WorkInfoBean;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    /**
     * 获取原材料
     */
    @POST("Interface/index/getMaterial")
    Observable<Material> getMaterial();

    /**
     * 获取工厂信息
     *
     * @param id 工厂ID默认为1
     */
    @POST("dataInterface/UserWorkInfo/getInfo")
    @FormUrlEncoded
    Observable<WorkInfoBean> getWorkInfo(@Field("id") int id);

    /**
     * 获取指定位置的生产线
     *
     * @param position 位置
     */
    @POST("dataInterface/UserProductionLine/search")
    @FormUrlEncoded
    Observable<SearchProductionLineBean> getProductionLine(@Field("position") int position);

    /**
     * 创建生产线
     *
     * @param lineId 生产线类型
     * @param pos    生产线位置
     */
    @POST("Interface/index/createStudentLine")
    @FormUrlEncoded
    Observable<ResultMessage_CreateProductionLine> createProduction(@Field("lineId") int lineId, @Field("pos") int pos);

    @POST("Interface/index/addUserMaterialStore")
    @FormUrlEncoded
    Observable<ResultMessage_Store> addMaterialStore(@FieldMap Map<String, Object> hashMap);
}
