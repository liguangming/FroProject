package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.ClassInfoContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.ClassInfoBean;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import fro.org.froproject.mvp.model.entity.CourseBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
public class ClassInfoModel extends BaseModel implements ClassInfoContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ClassInfoModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<ClassInfoBean<CourseBean>>> getClassInfo(int page, int classId) {
        Observable<BaseJson<ClassInfoBean<CourseBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getClassInfo(classId,page, Constants.PAGE_SIZE,MyApplication.getInstance().getToken());
        return response;
    }
}
