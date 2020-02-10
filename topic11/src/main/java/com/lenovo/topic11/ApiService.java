package com.lenovo.topic11;

import com.lenovo.topic11.bean.Material;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ApiService {

    @POST("Interface/index/getMaterial")
    Observable<Material> getMaterial();
}
