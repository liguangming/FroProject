package fro.org.froproject.mvp.presenter;

import android.app.Application;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.CoursePassedContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/13 0013.
 */

@ActivityScope
public class CoursePassedPresenter extends BasePresenter<CoursePassedContract.Model, CoursePassedContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public CoursePassedPresenter(CoursePassedContract.Model model, CoursePassedContract.View rootView
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

    public void getCourseList(int page, int pageSize) {
        mModel.getCourseList(page, pageSize)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            CourseResponseBean courseList = (CourseResponseBean) baseJson.getD();
                            if (page == 0) {
                                mRootView.setList(courseList.getDataList());
                            } else {
                                mRootView.addList(courseList.getDataList());
                            }
                            if (page + 1 == courseList.getPages() || courseList.getPages() == 0) {
                                mRootView.endLoadMore();
                            } else {
                                mRootView.stopLoadMore();
                            }
                        } else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}