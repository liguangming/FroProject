package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ClassContract;
import fro.org.froproject.mvp.model.ClassModel;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@Module
public class ClassModule {
    private ClassContract.View view;

    /**
     * 构建ClassModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ClassModule(ClassContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ClassContract.View provideClassView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ClassContract.Model provideClassModel(ClassModel model) {
        return model;
    }
}