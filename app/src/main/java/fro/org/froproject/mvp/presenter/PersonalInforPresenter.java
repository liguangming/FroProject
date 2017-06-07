package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.fro.common.util.ImageUtils;
import org.fro.common.widgets.locationview.SSQPickerDialog;
import org.fro.common.widgets.locationview.entity.CityData;
import org.fro.common.widgets.locationview.entity.CountryData;
import org.fro.common.widgets.locationview.entity.ProvinceData;
import org.fro.common.widgets.photoclop.UCrop;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.FileUtils;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.mvp.contract.PersonalInforContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CommonBean;
import fro.org.froproject.mvp.model.entity.CredentialsBean;
import fro.org.froproject.mvp.model.entity.OrgBean;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.model.entity.WorkYear;
import fro.org.froproject.mvp.ui.activity.CommonActivity;
import fro.org.froproject.mvp.ui.activity.MainGridActivity;
import fro.org.froproject.mvp.ui.activity.RegisterActivity;
import fro.org.froproject.mvp.ui.activity.SexSelectActivity;
import fro.org.froproject.mvp.ui.view.photoclip.PhotoConfig;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;

import static android.app.Activity.RESULT_OK;
import static fro.org.froproject.app.Constants.DEFAULT_ID;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

@ActivityScope
public class PersonalInforPresenter extends BasePresenter<PersonalInforContract.Model, PersonalInforContract.View> {
    private static final int REQUEST_CODE_SELECT = 10;  //从相册选取
    public static final int REQUEST_CODE_CAMERA = 11; // 从相机选择
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;
    int orgDetailId = DEFAULT_ID, orgTypeId = DEFAULT_ID, orgNatureID = DEFAULT_ID, workYearId = DEFAULT_ID, credentialsId = DEFAULT_ID;
    private String orgNatureName, orgTypeName, orgDetailName, workYearName, credentialsName;
    public int regionId = DEFAULT_ID;
    public CountryData mCountry;
    public CityData mCity;
    public ProvinceData mProvice;
    private boolean isFromAlbum;//是否来自相册
    private Uri mDestinationUri;
    public String path;

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
        } else if (viewID == R.id.sex) {
            Intent mIntent = new Intent(mApplication, SexSelectActivity.class);
            mIntent.putExtra(Constants.REQUEST, Constants.SEX);
            mRootView.launchActivityForResult(mIntent, Constants.RESULT_1006);
        }
    }


    /**
     * 从相册中获取
     *
     * @param mDestinationUri
     */
    public void pickFromGallery(Uri mDestinationUri) {
        this.mDestinationUri = mDestinationUri;
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mRootView.launchActivityForResult(intent, REQUEST_CODE_SELECT);
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
            } else if (requestCode == Constants.RESULT_1006) {
                mRootView.setSex(intent.getStringExtra(Constants.SEX));

            } else if (requestCode == REQUEST_CODE_CAMERA) {// 调用相机拍照
                isFromAlbum = false;
                startCropActivity(Uri.fromFile(PhotoConfig.getPhotoTempFile(mApplication)));
            } else if (requestCode == REQUEST_CODE_SELECT) { // 直接从相册获取
                isFromAlbum = true;
                startCropActivity(intent.getData());
            } else if (requestCode == UCrop.REQUEST_CROP) {// 裁剪图片结果
                isFromAlbum = false;
                handleCropResult(intent);
            } else if (requestCode == UCrop.RESULT_ERROR) {// 裁剪图片错误
                isFromAlbum = false;
//                handleCropError(intent);

            }
        }

    }

    private void handleCropResult(Intent result) {
        PhotoConfig.deletePhotoTempFile(mApplication);
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri) {
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(mApplication.getContentResolver(), resultUri);
                int degree = ImageUtils.readPictureDegree(resultUri.getPath());
                if (degree != 0) {//需要旋转图片
                    bitmap = ImageUtils.rotateBitmap(bitmap, degree);
                }
                Bitmap roundBitmap = ImageUtils.toRoundBitmap(bitmap);
                mRootView.setAtvorImage(roundBitmap);
                //存储临时文件 用作上传
                uploadImg(FileUtils.saveFile(mApplication, roundBitmap, "temp.png"));
            } catch (Exception e) {
                e.printStackTrace();
                mRootView.showMessage("操作失败");
            }
        } else {
            mRootView.showMessage("操作失败");
        }
    }

    public void uploadImg(File file) {
        mModel.uploadImg(file)
                .subscribeOn(Schedulers.io())
                .compose(RxUtils.bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {//
                            CommonBean bean = (CommonBean) baseJson.getD();
                            path = bean.getPath();
                        }
                    }
                });
    }

    /**
     * 进入图片裁剪界面
     *
     * @param uri
     */
    private void startCropActivity(Uri uri) {
        mRootView.startCropActivity(uri);

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
        SSQPickerDialog mPickerDialog = new SSQPickerDialog(mAppManager.getCurrentActivity(), mProvice, mCity, mCountry, MyApplication.getInstance().getProvinceDataList());
        mPickerDialog.setDialogMode(SSQPickerDialog.DIALOG_MODE_BOTTOM);
        mPickerDialog.show();
        mPickerDialog.setDatePickListener((selectProvince, selectCity, selectCounty) -> {
            mRootView.setLocation(selectProvince.getName() + selectCity.getName() + selectCounty.getName());
            regionId = selectCounty.getId();
            mProvice = selectProvince;
            mCity = selectCity;
            mCountry = selectCounty;
        });
    }

    /**
     * 提交信息
     *
     * @param data
     */
    public void commit(Map<String, String> data) {
        if (!checkNull())
            return;
        if (!TextUtils.isEmpty(path))
            data.put("avatar", path);
        if (regionId != -999)
            data.put("regionId", String.valueOf(regionId));
        data.put("natureId", String.valueOf(orgNatureID));
        data.put("categoryId", String.valueOf(orgTypeId));
        if (orgDetailId != -999)
            data.put("organizationId ", String.valueOf(orgDetailId));
        if (workYearId != -999)
            data.put("workYearId", String.valueOf(workYearId));
        data.put("idTypeId", String.valueOf(credentialsId));
        mModel.commit(data)
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
                            mRootView.killMyself();
                            mAppManager.killActivity(RegisterActivity.class);
                            mRootView.launchActivity(new Intent(mApplication, MainGridActivity.class));
                            setUserInfo(data);
                            // TODO: 2017/6/6 0006 完成注册之后关闭两个注册界面 跳转到主页
                        } else {
                            if (!TextUtils.isEmpty(baseJson.getM()))
                                mRootView.showMessage(baseJson.getM());
                            else
                                mRootView.showMessage(mApplication.getString(R.string.message_err));
                        }
                    }
                });
    }

    public boolean checkNull() {
        if (credentialsId == -999) {
            mRootView.showMessage(mApplication.getString(R.string.credentials_not_null_tips));
            return false;
        }
        if (orgNatureID == -999) {
            mRootView.showMessage(mApplication.getString(R.string.org_nature_not_null_tips));
            return false;
        }
        if (orgTypeId == -999) {
            mRootView.showMessage(mApplication.getString(R.string.org_type_not_null_tips));
            return false;
        }
        return true;
    }

    /**
     * 更新用户信息
     *
     * @param params
     */
    public void setUserInfo(Map<String, String> params) {

        UserInfoBean userInfo = new UserInfoBean();
        if (!TextUtils.isEmpty(path))
            userInfo.setAvatar(path);
        userInfo.setNickName(params.get("nickName"));
        userInfo.setName(params.get("name"));
        userInfo.setSex(params.get("sex"));
        userInfo.setEmail(params.get("email"));
        CredentialsBean idTypeResponse = new CredentialsBean(); //证件
        idTypeResponse.setId(credentialsId);
        idTypeResponse.setName(credentialsName);//证件名称
        userInfo.setIdTypeResponse(idTypeResponse);
        userInfo.setBirthDay(Long.parseLong(params.get("birthDay")));
        userInfo.setPhoneNumber(params.get("phoneNumber"));
        userInfo.setIdNumber(params.get("idNumber"));//证件号码
        userInfo.setPosition(params.get("position"));
        userInfo.setWorkOrg(params.get("workOrg"));
        OrgBean orgNatureTemp = new OrgBean();
        orgNatureTemp.setName(orgNatureName);
        orgNatureTemp.setId(orgNatureID);
        OrgBean orgTypeTemp = new OrgBean();
        orgTypeTemp.setName(orgTypeName);
        orgTypeTemp.setId(orgTypeId);
        OrgBean orgDetailTemp = new OrgBean();
        orgDetailTemp.setName(orgDetailName);
        orgDetailTemp.setId(orgDetailId);
        userInfo.setNatureResponse(orgNatureTemp);
        userInfo.setCategoryResponse(orgTypeTemp);
        if (orgDetailTemp.getId() != -999)
            userInfo.setOrganizationResponse(orgDetailTemp);
        if (mProvice != null)
            userInfo.setProvinceResponse(mProvice);
        if (mCity != null)
            userInfo.setCityResponse(mCity);
        if (mCountry != null)
            userInfo.setCountyResponse(mCountry);

        WorkYear mWorkYear = new WorkYear();
        mWorkYear.setId(workYearId);
        mWorkYear.setName(workYearName);
        if (workYearId != -999)
            userInfo.setWorkYearResponse(mWorkYear);
        MyApplication.getInstance().setUserInfoBean(userInfo);
    }

}