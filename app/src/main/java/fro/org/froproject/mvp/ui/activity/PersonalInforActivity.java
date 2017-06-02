package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.di.component.DaggerPersonalInforComponent;
import fro.org.froproject.di.module.PersonalInforModule;
import fro.org.froproject.mvp.contract.PersonalInforContract;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.presenter.PersonalInforPresenter;
import fro.org.froproject.mvp.ui.view.HeadView;
import fro.org.froproject.mvp.ui.view.PersonalItemView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

public class PersonalInforActivity extends BaseActivity<PersonalInforPresenter> implements PersonalInforContract.View {
    @BindView(R.id.nick_name)
    PersonalItemView nickName;
    @BindView(R.id.really_name)
    PersonalItemView reallyName;
    @BindView(R.id.sex)
    PersonalItemView sex;
    @BindView(R.id.age)
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
    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerPersonalInforComponent
                .builder()
                .appComponent(appComponent)
                .personalInforModule(new PersonalInforModule(this)) //请将PersonalInforModule()第一个首字母改为小写
                .build()
                .inject(this);
    }
    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_personal_infor;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }
    @OnClick({R.id.org_nature, R.id.org_type, R.id.org_detail,R.id.credentials,R.id.work_life})
    public void gotoActivity(View view){
        mPresenter.gotoActivity(view.getId());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.launchActivityResult(requestCode,resultCode,data);
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
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    /**
     *修改userInfo
     */

    @Override
    public void setUserInfo() {
        UserInfoBean userInfoBean=new UserInfoBean();
    }

    @Override
    public void launchActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent,requestCode);
    }
}