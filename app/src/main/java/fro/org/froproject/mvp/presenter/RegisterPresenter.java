package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.HashMap;
import java.util.Map;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.mvp.contract.RegisterContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.Token;
import fro.org.froproject.mvp.ui.activity.PersonalInforActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/1 0001.
 */

@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.Model, RegisterContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public RegisterPresenter(RegisterContract.Model model, RegisterContract.View rootView
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
     * 注册提交
     */
    public void submit(String phoneNumber, String verificationCode, String password) {
        mModel.submit(phoneNumber, verificationCode, password)
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
                            Token token = (Token) baseJson.getD();
                            MyApplication.getInstance().setToken(token.getToken());
                            mRootView.launchActivity(new Intent(mApplication, PersonalInforActivity.class));
                        } else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}