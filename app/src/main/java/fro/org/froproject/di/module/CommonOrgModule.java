package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.CommonOrgContract;
import fro.org.froproject.mvp.model.CommonOrgModel;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@Module
public class CommonOrgModule {
    private CommonOrgContract.View view;

    /**
     * 构建CommonOrgModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommonOrgModule(CommonOrgContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommonOrgContract.View provideCommonOrgView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommonOrgContract.Model provideCommonOrgModel(CommonOrgModel model) {
        return model;
    }
}