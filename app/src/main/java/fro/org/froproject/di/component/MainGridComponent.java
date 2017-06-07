package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.MainGridModule;
import fro.org.froproject.mvp.ui.activity.MainGridActivity;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
@Component(modules = MainGridModule.class, dependencies = AppComponent.class)
public interface MainGridComponent {
    void inject(MainGridActivity activity);
}