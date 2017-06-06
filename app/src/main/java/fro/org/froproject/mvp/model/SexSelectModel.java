package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import javax.inject.Inject;

import fro.org.froproject.mvp.contract.SexSelectContract;


/**
 * Created by Lgm on 2017/6/6 0006.
 */

@ActivityScope
public class SexSelectModel extends BaseModel implements SexSelectContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SexSelectModel(IRepositoryManager repositoryManager, Gson gson, Application application) {
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

}
