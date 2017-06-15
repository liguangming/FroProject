package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ClassInfoContract;
import fro.org.froproject.mvp.model.ClassInfoModel;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@Module
public class ClassInfoModule {
    private ClassInfoContract.View view;

    /**
     * 构建ClassInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ClassInfoModule(ClassInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ClassInfoContract.View provideClassInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ClassInfoContract.Model provideClassInfoModel(ClassInfoModel model) {
        return model;
    }
}