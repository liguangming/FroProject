package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.util.TimeUtils;
import org.fro.common.widgets.LoadingView;
import org.fro.common.widgets.photoclop.UCrop;
import org.fro.common.widgets.pickerview.TimePickerView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.di.component.DaggerModifyUserInfoComponent;
import fro.org.froproject.di.module.ModifyUserInfoModule;
import fro.org.froproject.mvp.contract.ModifyUserInfoContract;
import fro.org.froproject.mvp.model.api.Api;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.presenter.ModifyUserInfoPresenter;
import fro.org.froproject.mvp.ui.view.HeadView;
import fro.org.froproject.mvp.ui.view.PersonalItemView;
import fro.org.froproject.mvp.ui.view.PopupListView;
import fro.org.froproject.mvp.ui.view.photoclip.PhotoConfig;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

public class ModifyUserInfoActivity extends BaseActivity<ModifyUserInfoPresenter> implements ModifyUserInfoContract.View {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.nick_name)
    PersonalItemView nickName;
    @BindView(R.id.really_name)
    PersonalItemView reallyName;
    @BindView(R.id.sex)
    PersonalItemView sex;
    @BindView(R.id.birthday)
    PersonalItemView birthDay;
    @BindView(R.id.email)
    PersonalItemView email;
    @BindView(R.id.credentials)
    PersonalItemView credentialsType;
    @BindView(R.id.credentials_number)
    PersonalItemView credentialsNum;
    //机构性质
    @BindView(R.id.org_nature)
    PersonalItemView orgNature;
    //机构类别
    @BindView(R.id.org_type)
    PersonalItemView orgType;
    //具体机构
    @BindView(R.id.org_detail)
    PersonalItemView orgDetail;
    //机构分支
    @BindView(R.id.work_branch)
    PersonalItemView workBranch;
    @BindView(R.id.job_position)
    PersonalItemView jobPosition;
    @BindView(R.id.work_life)
    PersonalItemView workLife;
    @BindView(R.id.image_title)
    ImageView imageView;
    @BindView(R.id.location)
    PersonalItemView location;
    private Uri mDestinationUri;
    private UserInfoBean userInfo;
    private int orgNatureID;
    private int orgTypeId;
    private int orgDetailId;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerModifyUserInfoComponent
                .builder()
                .appComponent(appComponent)
                .modifyUserInfoModule(new ModifyUserInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_modify_user_info;
    }

    @OnClick(R.id.location)
    public void showLocationDialog() {
        mPresenter.showLocationDialog();
    }


    @OnClick({R.id.org_nature, R.id.org_type, R.id.org_detail, R.id.work_life})
    public void gotoActivity(View view) {
        mPresenter.gotoActivity(view.getId());
    }

    @OnClick(R.id.image_title)
    public void showPopWindow() {
        mDestinationUri = Uri.fromFile(PhotoConfig.getCroppedFile(this));
        PopupListView popupListView = new PopupListView(this);
        popupListView.show(new String[]{getString(R.string.get_from_box)}, new PopupListView.ICallBack() {
            @Override
            public void performClick(int position) {
                switch (position) {
                    case 0:
                        mPresenter.pickFromGallery(mDestinationUri);  //相册中获取
                        break;
                    case 1:
//                        getString(R.string.get_from_take_picture)
//                        takePhoto();
                        break;
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.launchActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void launchActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        headView.setTitleStr(R.string.personal_info);
        headView.setRightText(R.string.complete);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    public void setData() {
        userInfo = MyApplication.getInstance().getUserInfoBean();
//        ImageLoader.getInstance().displayImage(Api.APP_URL + userInfo.getAvatar(), imageView, myDisplayImageOptions);
        nickName.setEditText(userInfo.getNickName());
        reallyName.setTextViewText(userInfo.getName());
        sex.setTextViewText(userInfo.getSex());

        if (userInfo.getBirthDay() != 0) {
            birthDay.setTextViewText(TimeUtils.parseTimeToYM2(userInfo.getBirthDay()));
            birthDay.setTextViewColor(R.color.cd_text_color6);
        }
        email.setEditText(userInfo.getEmail());
        credentialsType.setTextViewText(userInfo.getIdTypeResponse().getName());
        credentialsNum.setTextViewText(userInfo.getIdNumber());
        workBranch.setEditText(userInfo.getWorkOrg());
        jobPosition.setEditText(userInfo.getPosition());
        if (userInfo.getProvinceResponse() != null && userInfo.getCityResponse() != null && userInfo.getCountyResponse() != null && !TextUtils.isEmpty(userInfo.getProvinceResponse().getName())) {
            location.setTextViewText(userInfo.getProvinceResponse().getName() + userInfo.getCityResponse().getName() + userInfo.getCountyResponse().getName());
            location.setTextViewColor(R.color.cd_text_color6);
        }
        orgNatureID = userInfo.getNatureResponse().getId();
        if (orgNatureID != -999) {
            orgNature.setTextViewText(userInfo.getNatureResponse().getName());
            orgNature.setTextViewColor(R.color.cd_text_color6);
        }
        orgTypeId = userInfo.getCategoryResponse().getId();
        if (orgTypeId != -999) {
            orgType.setTextViewText(userInfo.getCategoryResponse().getName());
            orgType.setTextViewColor(R.color.cd_text_color6);
        }
        if(userInfo.getOrganizationResponse()!=null) {
            orgDetailId = userInfo.getOrganizationResponse().getId();
            if (orgDetailId != -999) {
                orgDetail.setClickable(true);
                orgDetail.setTextViewText(userInfo.getOrganizationResponse().getName());
            } else {
                orgDetail.setClickable(false);
                orgDetail.setTextViewText(getString(R.string.org_detail_tips));
            }
        }
        orgDetail.setTextViewColor(R.color.cd_text_color6);
        if (userInfo.getWorkYearResponse() != null && userInfo.getWorkYearResponse().getId() != -999) {
            workLife.setTextViewText(userInfo.getWorkYearResponse().getName());
            workLife.setTextViewColor(R.color.cd_text_color6);
        }
    }

    @Override
    public void showLoading() {
        LoadingView.showLoading(this);
    }

    @Override
    public void hideLoading() {
        LoadingView.dismissLoading();
    }

    @Override
    public void setLocation(String s) {
        location.setTextViewText(s);
        location.setTextViewColor(R.color.cd_text_color6);
    }

    @Override
    public void setName(int result, String name) {
        if (result == Constants.RESULT_1001) {
            orgNature.setTextViewText(name);
            orgNature.setTextViewColor(R.color.cd_text_color6);
        } else if (result == Constants.RESULT_1002) {
            orgType.setTextViewText(name);
            orgType.setTextViewColor(R.color.cd_text_color6);
        } else if (result == Constants.RESULT_1003) {
            orgDetail.setTextViewText(name);
            orgDetail.setTextViewColor(R.color.cd_text_color6);
            if (name.equals(getString(R.string.org_detail_tips))) {
                orgDetail.setEnabled(false);
            } else {
                orgDetail.setEnabled(true);
            }
        } else if (result == Constants.RESULT_1005) {
            workLife.setTextViewText(name);
            workLife.setTextViewColor(R.color.cd_text_color6);
        }
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @OnClick(R.id.rightText)
    public void complete() {
        if (!checkNUll())
            return;
        mPresenter.commit(getData());
    }

    @Override
    public void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(UiUtils.getScreenWidth(this), getResources().getDimensionPixelOffset(R.dimen.sa_banner_height))
                .withTargetActivity(PhotoCropActivity.class)
                .start(this);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public void setAtvorImage(Bitmap roundBitmap) {
        imageView.setImageBitmap(roundBitmap);
    }


    public Map<String, String> getData() {
        Map<String, String> params = new HashMap<>();
        params.put("nickName", nickName.getEditText());
        params.put("name", reallyName.getTextViewText());
        params.put("sex", sex.getTextViewText());
        if (!birthDay.getTextViewText().equals(getString(R.string.not_setting)))
            params.put("birthDay", TimeUtils.getLongTime(birthDay.getTextViewText()));
        if (!TextUtils.isEmpty(email.getEditText()))
            params.put("email", email.getEditText());
        params.put("idNumber", credentialsNum.getTextViewText());
        params.put("workOrg", workBranch.getEditText());//机构分支
        if (!TextUtils.isEmpty(jobPosition.getEditText()))
            params.put("position", jobPosition.getEditText());
        params.put("phoneNumber", MyApplication.getInstance().getUserInfoBean().getPhoneNumber());
        return params;
    }

    @OnClick(R.id.birthday)
    public void showDateDialog() {
        String textViewText = birthDay.getTextViewText();
        Calendar selectDate = Calendar.getInstance();
        if (!TextUtils.isEmpty(textViewText) && !textViewText.equals(getString(R.string.not_setting))) {
            String[] birth = textViewText.split("-");
            selectDate.set(Integer.parseInt(birth[0]), Integer.parseInt(birth[1]) - 1, Integer.parseInt(birth[2]));
        } else {
            selectDate = null;
        }
        TimePickerView pvTime = new TimePickerView.Builder(this, (date, v) -> {//选中事件回调
            birthDay.setTextViewText(TimeUtils.parseTimeToYM2(date.getTime()));
            birthDay.setTextViewColor(R.color.cd_text_color6);
        })
                .setType(new boolean[]{true, true, true, false, false, false})//默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(22)//标题文字大小
                .setTitleText("选择生日")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setLabel("", "", "", "", "", "")
                .setDate(selectDate)// 如果不设置的话，默认是系统时间*/
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    /**
     * 输入监测
     *
     * @return
     */
    public boolean checkNUll() {
        if (TextUtils.isEmpty(nickName.getEditText())) {
            showMessage(getString(R.string.nick_name_not_null_tips));
            return false;
        }
        if (TextUtils.isEmpty(birthDay.getTextViewText()) || birthDay.getTextViewText().equals(getString(R.string.not_setting))) {
            showMessage(getString(R.string.birthday_not_null_tips));
            return false;
        }

        if (TextUtils.isEmpty(workBranch.getEditText())) {
            showMessage(getString(R.string.org_branch_not_null_tips));
            return false;
        }
        return true;
    }
}