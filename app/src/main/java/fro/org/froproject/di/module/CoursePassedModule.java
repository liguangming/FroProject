package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.CoursePassedContract;
import fro.org.froproject.mvp.model.CoursePassedModel;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@Module
public class CoursePassedModule {
    private CoursePassedContract.View view;

    /**
     * 构建CoursePassedModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CoursePassedModule(CoursePassedContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CoursePassedContract.View provideCoursePassedView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CoursePassedContract.Model provideCoursePassedModel(CoursePassedModel model) {
        return model;
    }
}