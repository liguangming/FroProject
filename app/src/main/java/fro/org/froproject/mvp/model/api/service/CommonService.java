package fro.org.froproject.mvp.model.api.service;

import java.util.IdentityHashMap;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 存放通用的api
 * Created by Lgm on 2017/5/31 0031.
 */

public interface CommonService {
    String HEADER_API_VERSION = "Accept: application/json";
    String HEADER_API_VERSION1 = "Content-Type: application/json";

    /**
     * 登录
     *
     * @param route
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @POST("user/login")
    Observable<BaseJson<UserInfoBean>> login(@Body RequestBody route);

    /**
     * 获取验证码
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @POST("verification")
    Observable<BaseJson> getAuthCode(@Body RequestBody route);

    /**
     * 忘记密码
     */

    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @POST("user/forgetpassword")
    Observable<BaseJson> submit(RequestBody body);

    /**
     * 注册
     *
     * @param body
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @POST("user/register")
    Observable<BaseJson> register(RequestBody body);

    /**
     * 获取组织机构性质
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("traininggorganization/nature/list")
    Observable<BaseJson> getNatureList();

}
