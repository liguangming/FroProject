package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.CourseModule;
import fro.org.froproject.mvp.ui.fragment.CourseFragment;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
@Component(modules = CourseModule.class, dependencies = AppComponent.class)
public interface CourseComponent {
    void inject(CourseFragment activity);
}