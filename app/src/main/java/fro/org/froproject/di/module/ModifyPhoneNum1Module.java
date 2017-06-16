package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ModifyPhoneNum1Contract;
import fro.org.froproject.mvp.model.ModifyPhoneNum1Model;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@Module
public class ModifyPhoneNum1Module {
    private ModifyPhoneNum1Contract.View view;

    /**
     * 构建ModifyPhoneNum1Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ModifyPhoneNum1Module(ModifyPhoneNum1Contract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ModifyPhoneNum1Contract.View provideModifyPhoneNum1View() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ModifyPhoneNum1Contract.Model provideModifyPhoneNum1Model(ModifyPhoneNum1Model model) {
        return model;
    }
}