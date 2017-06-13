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

import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.ProgressAndScoreContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import fro.org.froproject.mvp.model.entity.ScoreBean;
import fro.org.froproject.mvp.model.entity.ScoreClassBean;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@ActivityScope
public class ProgressAndScoreModel extends BaseModel implements ProgressAndScoreContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public ProgressAndScoreModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<ScoreBean<ScoreClassBean>>> getScoreClassList(int page) {
        Observable<BaseJson<ScoreBean<ScoreClassBean>>> response = mRepositoryManager.obtainRetrofitService(CommonService.class).getScoreClassList(page, Constants.PAGE_SIZE,MyApplication.getInstance().getToken());
        return response;
    }
}
