package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.CourseInfoContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@ActivityScope
public class CourseInfoModel extends BaseModel implements CourseInfoContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public CourseInfoModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<CourseBean>> getCourseContent(int classId, int courseId) {
        Observable<BaseJson<CourseBean>> userInfo = mRepositoryManager.obtainRetrofitService(CommonService.class).getCourseContent(classId,courseId, MyApplication.getInstance().getToken());
        return userInfo;
    }
}
