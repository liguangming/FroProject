package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.CourseStudyContract;
import fro.org.froproject.mvp.model.CourseStudyModel;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@Module
public class CourseStudyModule {
    private CourseStudyContract.View view;

    /**
     * 构建CourseStudyModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CourseStudyModule(CourseStudyContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CourseStudyContract.View provideCourseStudyView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CourseStudyContract.Model provideCourseStudyModel(CourseStudyModel model) {
        return model;
    }
}