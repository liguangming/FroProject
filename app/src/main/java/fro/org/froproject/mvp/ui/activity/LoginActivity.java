package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import org.fro.common.widgets.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.utils.CheckUtils;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerLoginComponent;
import fro.org.froproject.di.module.LoginModule;
import fro.org.froproject.mvp.contract.LoginContract;
import fro.org.froproject.mvp.presenter.LoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/5/31 0031.
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.password_edit)
    EditText password_edit;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this)) //请将LoginModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.login)
    public void login() {
        if (!check())
            return;
        Utils.hideKeyboard(this,password_edit);

        mPresenter.login(phone_edit.getText().toString(), password_edit.getText().toString());
    }

    @OnClick(R.id.forget_passwrod)
    public void gotoForgetPassWordActivity() {
        mPresenter.gotoForgetPassWordActivity();
    }

    @OnClick(R.id.register)
    public void gotoRegisterPassWord() {
        mPresenter.gotoRegisterPassWord();
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
     * 输入检查
     */
    public boolean check() {
        if (!CheckUtils.isMobileNO(phone_edit.getText().toString())) {
            showMessage(getString(R.string.phone_num_tips));
            return false;
        }
        if (!CheckUtils.passwordRight(password_edit.getText().toString())) {
            showMessage(getString(R.string.input_right_password));
            return false;
        }
        return true;
    }
}