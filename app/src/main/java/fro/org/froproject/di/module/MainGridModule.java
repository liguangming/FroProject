package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.MainGridContract;
import fro.org.froproject.mvp.model.MainGridModel;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@Module
public class MainGridModule {
    private MainGridContract.View view;

    /**
     * 构建MainGridModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public MainGridModule(MainGridContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    MainGridContract.View provideMainGridView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    MainGridContract.Model provideMainGridModel(MainGridModel model) {
        return model;
    }
}