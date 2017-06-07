package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.ClassContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import fro.org.froproject.mvp.model.entity.OrgBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
public class ClassModel extends BaseModel implements ClassContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ClassModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<ClassListBean<ClassBean>>> getMyClassList(int page, int pageSize) {
        Observable<BaseJson<ClassListBean<ClassBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getMyClassList(MyApplication.getInstance().getToken(), page, pageSize);
        return response;
    }
}
