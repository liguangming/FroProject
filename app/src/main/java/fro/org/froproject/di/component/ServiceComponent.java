package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.ServiceModule;
import fro.org.froproject.mvp.ui.fragment.ServiceFragment;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
@Component(modules = ServiceModule.class, dependencies = AppComponent.class)
public interface ServiceComponent {
    void inject(ServiceFragment activity);
}