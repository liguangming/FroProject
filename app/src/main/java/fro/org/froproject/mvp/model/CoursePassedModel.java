package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.CoursePassedContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@ActivityScope
public class CoursePassedModel extends BaseModel implements CoursePassedContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public CoursePassedModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<CourseResponseBean>> getCourseList(int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("type", "1");
        map.put("page", page);
        map.put("size", pageSize);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(map));
        Observable<BaseJson<CourseResponseBean>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getCourseList(body, MyApplication.getInstance().getToken());
        return response;
    }
}
