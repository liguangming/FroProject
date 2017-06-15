package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.HistoryClassInfoContract;
import fro.org.froproject.mvp.model.HistoryClassInfoModel;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@Module
public class HistoryClassInfoModule {
    private HistoryClassInfoContract.View view;

    /**
     * 构建HistoryClassInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HistoryClassInfoModule(HistoryClassInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HistoryClassInfoContract.View provideHistoryClassInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HistoryClassInfoContract.Model provideHistoryClassInfoModel(HistoryClassInfoModel model) {
        return model;
    }
}