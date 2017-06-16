package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.media.session.MediaSession;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.ModifyPhoneNum1Contract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CommonBean;
import fro.org.froproject.mvp.ui.activity.ModifyPhoneNum2Activity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/16 0016.
 */

@ActivityScope
public class ModifyPhoneNum1Presenter extends BasePresenter<ModifyPhoneNum1Contract.Model, ModifyPhoneNum1Contract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public ModifyPhoneNum1Presenter(ModifyPhoneNum1Contract.Model model, ModifyPhoneNum1Contract.View rootView
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

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void getAuthCode(String phone) {
        mModel.getAuthCode(phone)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dispose -> mRootView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            mRootView.showCountView();
                        } else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }

    /**
     * 提交
     *
     * @param authCode
     * @param phone
     */
    public void commitModifyPhone1(String authCode, String phone) {
        mModel.commitModifyPhone1(authCode, phone)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dispose -> mRootView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            CommonBean commonBean = (CommonBean) baseJson.getD();
                            Intent intent = new Intent();
                            intent.putExtra("token", commonBean.getToken());
                            intent.setClass(mApplication, ModifyPhoneNum2Activity.class);
                            mRootView.launchActivity(intent);
                        } else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}