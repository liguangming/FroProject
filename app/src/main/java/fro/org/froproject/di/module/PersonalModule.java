package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.PersonalContract;
import fro.org.froproject.mvp.model.PersonalModel;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@Module
public class PersonalModule {
    private PersonalContract.View view;

    /**
     * 构建PersonalModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PersonalModule(PersonalContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PersonalContract.View providePersonalView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PersonalContract.Model providePersonalModel(PersonalModel model) {
        return model;
    }
}