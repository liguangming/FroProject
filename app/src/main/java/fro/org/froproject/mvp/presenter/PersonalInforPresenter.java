package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;

import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.mvp.contract.PersonalInforContract;
import fro.org.froproject.mvp.ui.activity.CommonActivity;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

import javax.inject.Inject;

/**
 * Created by Lgm on 2017/6/2 0002.
 */

@ActivityScope
public class PersonalInforPresenter extends BasePresenter<PersonalInforContract.Model, PersonalInforContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    int orgDetailId =-999,orgTypeId = -999,orgNatureID = -999;
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
            intent.putExtra(Constants.REQUEST,Constants.ORG_NATURE);
           mRootView.launchActivityForResult(intent, 0);
        } else if (viewID == R.id.org_type) {
                intent.putExtra(Constants.REQUEST,Constants.ORG_TYPE);

                intent.putExtra(Constants.ORG_NATURE, orgNatureID);
            if (orgNatureID == -999) {
                mRootView.showMessage( mApplication.getString(R.string.check_org_nature));
            } else {
                mRootView.launchActivityForResult(intent, 0);
            }
        } else if (viewID == R.id.org_detail) {
            intent.putExtra(Constants.REQUEST,Constants.ORG_DETAIL);
            intent.putExtra(Constants.ORG_NATURE, orgNatureID);
            intent.putExtra(Constants.ORG_TYPE, orgTypeId);
            if (orgNatureID == -999 || orgTypeId == -999) {
                mRootView.showMessage( mApplication.getString(R.string.check_org_nature_and_type));
            } else {
                mRootView.launchActivityForResult(intent, 0);
            }
        }
    }

    public void launchActivityResult(int requestCode, int resultCode, Intent data) {//跳转返回的结果

    }
}