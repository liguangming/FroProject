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
import fro.org.froproject.mvp.contract.ModifyPhoneNum2Contract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@ActivityScope
public class ModifyPhoneNum2Model extends BaseModel implements ModifyPhoneNum2Contract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ModifyPhoneNum2Model(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> commitModifyPhone2(String phone, String code, String token) {
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("phoneNumber", phone);
        map.put("verificationCode", code);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(map));
        Observable<BaseJson> response = mRepositoryManager.obtainRetrofitService(CommonService.class).commitModifyPhone2(body, MyApplication.getInstance().getToken());
        return response;
    }
}
