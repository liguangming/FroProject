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
    int page;

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

    public void getMyClassList() {
        mModel.getMyClassList(page, Constants.PAGE_SIZE)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    if (true)
                        mRootView.showLoading();//显示上拉刷新的进度条
                    else
                        mRootView.startLoadMore();//显示下拉加载更多的进度条
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> {
                    if (true)
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    else
                        mRootView.endLoadMore();//隐藏下拉加载更多的进度条
                })
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson baseJson) {
                        if (baseJson.isSuccess()) {

                        } else {
                            if (!TextUtils.isEmpty(baseJson.getM()))
                                mRootView.showMessage(baseJson.getM());
                        }
//                        lastUserId = users.get(users.size() - 1).getId();//记录最后一个id,用于下一次请求
//                        if (pullToRefresh) mUsers.clear();//如果是上拉刷新则清空列表
//                        preEndIndex = mUsers.size();//更新之前列表总长度,用于确定加载更多的起始位置
//                        mUsers.addAll(users);
//                        if (pullToRefresh)
//                            mAdapter.notifyDataSetChanged();
//                        else
//                            mAdapter.notifyItemRangeInserted(preEndIndex, users.size());
                    }
                });
    }
}