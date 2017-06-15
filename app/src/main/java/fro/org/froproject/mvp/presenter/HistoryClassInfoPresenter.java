package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;

import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.HistoryClassInfoContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import fro.org.froproject.mvp.model.entity.PagedResult;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
public class HistoryClassInfoPresenter extends BasePresenter<HistoryClassInfoContract.Model, HistoryClassInfoContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public HistoryClassInfoPresenter(HistoryClassInfoContract.Model model, HistoryClassInfoContract.View rootView
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

    public void getCourseList(int page, int classId) {
        mModel.getCourseList(page,classId)
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
                            PagedResult<CourseBean> mCourseResponseBean = (PagedResult<CourseBean>) baseJson.getD();
                            List<CourseBean> courseList = mCourseResponseBean.getDataList();
                            if (page + 1 == mCourseResponseBean.getPages()||mCourseResponseBean.getPages()==0) {
                                mRootView.setLoadComplete(true);
                            } else {
                                mRootView.stopLoadMore(true);
                            }
                            if (page == 0) {
                                mRootView.setList(mCourseResponseBean.getDataList());
                            } else {
                                mRootView.addList(mCourseResponseBean.getDataList());
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseJson.getM()))
                                mRootView.showMessage(baseJson.getM());
                        }
                    }
                });;

    }
}