package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.ProgressAndScoreModule;
import fro.org.froproject.mvp.ui.activity.ProgressAndScoreActivity;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

@ActivityScope
@Component(modules = ProgressAndScoreModule.class, dependencies = AppComponent.class)
public interface ProgressAndScoreComponent {
    void inject(ProgressAndScoreActivity activity);
}