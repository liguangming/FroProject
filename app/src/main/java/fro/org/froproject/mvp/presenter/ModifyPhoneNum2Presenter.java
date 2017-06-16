package fro.org.froproject.mvp.presenter;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.fro.common.util.SharedUtils;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.ModifyPhoneNum2Contract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.ui.activity.LoginActivity;
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
public class ModifyPhoneNum2Presenter extends BasePresenter<ModifyPhoneNum2Contract.Model, ModifyPhoneNum2Contract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public ModifyPhoneNum2Presenter(ModifyPhoneNum2Contract.Model model, ModifyPhoneNum2Contract.View rootView
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

    public void commitModifyPhone2(String phone, String code, String token) {
        mModel.commitModifyPhone2(phone, code, token)
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
                            mRootView.showMessage("操作成功");
                            MyApplication.getInstance().getUserInfoBean().setPhoneNumber(phone);
                            SharedUtils.getInstance().remove(mApplication, "password");
                            SharedUtils.getInstance().put(mApplication, "phoneNumber", phone);
                            mAppManager.getActivityList().stream().filter(activity -> !(activity instanceof LoginActivity)).forEach(Activity::finish);
                            mRootView.launchActivity(new Intent(mApplication, LoginActivity.class));
                        } else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}