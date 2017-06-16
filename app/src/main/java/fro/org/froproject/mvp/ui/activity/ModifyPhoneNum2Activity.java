package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
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
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerModifyPhoneNum2Component;
import fro.org.froproject.di.module.ModifyPhoneNum2Module;
import fro.org.froproject.mvp.contract.ModifyPhoneNum2Contract;
import fro.org.froproject.mvp.presenter.ModifyPhoneNum2Presenter;
import fro.org.froproject.mvp.ui.view.CountView;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

public class ModifyPhoneNum2Activity extends BaseActivity<ModifyPhoneNum2Presenter> implements ModifyPhoneNum2Contract.View {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.auth_code)
    EditText authCode;
    @BindView(R.id.textView2)
    TextView affirmCode;
    private CountView mCountView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerModifyPhoneNum2Component
                .builder()
                .appComponent(appComponent)
                .modifyPhoneNum2Module(new ModifyPhoneNum2Module(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_modify_number2;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        headView.setTitleStr(R.string.modify_phone_num);
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
        if (!CheckUtils.isMobileNO(phoneEdit.getText().toString())) {
            showMessage("请填写正确的手机号码");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountView != null)
            mCountView.cancel();
    }
    @OnClick(R.id.complete)
    public void commit() {
        mPresenter.commitModifyPhone2(phoneEdit.getText().toString(),authCode.getText().toString(),getIntent().getStringExtra("token"));
    }


}