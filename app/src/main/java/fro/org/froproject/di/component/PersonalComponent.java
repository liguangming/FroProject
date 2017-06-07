package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.PersonalModule;
import fro.org.froproject.mvp.ui.fragment.PersonalFragment;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
@Component(modules = PersonalModule.class, dependencies = AppComponent.class)
public interface PersonalComponent {
    void inject(PersonalFragment activity);
}