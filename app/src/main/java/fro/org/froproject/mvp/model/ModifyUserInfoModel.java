package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.ModifyUserInfoContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CommonBean;
import fro.org.froproject.mvp.model.entity.OrgBean;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@ActivityScope
public class ModifyUserInfoModel extends BaseModel implements ModifyUserInfoContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ModifyUserInfoModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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


    @Override
    public Observable<BaseJson> commit(Map<String, String> data) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(data));
        Observable<BaseJson> response = mRepositoryManager.obtainRetrofitService(CommonService.class).commit(body, MyApplication.getInstance().getToken());
        return response;
    }

    @Override
    public Observable<BaseJson<CommonBean>> uploadImg(File file) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (file != null) {
            builder.addFormDataPart("upfile", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
        }
        MultipartBody requestBody = builder.build();
        Observable<BaseJson<CommonBean>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).uploadImg(requestBody, MyApplication.getInstance().getToken());
        return response;
    }
}
