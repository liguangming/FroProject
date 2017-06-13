package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.CoursePassedModule;
import fro.org.froproject.mvp.ui.fragment.CoursePassedFragment;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@ActivityScope
@Component(modules = CoursePassedModule.class, dependencies = AppComponent.class)
public interface CoursePassedComponent {
    void inject(CoursePassedFragment activity);
}