package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.ModifyPhoneNum1Module;
import fro.org.froproject.mvp.ui.activity.ModifyPhoneNum1Activity;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@ActivityScope
@Component(modules = ModifyPhoneNum1Module.class, dependencies = AppComponent.class)
public interface ModifyPhoneNum1Component {
    void inject(ModifyPhoneNum1Activity activity);
}