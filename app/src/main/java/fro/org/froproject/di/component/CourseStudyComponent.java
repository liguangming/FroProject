package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.CourseStudyModule;
import fro.org.froproject.mvp.ui.activity.CourseStudyActivity;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

@ActivityScope
@Component(modules = CourseStudyModule.class, dependencies = AppComponent.class)
public interface CourseStudyComponent {
    void inject(CourseStudyActivity activity);
}