package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.LoadingView;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.StatusBarUtil;
import fro.org.froproject.di.component.DaggerCourseStudyComponent;
import fro.org.froproject.di.module.CourseStudyModule;
import fro.org.froproject.mvp.contract.CourseStudyContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.presenter.CourseStudyPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

public class CourseStudyActivity extends BaseActivity<CourseStudyPresenter> implements CourseStudyContract.View {
    @BindView(R.id.study_button)
    Button studyButton;
    @BindView(R.id.title_course_name)
    TextView titleCourse_Name;
    @BindView(R.id.course_name)
    TextView course_Name;
    @BindView(R.id.status_text)
    TextView statusText;
    @BindView(R.id.order)
    TextView order;
    @BindView(R.id.course_order)
    TextView course_order;
    @BindView(R.id.classname)
    TextView className;
    @BindView(R.id.image_title)
    ImageView imageTitle;
    private CourseBean courseBean;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCourseStudyComponent.builder()
                .appComponent(appComponent)
                .courseStudyModule(new CourseStudyModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.course_study;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.title_bg), 0);
        courseBean = (CourseBean) getIntent().getSerializableExtra(Constants.COURSE);
        String str = getString(R.string.order_course);
        String courseName = String.format(str, courseBean.getOrder(), courseBean.getCourseName());
        titleCourse_Name.setText(courseName);
        course_Name.setText(courseName);
        if (courseBean.getLearnStatus() == 1) {//已通过
            studyButton.setText("");
            studyButton.setBackgroundResource(R.mipmap.ic_correct_bg);
            statusText.setText(R.string.passed);
            statusText.setTextColor(Color.parseColor("#aae230"));
        } else if (courseBean.getLearnStatus() == 0) {//未通过
            statusText.setText(R.string.not_passed_tips);
            studyButton.setText(R.string.study);
            statusText.setTextColor(Color.parseColor("#ec6a43"));
        } else {
            studyButton.setText(R.string.study);
            statusText.setVisibility(View.GONE);
        }
        className.setText(courseBean.getClassName());
        String courseOrder = String.format(str, courseBean.getOrder(), "");
        course_order.setText(courseOrder);
        order.setText(courseBean.getOrder() + "/" + courseBean.getTotalCourseNum());
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

    @OnClick(R.id.study_button)
    public void gotoCourseInfoActivity() {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, courseBean);
        intent.setClass(this, CourseInfoActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.test_text, R.id.test_image})
    public void gotoExerciseActivity() {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, courseBean);
        intent.setClass(this, ExerciseActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.back_image)
    public void click() {
        finish();
    }
}