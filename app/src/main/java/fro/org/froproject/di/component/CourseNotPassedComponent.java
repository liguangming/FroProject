package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.CourseNotPassedModule;
import fro.org.froproject.mvp.ui.fragment.CourseNotPassedFragment;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@ActivityScope
@Component(modules = CourseNotPassedModule.class, dependencies = AppComponent.class)
public interface CourseNotPassedComponent {
    void inject(CourseNotPassedFragment activity);
}