package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ResultContract;
import fro.org.froproject.mvp.model.ResultModel;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@Module
public class ResultModule {
    private ResultContract.View view;

    /**
     * 构建ResultModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ResultModule(ResultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ResultContract.View provideResultView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ResultContract.Model provideResultModel(ResultModel model) {
        return model;
    }
}