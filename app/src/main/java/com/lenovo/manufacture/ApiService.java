package com.lenovo.manufacture;

import com.lenovo.manufacture.Bean.MaterialBean;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/Interface/index/getMaterial")
    Observable<MaterialBean> getMaterial();

}
