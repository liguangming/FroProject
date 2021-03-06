package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.CourseInfoModule;
import fro.org.froproject.mvp.ui.activity.CourseInfoActivity;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@ActivityScope
@Component(modules = CourseInfoModule.class, dependencies = AppComponent.class)
public interface CourseInfoComponent {
    void inject(CourseInfoActivity activity);
}