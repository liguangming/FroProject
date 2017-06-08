package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;

import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.ClassContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import fro.org.froproject.mvp.ui.adapter.ClassListAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/7 0007.
 */

@ActivityScope
public class ClassPresenter extends BasePresenter<ClassContract.Model, ClassContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    int mpage;

    @Inject
    public ClassPresenter(ClassContract.Model model, ClassContract.View rootView
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

    public void getMyClassList(final int page) {
        mpage=page;
        mModel.getMyClassList(page, Constants.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (0 == page) {
                        mRootView.showLoading();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    mRootView.hideLoading();
                })
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            List<ClassBean> list = ((ClassListBean) baseJson.getD()).getPagedResult().getDataList();
                            if(page==0){
                                mRootView.setList(list);
                            }else {
                                mRootView.add(list);
                            }
                            if (page + 1 == ((ClassListBean) baseJson.getD()).getPagedResult().getPages()) {//加载完毕
                                mRootView.endLoadMore();
                            } else {
                                mRootView.stopLoadMore();
                            }
                            mRootView.setJoinClassCount(((ClassListBean) baseJson.getD()).getJoinClassNumber());
                        } else {
                            if (!TextUtils.isEmpty(baseJson.getM()))
                                mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}