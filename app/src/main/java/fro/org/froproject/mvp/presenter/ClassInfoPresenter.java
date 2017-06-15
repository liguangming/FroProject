package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.text.TextUtils;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;

import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.ClassInfoContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.ClassInfoBean;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import fro.org.froproject.mvp.model.entity.CourseBean;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/15 0015.
 */

@ActivityScope
public class ClassInfoPresenter extends BasePresenter<ClassInfoContract.Model, ClassInfoContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public ClassInfoPresenter(ClassInfoContract.Model model, ClassInfoContract.View rootView
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

    public void getClassInfo(int page, int classId) {
        mModel.getClassInfo(page, classId)
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
                            ClassInfoBean<CourseBean> classInfo = (ClassInfoBean) baseJson.getD();
                            mRootView.updateUi(classInfo);
                            if (page == 0) {
                                mRootView.setList(classInfo.getPagedResult().getDataList());
                            } else {
                                mRootView.addList(classInfo.getPagedResult().getDataList());
                            }
                            if (page + 1 == classInfo.getPagedResult().getPages()) {//加载完毕
                                mRootView.endLoadMore();
                            } else {
                                mRootView.stopLoadMore();
                            }
                        } else {
                            if (!TextUtils.isEmpty(baseJson.getM()))
                                mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}