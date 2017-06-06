package fro.org.froproject.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.SexSelectContract;
import fro.org.froproject.mvp.model.SexSelectModel;


/**
 * Created by Lgm on 2017/6/6 0006.
 */

@Module
public class SexSelectModule {
    private SexSelectContract.View view;

    /**
     * 构建SexSelectModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SexSelectModule(SexSelectContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SexSelectContract.View provideSexSelectView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SexSelectContract.Model provideSexSelectModel(SexSelectModel model) {
        return model;
    }
}