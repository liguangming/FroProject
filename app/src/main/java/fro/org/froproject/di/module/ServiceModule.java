package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ServiceContract;
import fro.org.froproject.mvp.model.ServiceModel;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@Module
public class ServiceModule {
    private ServiceContract.View view;

    /**
     * 构建ServiceModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ServiceModule(ServiceContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ServiceContract.View provideServiceView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ServiceContract.Model provideServiceModel(ServiceModel model) {
        return model;
    }
}