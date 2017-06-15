package fro.org.froproject.mvp.model.api.service;

import java.util.List;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.ClassInfoBean;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import fro.org.froproject.mvp.model.entity.CommonBean;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import fro.org.froproject.mvp.model.entity.ExerciseBean;
import fro.org.froproject.mvp.model.entity.ExerciseResponseBean;
import fro.org.froproject.mvp.model.entity.HistoryClassBean;
import fro.org.froproject.mvp.model.entity.HistoryClassListBean;
import fro.org.froproject.mvp.model.entity.OrgBean;
import fro.org.froproject.mvp.model.entity.ScoreBean;
import fro.org.froproject.mvp.model.entity.ScoreClassBean;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 存放通用的api
 * Created by Lgm on 2017/5/31 0031.
 */

public interface CommonService {
    String HEADER_API_VERSION = "Accept: application/json";
    String HEADER_API_VERSION1 = "Content-Type: application/json";
    String HEADER_API_TOKEN = "Session-Token";

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
    Observable<BaseJson> submit(@Body RequestBody body);

    /**
     * 注册
     *
     * @param body
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @POST("user/register")
    Observable<BaseJson<CommonBean>> register(@Body RequestBody body);

    /**
     * 获取组织机构性质
     */

    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("traininggorganization/nature/list")
    Observable<BaseJson<List<OrgBean>>> getNatureList();

    /**
     * 获取组织机构类别
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("traininggorganization/category/list/{id}")
    Observable<BaseJson<List<OrgBean>>> getOrgTypeList(@Path("id") int id);

    /**
     * 获取具体机构
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("traininggorganization/list/{id}")
    Observable<BaseJson<List<OrgBean>>> getOrgDetailList(@Path("id") int orgTypeId);

    /**
     * 获取工作年限
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("workYear/list")
    Observable<BaseJson<List<OrgBean>>> getWorkYearList();

    /**
     * 获取证件
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("idtype/list")
    Observable<BaseJson<List<OrgBean>>> getCredentials(@Header(HEADER_API_TOKEN) String token);

    /**
     * 提交个人信息
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @POST("user/updatedetail")
    Observable<BaseJson> commit(@Body RequestBody body, @Header(HEADER_API_TOKEN) String token);

    /**
     * 上传头像
     *
     * @param body
     * @param token
     * @return
     */
    @Multipart
    @POST("pic/imageup")
    Observable<BaseJson<CommonBean>> uploadImg(@Part("upfile\"; filename=\"test.jpg\"") RequestBody body, @Header(HEADER_API_TOKEN) String token);

    /**
     * 获取我的班级
     *
     * @param token
     * @param page
     * @param pageSize
     * @return
     */

    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("class/get/current")
    Observable<BaseJson<ClassListBean<ClassBean>>> getMyClassList(@Header(HEADER_API_TOKEN) String token, @Query("page") int page, @Query("size") int pageSize);

    /**
     * 获取历史班级列表
     *
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("class/get/history")
    Observable<BaseJson<HistoryClassListBean<HistoryClassBean>>> getHistoryClassList(@Header(HEADER_API_TOKEN) String token, @Query("page") int page, @Query("size") int pageSize);

//    /**
//     * 获取课程列表
//     *
//     * @param body
//     * @param token
//     * @return
//     */
//    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
//    @POST("course/process/list")
//    Observable<BaseJson<CourseResponseBean>> getNotPassCourseList(@Body RequestBody body, @Header(HEADER_API_TOKEN) String token);

    /**
     * 获取进度与成绩列表
     *
     * @param page
     * @param pageSize
     * @param token
     * @return
     */

    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("class/get/process")
    Observable<BaseJson<ScoreBean<ScoreClassBean>>> getScoreClassList(@Query("page") int page, @Query("size") int pageSize, @Header(HEADER_API_TOKEN) String token);

    /**
     * 获取课程详情
     *
     * @param classId
     * @param courseId
     * @param token
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("course/get/{classId}/{courseId}")
    Observable<BaseJson<CourseBean>> getCourseContent(@Path("classId") int classId, @Path("courseId") int courseId, @Header(HEADER_API_TOKEN) String token);

    /**
     * 获取问题列表
     *
     * @param classId
     * @param courseId
     * @param token
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("courseexercise/courseexercise/{classId}/{courseId}/list")
    Observable<BaseJson<List<ExerciseBean>>> getQuestionList(@Path("classId") int classId, @Path("courseId") int courseId, @Header(HEADER_API_TOKEN) String token);

    /**
     * 提交答题结果
     *
     * @param body
     * @param token
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @POST("courseexercise/commit")
    Observable<BaseJson<ExerciseResponseBean>> commitExercise(@Body RequestBody body, @Header(HEADER_API_TOKEN) String token);

    /**
     * 获取班级信息
     *
     * @param page
     * @param classId
     * @param pageSize
     * @return
     */
    @Headers({HEADER_API_VERSION, HEADER_API_VERSION1})
    @GET("class/get/current/{classId}")
    Observable<BaseJson<ClassInfoBean<CourseBean>>> getClassInfo(@Path("classId") int classId, @Query("page") int page, @Query("size") int pageSize, @Header(HEADER_API_TOKEN) String token);

    /**
     * 查询课程
     *
     * @param body
     * @param token
     * @return
     */
    @POST("course/process/list")
    Observable<BaseJson<CourseResponseBean>> getCourseList(@Body RequestBody body, @Header(HEADER_API_TOKEN) String token);



    /**
     * 查询班级课程
     * @param body
     * @param token
     * @return
     */
    @POST("course/course/list")
    Observable<BaseJson<CourseResponseBean>> getClassCourseList(@Body RequestBody body,@Header(HEADER_API_TOKEN)  String token);

    /**
     * 修改密码
     * @param body
     * @return
     */
    @POST("user/chagePassword")
    Observable<BaseJson> modifyPassword(@Body RequestBody body,@Header(HEADER_API_TOKEN)  String token);
}
