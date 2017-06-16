package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.ModifyPhoneNum1Contract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CommonBean;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@ActivityScope
public class ModifyPhoneNum1Model extends BaseModel implements ModifyPhoneNum1Contract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ModifyPhoneNum1Model(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> getAuthCode(String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", phone);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(map));
        Observable<BaseJson> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getAuthCode(body);
        return response;
    }

    @Override
    public Observable<BaseJson<CommonBean>> commitModifyPhone1(String authCode, String phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", phone);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(map));
        Observable<BaseJson<CommonBean>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).commitModifyPhone1(body, MyApplication.getInstance().getToken());
        return response;
    }
}
