package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.util.List;
import java.util.Map;

import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.ExerciseContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.model.entity.ExerciseBean;
import fro.org.froproject.mvp.model.entity.ExerciseResponseBean;
import fro.org.froproject.mvp.ui.activity.ResultActivity;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/14 0014.
 */

@ActivityScope
public class ExercisePresenter extends BasePresenter<ExerciseContract.Model, ExerciseContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public ExercisePresenter(ExerciseContract.Model model, ExerciseContract.View rootView
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

    public void getQuestionList(int classId, int courseId) {
        mModel.getQuestionList(classId, courseId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(pose -> mRootView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            List<ExerciseBean> exerciseBean = (List<ExerciseBean>) baseJson.getD();
                            if (exerciseBean.get(0).getCourseExerCises() != null && exerciseBean.get(0).getCourseExerCises().size() != 0)
                                mRootView.setList(exerciseBean);
                        } else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }

    public void commit(CourseBean course, Map<String, Object> resultMap) {
        mModel.commit(course.getClassId(), course.getId(), resultMap)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(pose -> mRootView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {
                            ExerciseResponseBean mExerciseResponseBean = (ExerciseResponseBean) baseJson.getD();
                            Intent intent = new Intent(mApplication, ResultActivity.class);
                            intent.putExtra(Constants.RESULT, mExerciseResponseBean);
                            intent.putExtra(Constants.COURSE, course);
                            mRootView.launchActivity(intent);
                        } else {
                            mRootView.showMessage(baseJson.getM());
                        }
                    }
                });
    }
}