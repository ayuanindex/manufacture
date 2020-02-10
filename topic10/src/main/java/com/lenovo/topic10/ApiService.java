package com.lenovo.topic10;

import com.lenovo.topic10.bean.MaterialBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiService {
    /**
     * 获取原材料
     */
    @POST("Interface/index/getMaterial")
    Observable<MaterialBean> getMaterial();
}
