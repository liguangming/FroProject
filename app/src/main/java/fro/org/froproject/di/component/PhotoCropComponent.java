package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.PhotoCropModule;
import fro.org.froproject.mvp.ui.activity.PhotoCropActivity;


/**
 * Created by Lgm on 2017/6/6 0006.
 */

@ActivityScope
@Component(modules = PhotoCropModule.class, dependencies = AppComponent.class)
public interface PhotoCropComponent {
    void inject(PhotoCropActivity activity);
}