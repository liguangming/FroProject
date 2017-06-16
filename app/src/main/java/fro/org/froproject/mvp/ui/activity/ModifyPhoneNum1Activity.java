package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.CheckUtils;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerModifyPhoneNum1Component;
import fro.org.froproject.di.module.ModifyPhoneNum1Module;
import fro.org.froproject.mvp.contract.ModifyPhoneNum1Contract;
import fro.org.froproject.mvp.presenter.ModifyPhoneNum1Presenter;
import fro.org.froproject.mvp.ui.view.CountView;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

public class ModifyPhoneNum1Activity extends BaseActivity<ModifyPhoneNum1Presenter> implements ModifyPhoneNum1Contract.View {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.phone_edit)
    TextView phoneEdit;
    @BindView(R.id.auth_code)
    EditText authCode;
    @BindView(R.id.textView2)
    TextView affirmCode;
    private CountView mCountView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerModifyPhoneNum1Component.builder()
                .appComponent(appComponent)
                .modifyPhoneNum1Module(new ModifyPhoneNum1Module(this)) //请将ModifyPhoneNum1Module()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_modify_number;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        headView.setTitleStr(R.string.modify_phone_num);
        phoneEdit.setText(MyApplication.getInstance().getUserInfoBean().getPhoneNumber());
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
     * 获取验证码
     */
    @OnClick(R.id.textView2)
    public void getCode() {
        String phone = phoneEdit.getText().toString();
        if (!CheckUtils.isMobileNO(phone)) {
            showMessage(getString(R.string.input_right_phone_number));
            return;
        }
        Utils.hideKeyboard(this, phoneEdit);
        mPresenter.getAuthCode(phoneEdit.getText().toString());
    }

    @Override
    public void showCountView() {
        if (mCountView == null)
            mCountView = new CountView(Constants.AUTH_CODE_TIME, 1 * 1000, affirmCode);
        mCountView.start();
    }

    @OnClick(R.id.complete)
    public void complete() {
        if (!CheckUtils.authCodeValible(authCode.getText().toString())) {
            showMessage("请输入正确的验证码");
            return;
        }
        mPresenter.commitModifyPhone1(authCode.getText().toString(), phoneEdit.getText().toString());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mCountView != null)
            mCountView.cancel();
    }
}