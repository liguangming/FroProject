package fro.org.froproject.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import fro.org.froproject.di.module.SearchResultModule;
import fro.org.froproject.mvp.ui.activity.SearchResultActivity;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
@Component(modules = SearchResultModule.class, dependencies = AppComponent.class)
public interface SearchResultComponent {
    void inject(SearchResultActivity activity);
}