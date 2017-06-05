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
import fro.org.froproject.mvp.contract.PersonalInforContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.OrgBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@ActivityScope
public class PersonalInforModel extends BaseModel implements PersonalInforContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public PersonalInforModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<OrgBean>>> getOrgDetailList(int orgTypeId) {
        Observable<BaseJson<List<OrgBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getOrgDetailList(orgTypeId);
        return response;
    }
}
