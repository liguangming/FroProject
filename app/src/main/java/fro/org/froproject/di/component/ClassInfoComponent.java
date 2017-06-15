package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.ClassInfoModule;
import fro.org.froproject.mvp.ui.activity.ClassInfoActivity;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
@Component(modules = ClassInfoModule.class, dependencies = AppComponent.class)
public interface ClassInfoComponent {
    void inject(ClassInfoActivity activity);
}