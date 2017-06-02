package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
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
import fro.org.froproject.app.utils.CheckUtils;
import fro.org.froproject.di.component.DaggerForgetPasswordComponent;
import fro.org.froproject.di.module.ForgetPasswordModule;
import fro.org.froproject.mvp.contract.ForgetPasswordContract;
import fro.org.froproject.mvp.presenter.ForgetPasswordPresenter;
import fro.org.froproject.mvp.ui.view.CountView;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/1 0001.
 */

public class ForgetPasswordActivity extends BaseActivity<ForgetPasswordPresenter> implements ForgetPasswordContract.View {
    @BindView(R.id.text_button)
    TextView button;
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.password_set_edit)
    EditText passwordSet;
    @BindView(R.id.affirm_password_edit)
    EditText affirmPassword;
    @BindView(R.id.auth_code)
    EditText authCode;
    @BindView(R.id.get_code_text)
    TextView getCodeText;
    private CountView mCountView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerForgetPasswordComponent
                .builder()
                .appComponent(appComponent)
                .forgetPasswordModule(new ForgetPasswordModule(this)) //请将ForgetPasswordModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        headView.setTitleStr(R.string.forget_password);
        button.setText(R.string.complete);
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
    public void showCountView() {
        if (mCountView != null)
            mCountView = new CountView(Constants.AUTH_CODE_TIME, 1 * 1000, getCodeText);
        mCountView.start();
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


    @OnClick(R.id.complete)
    public void submit() {
        mPresenter.submit(phoneEdit.getText().toString(), authCode.getText().toString(), passwordSet.getText().toString());
    }

    @OnClick(R.id.get_code_text)
    public void getAuthCode() {
        String phone = phoneEdit.getText().toString();
        if (!CheckUtils.isMobileNO(phone)) {
            showMessage(getString(R.string.input_right_phone_number));
            return;
        }
        mPresenter.getAuthCode(phone);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountView != null)
            mCountView.cancel();
    }

    @Override
    public boolean check() {
        if (!CheckUtils.isMobileNO(phoneEdit.getText().toString())) {
            showMessage(getString(R.string.input_right_phone_number));
            return false;
        }
        if (!CheckUtils.authCodeValible(affirmPassword.getText().toString())) {
            showMessage(getString(R.string.input_right_auth_code));
            return false;
        }
        if (!TextUtils.isEmpty(passwordSet.getText().toString()) && !passwordSet.getText().toString().equals(affirmPassword.getText().toString())) {
            showMessage(getString(R.string.password_not_match));
            return false;
        }
        return true;
    }
}