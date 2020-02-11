package com.lenovo.topic07;

import com.lenovo.topic07.bean.AllPersonBean;
import com.lenovo.topic07.bean.AllProductioinLine;
import com.lenovo.topic07.bean.ResultMessage;

import java.util.Map;

import io.reactivex.Observable;
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
     * 获取全部人员
     */
    @POST("dataInterface/People/getAll")
    Observable<AllPersonBean> getAllPerson();

    /**
     * 创建学生员工
     *
     * @param hashMap 请求参数
     */
    @POST("dataInterface/UserPeople/create")
    @FormUrlEncoded
    Observable<ResultMessage> createStudentPerson(@FieldMap Map<String, Object> hashMap);

    /**
     * 获取所有生产线
     */
    @POST("dataInterface/UserProductionLine/getAll")
    Observable<AllProductioinLine> getAllProductionLine();
}
