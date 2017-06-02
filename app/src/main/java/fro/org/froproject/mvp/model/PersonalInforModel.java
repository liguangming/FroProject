package fro.org.froproject.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import static com.jess.arms.utils.Preconditions.checkNotNull;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import fro.org.froproject.mvp.contract.PersonalInforContract;


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

}