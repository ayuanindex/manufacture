package com.lenovo.topic19;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface APIService {

    //出库
    @POST("Interface/index/getUserMaterialPurchaseLog")
    Observable<Bean> getOut();

    //入库
    @POST("Interface/index/getUserMaterialLog")
    Observable<Bean> getIn();
}
