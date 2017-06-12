package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.HistoryModule;
import fro.org.froproject.mvp.ui.activity.HistoryActivity;


/**
 * Created by Lgm on 2017/6/12 0012.
 */

@ActivityScope
@Component(modules = HistoryModule.class, dependencies = AppComponent.class)
public interface HistoryComponent {
    void inject(HistoryActivity activity);
}