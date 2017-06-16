package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.ModifyPhoneNum2Module;
import fro.org.froproject.mvp.ui.activity.ModifyPhoneNum2Activity;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@ActivityScope
@Component(modules = ModifyPhoneNum2Module.class, dependencies = AppComponent.class)
public interface ModifyPhoneNum2Component {
    void inject(ModifyPhoneNum2Activity activity);
}