package fro.org.froproject.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.fro.common.util.SharedUtils;

import javax.inject.Inject;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.ModifyPasswordContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.ui.activity.LoginActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
public class ModifyPasswordPresenter extends BasePresenter<ModifyPasswordContract.Model, ModifyPasswordContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public ModifyPasswordPresenter(ModifyPasswordContract.Model model, ModifyPasswordContract.View rootView
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

    public void modifyPassword(String oldPasswordStr, String newPasswordStr) {
        mModel.modifyPassword(oldPasswordStr, newPasswordStr)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            mRootView.showMessage("修改密码成功");
                            SharedUtils.getInstance().remove(mApplication, "password");
                            mRootView.launchActivity(new Intent(MyApplication.getInstance(), LoginActivity.class));
                            mAppManager.getActivityList().stream().filter(activity -> !(activity instanceof LoginActivity)).forEach(Activity::finish);
                        } else {
                            if (!TextUtils.isEmpty(baseJson.getM()))
                                mRootView.showMessage(baseJson.getM());
                        }
                    }
                });

    }
}