package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.utils.CheckUtils;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerModifyPasswordComponent;
import fro.org.froproject.di.module.ModifyPasswordModule;
import fro.org.froproject.mvp.contract.ModifyPasswordContract;
import fro.org.froproject.mvp.presenter.ModifyPasswordPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

public class ModifyPasswordActivity extends BaseActivity<ModifyPasswordPresenter> implements ModifyPasswordContract.View {
    @BindView(R.id.password_set_edit)
    EditText passwordNow;
    @BindView(R.id.password_new_set)
    EditText newPassword;
    @BindView(R.id.password_confirm)
    EditText passwordConfirm;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerModifyPasswordComponent
                .builder()
                .appComponent(appComponent)
                .modifyPasswordModule(new ModifyPasswordModule(this)) //请将ModifyPasswordModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_modiy_password;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.commit_button)
    public void commit() {
        if (!CheckUtils.passwordRight(passwordNow.getText().toString())) {
            showMessage("密码为6-16位数字或字母");
            return;
        }
        if (!TextUtils.isEmpty(newPassword.getText().toString()) && !newPassword.getText().toString().equals(passwordConfirm.getText().toString())) {
            showMessage("密码与确认密码不相符");
            return;
        }
        Utils.hideKeyboard(this, newPassword);
        String oldPasswordStr = Utils.encodePassword(passwordNow.getText().toString());
        String newPasswordStr = Utils.encodePassword(newPassword.getText().toString());
        mPresenter.modifyPassword(oldPasswordStr, newPasswordStr);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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


}