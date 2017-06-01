package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.RegisterModule;
import fro.org.froproject.mvp.ui.activity.RegisterActivity;


/**
 * Created by Lgm on 2017/6/1 0001.
 */

@ActivityScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {
    void inject(RegisterActivity activity);
}