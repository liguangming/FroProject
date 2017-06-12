package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.contract.HistoryContract;
import fro.org.froproject.mvp.model.api.service.CommonService;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import fro.org.froproject.mvp.model.entity.HistoryClassBean;
import fro.org.froproject.mvp.model.entity.HistoryClassListBean;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/12 0012.
 */

@ActivityScope
public class HistoryModel extends BaseModel implements HistoryContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public HistoryModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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
    public Observable<BaseJson<HistoryClassListBean<HistoryClassBean>>> getHistoryClassList(int page,int size) {
        Observable<BaseJson<HistoryClassListBean<HistoryClassBean>>> data = mRepositoryManager.obtainRetrofitService(CommonService.class).getHistoryClassList(MyApplication.getInstance().getToken(),page,size);
        return data;
    }

}
