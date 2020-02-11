package com.lenovo.topic12;

import com.lenovo.topic12.bean.PartBean;
import com.lenovo.topic12.bean.PartStoreBean;
import com.lenovo.topic12.bean.ProductionLineBean;
import com.lenovo.topic12.bean.ResultMessage_Material;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {
    /**
     *
     */
    @POST("dataInterface/Part/getAll")
    Observable<PartBean> getAllPart();

    /**
     * 获取所有生产线
     */
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<ProductionLineBean> getAllProductionLine();

    /**
     * 补充材料
     *
     * @param userLineId 生产线ID
     */
    @POST("Interface/index/addUserMaterial")
    @FormUrlEncoded
    Observable<ResultMessage_Material> getResultMessage_Material(@Field("userLineId") int userLineId);

    /**
     * @return 查询学生原材料仓库
     */
    @POST("dataInterface/UserPartStore/getAll")
    Observable<PartStoreBean> getPartStore();
}
