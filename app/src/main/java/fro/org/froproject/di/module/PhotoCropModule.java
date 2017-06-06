package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.PhotoCropContract;
import fro.org.froproject.mvp.model.PhotoCropModel;


/**
 * Created by Lgm on 2017/6/6 0006.
 */

@Module
public class PhotoCropModule {
    private PhotoCropContract.View view;

    /**
     * 构建PhotoCropModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PhotoCropModule(PhotoCropContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PhotoCropContract.View providePhotoCropView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PhotoCropContract.Model providePhotoCropModel(PhotoCropModel model) {
        return model;
    }
}