package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ProgressAndScoreContract;
import fro.org.froproject.mvp.model.ProgressAndScoreModel;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@Module
public class ProgressAndScoreModule {
    private ProgressAndScoreContract.View view;

    /**
     * 构建ProgressAndScoreModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ProgressAndScoreModule(ProgressAndScoreContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ProgressAndScoreContract.View provideProgressAndScoreView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ProgressAndScoreContract.Model provideProgressAndScoreModel(ProgressAndScoreModel model) {
        return model;
    }
}