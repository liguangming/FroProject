package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.PersonalInforContract;
import fro.org.froproject.mvp.model.PersonalInforModel;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@Module
public class PersonalInforModule {
    private PersonalInforContract.View view;

    /**
     * 构建PersonalInforModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PersonalInforModule(PersonalInforContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PersonalInforContract.View providePersonalInforView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PersonalInforContract.Model providePersonalInforModel(PersonalInforModel model) {
        return model;
    }
}