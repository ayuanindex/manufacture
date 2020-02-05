package com.lenovo.topic5;

import com.lenovo.topic5.bean.AllPerson;

import io.reactivex.Observable;
import retrofit2.http.POST;

/**
 * @ProjectName: manufacture
 * @Package: com.lenovo.topic5
 * @ClassName: ApiService
 * @CreateDate: 2020/1/18 17:28
 */
public interface ApiService {
    @POST("dataInterface/People/getAll")
    Observable<AllPerson> getAllPerson();
}
