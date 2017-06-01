package fro.org.froproject.mvp.model.api.service;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * 存放通用的api
 * Created by Lgm on 2017/5/31 0031.
 */

public interface CommonService {
    String HEADER_API_VERSION = "Accept: application/json";
    String HEADER_API_VERSION1 = "Content-Type: application/json";
    @Headers({HEADER_API_VERSION,HEADER_API_VERSION1})
    @POST("user/login")
    Observable<BaseJson<UserInfoBean>> login(@Body RequestBody route);

}
