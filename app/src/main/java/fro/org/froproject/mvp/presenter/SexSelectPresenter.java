package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import javax.inject.Inject;

import fro.org.froproject.mvp.contract.SexSelectContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by Lgm on 2017/6/6 0006.
 */

@ActivityScope
public class SexSelectPresenter extends BasePresenter<SexSelectContract.Model, SexSelectContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public SexSelectPresenter(SexSelectContract.Model model, SexSelectContract.View rootView
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