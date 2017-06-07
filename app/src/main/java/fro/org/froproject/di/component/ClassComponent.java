package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.ClassModule;
import fro.org.froproject.mvp.ui.fragment.ClassFragment;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
@Component(modules = ClassModule.class, dependencies = AppComponent.class)
public interface ClassComponent {
    void inject(ClassFragment activity);
}