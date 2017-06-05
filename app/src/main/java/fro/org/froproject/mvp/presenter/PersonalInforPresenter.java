package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.fro.common.util.TimeUtils;
import org.fro.common.widgets.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.PersonalInforContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.OrgBean;
import fro.org.froproject.mvp.ui.activity.CommonActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static fro.org.froproject.app.Constants.DEFAULT_ID;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@ActivityScope
public class PersonalInforPresenter extends BasePresenter<PersonalInforContract.Model, PersonalInforContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    int orgDetailId = DEFAULT_ID, orgTypeId = DEFAULT_ID, orgNatureID = DEFAULT_ID, workYearId = DEFAULT_ID, credentialsId = DEFAULT_ID;
    private String orgNatureName, orgTypeName, orgDetailName, workYearName, credentialsName;

    @Inject
    public PersonalInforPresenter(PersonalInforContract.Model model, PersonalInforContract.View rootView
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

    public void gotoActivity(int viewID) {
        Intent intent = new Intent(mApplication, CommonActivity.class);
        if (viewID == R.id.org_nature) {
            intent.putExtra(Constants.REQUEST, Constants.ORG_NATURE);
            mRootView.launchActivityForResult(intent, Constants.RESULT_1001);
        } else if (viewID == R.id.org_type) {
            intent.putExtra(Constants.REQUEST, Constants.ORG_TYPE);
            intent.putExtra(Constants.ORG_NATURE, orgNatureID);
            if (orgNatureID == DEFAULT_ID) {
                mRootView.showMessage(mApplication.getString(R.string.check_org_nature));
            } else {
                mRootView.launchActivityForResult(intent, Constants.RESULT_1002);
            }
        } else if (viewID == R.id.org_detail) {
            intent.putExtra(Constants.REQUEST, Constants.ORG_DETAIL);
            intent.putExtra(Constants.ORG_NATURE, orgNatureID);
            intent.putExtra(Constants.ORG_TYPE, orgTypeId);
            if (orgNatureID == DEFAULT_ID || orgTypeId == DEFAULT_ID) {
                mRootView.showMessage(mApplication.getString(R.string.check_org_nature_and_type));
            } else {
                mRootView.launchActivityForResult(intent, Constants.RESULT_1003);
            }
        } else if (viewID == R.id.credentials) {//证件
            intent.putExtra(Constants.REQUEST, Constants.CREDENTIALS_TYPE);
            mRootView.launchActivityForResult(intent, Constants.RESULT_1004);
        } else if (viewID == R.id.work_life) {//工作年限
            intent.putExtra(Constants.REQUEST, Constants.WORK_YEAR);
            mRootView.launchActivityForResult(intent, Constants.RESULT_1005);
        }
    }

    public void launchActivityResult(int requestCode, int resultCode, Intent intent) {//跳转返回的结果
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RESULT_1001) {
                orgNatureID = intent.getIntExtra(Constants.ID, DEFAULT_ID);
                orgNatureName = intent.getStringExtra("name");
                mRootView.setName(Constants.RESULT_1001, orgNatureName);

                orgTypeId = Constants.DEFAULT_ID;
                mRootView.setName(Constants.RESULT_1002, mApplication.getString(R.string.not_setting));
            } else if (requestCode == Constants.RESULT_1002) {
                orgTypeId = intent.getIntExtra(Constants.ID, DEFAULT_ID);
                orgTypeName = intent.getStringExtra("name");
                mRootView.setName(Constants.RESULT_1002, orgTypeName);
                getOrgDetailList(orgTypeId);

                orgDetailId = Constants.DEFAULT_ID;
            } else if (requestCode == Constants.RESULT_1003) {
                orgDetailId = intent.getIntExtra(Constants.ID, DEFAULT_ID);
                orgDetailName = intent.getStringExtra("name");
                mRootView.setName(Constants.RESULT_1003, orgDetailName);
            } else if (requestCode == Constants.RESULT_1004) {
                credentialsId = intent.getIntExtra(Constants.ID, DEFAULT_ID);
                credentialsName = intent.getStringExtra("name");
                mRootView.setName(Constants.RESULT_1004, credentialsName);
                mRootView.clearCredentialNum();
            } else if (requestCode == Constants.RESULT_1005) {
                workYearId = intent.getIntExtra(Constants.ID, DEFAULT_ID);
                workYearName = intent.getStringExtra("name");
                mRootView.setName(Constants.RESULT_1005, workYearName);
            }
        }

    }

    /**
     * 获取具体组织
     *
     * @param orgTypeId
     */

    private void getOrgDetailList(int orgTypeId) {
        mModel.getOrgDetailList(orgTypeId)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(dispose -> mRootView.showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.bindToLifecycle(mRootView))
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        List<OrgBean> orgDetailList = (List<OrgBean>) baseJson.getD();
                        if (orgDetailList.isEmpty()) {
                            mRootView.setName(Constants.RESULT_1003, mApplication.getString(R.string.org_detail_tips));
                        } else {
                            mRootView.setName(Constants.RESULT_1003, mApplication.getString(R.string.not_setting));
                        }
                    }
                });
    }

    /**
     * 显示日历选择对话框
     */
    public void showLocationDialog() {
    }

}