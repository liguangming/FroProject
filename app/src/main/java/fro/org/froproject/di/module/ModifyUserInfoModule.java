package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ModifyUserInfoContract;
import fro.org.froproject.mvp.model.ModifyUserInfoModel;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@Module
public class ModifyUserInfoModule {
    private ModifyUserInfoContract.View view;

    /**
     * 构建ModifyUserInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ModifyUserInfoModule(ModifyUserInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ModifyUserInfoContract.View provideModifyUserInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ModifyUserInfoContract.Model provideModifyUserInfoModel(ModifyUserInfoModel model) {
        return model;
    }
}