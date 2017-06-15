package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ModifyPasswordContract;
import fro.org.froproject.mvp.model.ModifyPasswordModel;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@Module
public class ModifyPasswordModule {
    private ModifyPasswordContract.View view;

    /**
     * 构建ModifyPasswordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ModifyPasswordModule(ModifyPasswordContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ModifyPasswordContract.View provideModifyPasswordView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ModifyPasswordContract.Model provideModifyPasswordModel(ModifyPasswordModel model) {
        return model;
    }
}