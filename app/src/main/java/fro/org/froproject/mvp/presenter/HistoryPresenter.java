package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.HistoryContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.HistoryClassListBean;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.ui.activity.MainGridActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.internal.schedulers.ScheduledRunnable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/12 0012.
 */

@ActivityScope
public class HistoryPresenter extends BasePresenter<HistoryContract.Model, HistoryContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    protected HistoryPresenter(HistoryContract.Model model, HistoryContract.View rootView
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

    public void getHistoryClassList(int page, int size) {
        mModel.getHistoryClassList(page, size)   .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.<BaseJson>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            HistoryClassListBean history= (HistoryClassListBean) baseJson.getD();
                            if(page==0){
                                mRootView.setList(history.getPagedResult().getDataList());
                            }else {
                                mRootView.addList(history.getPagedResult().getDataList());
                            }
                        }else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });;
    }
}