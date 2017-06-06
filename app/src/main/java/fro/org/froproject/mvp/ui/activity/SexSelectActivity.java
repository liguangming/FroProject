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
import fro.org.froproject.app.Constants;
import fro.org.froproject.di.component.DaggerSexSelectComponent;
import fro.org.froproject.di.module.SexSelectModule;
import fro.org.froproject.mvp.contract.SexSelectContract;
import fro.org.froproject.mvp.presenter.SexSelectPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/6 0006.
 */

public class SexSelectActivity extends BaseActivity<SexSelectPresenter> implements SexSelectContract.View {
    @BindView(R.id.image_man)
    ImageView imageMan;
    @BindView(R.id.image_woman)
    ImageView imageWoman;
    private String sex;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSexSelectComponent
                .builder()
                .appComponent(appComponent)
                .sexSelectModule(new SexSelectModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_sex_select;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

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

    @OnClick({R.id.man_layout, R.id.woman_layout})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.man_layout) {
            imageMan.setVisibility(View.VISIBLE);
            imageWoman.setVisibility(View.GONE);
            sex = "男";
        } else if (id == R.id.woman_layout) {
            imageMan.setVisibility(View.GONE);
            imageWoman.setVisibility(View.VISIBLE);
            sex = "女";
        }
        Intent intent = new Intent();
        intent.putExtra(Constants.SEX, sex);
        setResult(RESULT_OK, intent);
        finish();
    }

}