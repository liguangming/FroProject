package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ExerciseContract;
import fro.org.froproject.mvp.model.ExerciseModel;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@Module
public class ExerciseModule {
    private ExerciseContract.View view;

    /**
     * 构建ExerciseModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ExerciseModule(ExerciseContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ExerciseContract.View provideExerciseView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ExerciseContract.Model provideExerciseModel(ExerciseModel model) {
        return model;
    }
}