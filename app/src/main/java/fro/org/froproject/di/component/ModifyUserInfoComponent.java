package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.ModifyUserInfoModule;
import fro.org.froproject.mvp.ui.activity.ModifyUserInfoActivity;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@ActivityScope
@Component(modules = ModifyUserInfoModule.class, dependencies = AppComponent.class)
public interface ModifyUserInfoComponent {
    void inject(ModifyUserInfoActivity activity);
}