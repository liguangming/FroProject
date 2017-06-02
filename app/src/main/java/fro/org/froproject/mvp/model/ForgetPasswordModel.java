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

import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.mvp.contract.ForgetPasswordContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by Lgm on 2017/6/1 0001.
 */

@ActivityScope
public class ForgetPasswordModel extends BaseModel implements ForgetPasswordContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ForgetPasswordModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson> submit(String phone, String verificationCode, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("password", Utils.encodePassword(password));
        map.put("phoneNumber",phone);
        map.put("verificationCode", verificationCode);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), mGson.toJson(map));
        Observable<BaseJson> response = mRepositoryManager.obtainRetrofitService(CommonService.class).submit(body);
        return response;
    }


}
