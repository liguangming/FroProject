package fro.org.froproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import fro.org.froproject.R;
import fro.org.froproject.di.component.DaggerCourseComponent;
import fro.org.froproject.di.module.CourseModule;
import fro.org.froproject.mvp.contract.CourseContract;
import fro.org.froproject.mvp.presenter.CoursePresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class CourseFragment extends BaseFragment<CoursePresenter> implements CourseContract.View {


    public static CourseFragment newInstance() {
        CourseFragment fragment = new CourseFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCourseComponent
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))//请将CourseModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cd_bottom_popup_dialog, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    /**
     * @param data
     */

    @Override
    public void setData(Object data) {

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

    }

}