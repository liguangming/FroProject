package fro.org.froproject.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import fro.org.froproject.mvp.contract.PersonalContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
public class PersonalPresenter extends BasePresenter<PersonalContract.Model, PersonalContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public PersonalPresenter(PersonalContract.Model model, PersonalContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}