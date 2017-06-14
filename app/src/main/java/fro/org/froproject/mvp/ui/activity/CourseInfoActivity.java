package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.StatusBarUtil;
import fro.org.froproject.di.component.DaggerCourseInfoComponent;
import fro.org.froproject.di.module.CourseInfoModule;
import fro.org.froproject.mvp.contract.CourseInfoContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.presenter.CourseInfoPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

public class CourseInfoActivity extends BaseActivity<CourseInfoPresenter> implements CourseInfoContract.View {
    @BindView(R.id.webView)
    WebView webView;
    private CourseBean courseBean;
    private int classId;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCourseInfoComponent
                .builder()
                .appComponent(appComponent)
                .courseInfoModule(new CourseInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {

        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.blue_bg), 0);
        return R.layout.course_info;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        courseBean = (CourseBean) getIntent().getSerializableExtra(Constants.COURSE);
        classId = courseBean.getClassId();
        WebSettings settings = webView.getSettings();
        settings.setSupportZoom(true);          //支持缩放
        settings.setBuiltInZoomControls(true);  //启用内置缩放装置
        settings.setJavaScriptEnabled(true);//启用JS脚本
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);// 缩放至屏幕的大小
        settings.setUseWideViewPort(true);//将图片调整到适合webview的大小
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        mPresenter.getCourseContent(classId, courseBean.getId());
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
    public void loadDataWithBaseURL(String text) {
        webView.loadDataWithBaseURL(null, text, "text/html", "UTF-8", null);
    }
    @OnClick(R.id.image_back)
    public void close() {
        killMyself();
    }
    @OnClick(R.id.go_answer)
    public void gotoAnswerQuestionActivity() {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, courseBean);
        intent.setClass(this, ExerciseActivity.class);
        startActivity(intent);
    }
}