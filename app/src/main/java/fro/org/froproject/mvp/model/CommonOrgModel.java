package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.CommonOrgContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.OrgBean;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@ActivityScope
public class CommonOrgModel extends BaseModel implements CommonOrgContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public CommonOrgModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<List<OrgBean>>> getNatureList() {
        Observable<BaseJson<List<OrgBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getNatureList();
        return response;
    }

    @Override
    public Observable<BaseJson<List<OrgBean>>> getOrgTypeList(int natureId) {
        Observable<BaseJson<List<OrgBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getOrgTypeList(natureId);
        return response;
    }

    @Override
    public Observable<BaseJson<List<OrgBean>>> getOrgDetailList(int orgTypeId) {
        Observable<BaseJson<List<OrgBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getOrgDetailList(orgTypeId);
        return response;
    }

    @Override
    public Observable<BaseJson<List<OrgBean>>> getWorkYearList() {
        Observable<BaseJson<List<OrgBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getWorkYearList();
        return response;
    }

    @Override
    public Observable<BaseJson<List<OrgBean>>> getCredentials() {
        Observable<BaseJson<List<OrgBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getCredentials(MyApplication.getInstance().getToken());
        return response;
    }



}
