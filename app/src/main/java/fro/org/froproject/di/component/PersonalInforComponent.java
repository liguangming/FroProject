package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.PersonalInforModule;
import fro.org.froproject.mvp.ui.activity.PersonalInforActivity;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@ActivityScope
@Component(modules = PersonalInforModule.class, dependencies = AppComponent.class)
public interface PersonalInforComponent {
    void inject(PersonalInforActivity activity);
}