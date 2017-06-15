package fro.org.froproject.mvp.model;

import android.app.Application;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.SearchResultContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
public class SearchResultModel extends BaseModel implements SearchResultContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SearchResultModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
        super(repositoryManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<BaseJson<CourseResponseBean>> getCourseList(int page, String courseName, String type) {
        Map<String, Object> params = new HashMap<>();
        if (!TextUtils.isEmpty(type))
            params.put("type", type);
        params.put("courseName", courseName);
        params.put("page", page);
        params.put("size", Constants.PAGE_SIZE);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(params));
        Observable<BaseJson<CourseResponseBean>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getCourseList(body, MyApplication.getInstance().getToken());
        return response;
    }

    @Override
    public Observable<BaseJson<CourseResponseBean>> getClassCourseList(int page, String searchContetn, String classId) {
        Map<String, Object> params = new HashMap<>();
        params.put("classId", classId);
        params.put("courseName", searchContetn);
        params.put("page", page);
        params.put("size", Constants.PAGE_SIZE);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(params));
        Observable<BaseJson<CourseResponseBean>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getClassCourseList(body, MyApplication.getInstance().getToken());
        return response;
    }

}
