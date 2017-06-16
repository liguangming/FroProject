package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.ModifyPhoneNum2Contract;
import fro.org.froproject.mvp.model.ModifyPhoneNum2Model;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

@Module
public class ModifyPhoneNum2Module {
    private ModifyPhoneNum2Contract.View view;

    /**
     * 构建ModifyPhoneNum2Module时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ModifyPhoneNum2Module(ModifyPhoneNum2Contract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ModifyPhoneNum2Contract.View provideModifyPhoneNum2View() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ModifyPhoneNum2Contract.Model provideModifyPhoneNum2Model(ModifyPhoneNum2Model model) {
        return model;
    }
}