package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.CourseInfoContract;
import fro.org.froproject.mvp.model.CourseInfoModel;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@Module
public class CourseInfoModule {
    private CourseInfoContract.View view;

    /**
     * 构建CourseInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CourseInfoModule(CourseInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CourseInfoContract.View provideCourseInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CourseInfoContract.Model provideCourseInfoModel(CourseInfoModel model) {
        return model;
    }
}