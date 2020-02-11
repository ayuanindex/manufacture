package com.lenovo.topic12;

import com.lenovo.topic12.bean.MaterialBean;
import com.lenovo.topic12.bean.ProductionLineBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * 获取所有原材料
     */
    @POST("Interface/index/getMaterial")
    Observable<MaterialBean> getMaterial();

    /**
     * 获取所有生产线
     */
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<ProductionLineBean> getAllProductionLine();
}
