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
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.CheckUtils;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerRegisterComponent;
import fro.org.froproject.di.module.RegisterModule;
import fro.org.froproject.mvp.contract.RegisterContract;
import fro.org.froproject.mvp.presenter.RegisterPresenter;
import fro.org.froproject.mvp.ui.view.CountView;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/1 0001.
 */

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.button_text)
    TextView text;
    @BindView(R.id.get_code_text)
    TextView getCodeText;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.auth_code)
    EditText authCode;
    @BindView(R.id.password_set_edit)
    EditText passwordSet;
    @BindView(R.id.affirm_password_edit)
    EditText affirmPasswordSet;
    private CountView mCountView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerRegisterComponent
                .builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this)) //请将RegisterModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_register;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        headView.setTitleStr(R.string.register);
        text.setText(R.string.next_step);
    }

    @OnClick(R.id.complete)
    public void submit() {
        if (!check())
            return;
        Utils.hideKeyboard(this, phoneEdit);
        mPresenter.submit(phoneEdit.getText().toString(), authCode.getText().toString(), passwordSet.getText().toString());
    }

    @OnClick(R.id.get_code_text)
    public void getAuthCode() {
        String phone = phoneEdit.getText().toString();
        if (!CheckUtils.isMobileNO(phone)) {
            showMessage(getString(R.string.input_right_phone_number));
            return;
        }
        Utils.hideKeyboard(this, phoneEdit);
        mPresenter.getAuthCode(phone);
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


    @Override
    public void showCountView() {
        if (mCountView == null)
            mCountView = new CountView(Constants.AUTH_CODE_TIME, 1 * 1000, getCodeText);
        mCountView.start();
    }

    public boolean check() {
        if (!CheckUtils.isMobileNO(phoneEdit.getText().toString())) {
            showMessage(getString(R.string.input_right_phone_number));
            return false;
        }
        if (!CheckUtils.authCodeValible(authCode.getText().toString())) {
            showMessage(getString(R.string.input_right_auth_code));
            return false;
        }
        if (TextUtils.isEmpty(passwordSet.getText().toString()) || TextUtils.isEmpty(affirmPasswordSet.getText().toString())) {
            showMessage(getString(R.string.password_not_null));
            return false;
        }
        if (!TextUtils.isEmpty(passwordSet.getText().toString()) && !passwordSet.getText().toString().equals(affirmPasswordSet.getText().toString())) {
            showMessage(getString(R.string.password_not_match));
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountView != null)
            mCountView.cancel();
    }
}