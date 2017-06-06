package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.SexSelectModule;
import fro.org.froproject.mvp.ui.activity.SexSelectActivity;


/**
 * Created by Lgm on 2017/6/6 0006.
 */

@ActivityScope
@Component(modules = SexSelectModule.class, dependencies = AppComponent.class)
public interface SexSelectComponent {
    void inject(SexSelectActivity activity);
}