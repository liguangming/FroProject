package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.CourseContract;
import fro.org.froproject.mvp.model.CourseModel;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@Module
public class CourseModule {
    private CourseContract.View view;

    /**
     * 构建CourseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CourseModule(CourseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CourseContract.View provideCourseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CourseContract.Model provideCourseModel(CourseModel model) {
        return model;
    }
}