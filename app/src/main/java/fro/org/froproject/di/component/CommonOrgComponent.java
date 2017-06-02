package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.CommonOrgModule;
import fro.org.froproject.mvp.ui.activity.CommonActivity;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@ActivityScope
@Component(modules = CommonOrgModule.class, dependencies = AppComponent.class)
public interface CommonOrgComponent {
    void inject(CommonActivity activity);
}