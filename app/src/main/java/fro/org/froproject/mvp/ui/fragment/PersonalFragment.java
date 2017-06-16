package fro.org.froproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import org.fro.common.widgets.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerPersonalComponent;
import fro.org.froproject.di.module.PersonalModule;
import fro.org.froproject.mvp.contract.PersonalContract;
import fro.org.froproject.mvp.model.api.Api;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.presenter.PersonalPresenter;
import fro.org.froproject.mvp.ui.activity.ModifyPasswordActivity;
import fro.org.froproject.mvp.ui.activity.ModifyPhoneNum1Activity;
import fro.org.froproject.mvp.ui.activity.ModifyUserInfoActivity;
import fro.org.froproject.mvp.ui.view.GlideRoundTransform;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class PersonalFragment extends BaseFragment<PersonalPresenter> implements PersonalContract.View {
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.userName)
    TextView uerName;
    @BindView(R.id.bank_name)
    TextView bank_name;
    @BindView(R.id.phone)
    TextView phone;

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerPersonalComponent
                .builder()
                .appComponent(appComponent)
                .personalModule(new PersonalModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        UserInfoBean userInfo = MyApplication.getInstance().getUserInfoBean();
        if (userInfo.getOrganizationResponse() != null &&
                !TextUtils.isEmpty(userInfo.getOrganizationResponse().getName())) {
            bank_name.setText(userInfo.getOrganizationResponse().getName());
        } else {
            bank_name.setText(userInfo.getCategoryResponse().getName());
        }
        phone.setText(userInfo.getPhoneNumber());
        uerName.setText(userInfo.getNickName());
        if (!TextUtils.isEmpty(userInfo.getAvatar()))
//            ImageLoader.getInstance().displayImage(Api.APP_URL + userInfo.getAvatar(), userImage, Utils.getImageLoadCofig(360));
            Glide.with(this).load(Api.APP_URL + userInfo.getAvatar()).transform(new GlideRoundTransform(getActivity(), 360)).into(userImage);
    }


    @OnClick({R.id.title_layout, R.id.modify_password_layout, R.id.modify_phone_layout})
    void gotoActivity(View v) {
        int id = v.getId();
        Intent intent = null;
        if (id == R.id.title_layout) {
            intent = new Intent(getContext(), ModifyUserInfoActivity.class);
        } else if (id == R.id.modify_password_layout) {
            intent = new Intent(getContext(), ModifyPasswordActivity.class);
        } else if (id == R.id.modify_phone_layout) {
            intent = new Intent(getContext(), ModifyPhoneNum1Activity.class);
        }
        startActivity(intent);
    }

    /**
     * @param data
     */

    @Override
    public void setData(Object data) {

    }


    @Override
    public void showLoading() {
        LoadingView.showLoading(getActivity());
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
    }

}