package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.HistoryClassInfoModule;
import fro.org.froproject.mvp.ui.activity.HistoryClassInfoActivity;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
@Component(modules = HistoryClassInfoModule.class, dependencies = AppComponent.class)
public interface HistoryClassInfoComponent {
    void inject(HistoryClassInfoActivity activity);
}