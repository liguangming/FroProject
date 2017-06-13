package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.CourseNotPassedContract;
import fro.org.froproject.mvp.model.CourseNotPassedModel;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@Module
public class CourseNotPassedModule {
    private CourseNotPassedContract.View view;

    /**
     * 构建CourseNotPassedModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CourseNotPassedModule(CourseNotPassedContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CourseNotPassedContract.View provideCourseNotPassedView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CourseNotPassedContract.Model provideCourseNotPassedModel(CourseNotPassedModel model) {
        return model;
    }
}