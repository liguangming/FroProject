package fro.org.froproject.di.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import fro.org.froproject.mvp.contract.SearchResultContract;
import fro.org.froproject.mvp.model.SearchResultModel;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@Module
public class SearchResultModule {
    private SearchResultContract.View view;

    /**
     * 构建SearchResultModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public SearchResultModule(SearchResultContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    SearchResultContract.View provideSearchResultView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    SearchResultContract.Model provideSearchResultModel(SearchResultModel model) {
        return model;
    }
}