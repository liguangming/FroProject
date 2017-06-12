package fro.org.froproject.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.HistoryContract;
import fro.org.froproject.mvp.model.HistoryModel;


/**
 * Created by Lgm on 2017/6/12 0012.
 */

@Module
public class HistoryModule {
    private HistoryContract.View view;

    /**
     * 构建HistoryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public HistoryModule(HistoryContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    HistoryContract.View provideHistoryView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    HistoryContract.Model provideHistoryModel(HistoryModel model) {
        return model;
    }
}