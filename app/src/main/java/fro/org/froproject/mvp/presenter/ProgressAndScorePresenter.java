package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;

import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.ProgressAndScoreContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import fro.org.froproject.mvp.model.entity.ScoreBean;
import fro.org.froproject.mvp.model.entity.ScoreClassBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/13 0013.
 */

@ActivityScope
public class ProgressAndScorePresenter extends BasePresenter<ProgressAndScoreContract.Model, ProgressAndScoreContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public ProgressAndScorePresenter(ProgressAndScoreContract.Model model, ProgressAndScoreContract.View rootView
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

    public void getScoreClassList(int page) {
        mModel.getScoreClassList(page)
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
                            if (baseJson.isSuccess()) {
                                ScoreBean<ScoreClassBean> data = (ScoreBean<ScoreClassBean>) baseJson.getD();
                                if (page == 0) {
                                    mRootView.setList(data.getPagedResult().getDataList());
                                } else {
                                    mRootView.addList(data.getPagedResult().getDataList());
                                }
                                if (page + 1 == data.getPagedResult().getPages() || data.getPagedResult().getPages() == 0) {
                                    mRootView.endLoadMore();
                                } else {
                                    mRootView.stopLoadMore();
                                }
                            } else {
                                mRootView.showMessage(baseJson.getM());
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseJson.getM()))
                                mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}