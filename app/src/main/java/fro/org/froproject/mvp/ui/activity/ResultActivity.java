package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.StatusBarUtil;
import fro.org.froproject.di.component.DaggerResultComponent;
import fro.org.froproject.di.module.ResultModule;
import fro.org.froproject.mvp.contract.ResultContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.model.entity.ExerciseResponseBean;
import fro.org.froproject.mvp.presenter.ResultPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

public class ResultActivity extends BaseActivity<ResultPresenter> implements ResultContract.View {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.result_text)
    TextView text;
    @BindView(R.id.correct_rate)
    TextView textRate;
    private CourseBean course;
    ExerciseResponseBean mExerciseResponseBean;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerResultComponent
                .builder()
                .appComponent(appComponent)
                .resultModule(new ResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_result;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.StatusBarLightMode(this);
//        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
        StatusBarUtil.setTransparent(this);
        course = (CourseBean) getIntent().getSerializableExtra(Constants.COURSE);
        mExerciseResponseBean = (ExerciseResponseBean) getIntent().getSerializableExtra(Constants.RESULT);
        if (!mExerciseResponseBean.isPassed()) {
            image.setImageResource(R.mipmap.not_pass_red);
            text.setText(R.string.not_passed_tips);
            text.setTextColor(Color.parseColor("#fc6c3f"));
            textRate.setTextColor(Color.parseColor("#fc6c3f"));

        } else {
            text.setText(R.string.passed_tips);
            text.setTextColor(Color.parseColor("#0acd38"));
            textRate.setTextColor(Color.parseColor("#0acd38"));
            image.setImageResource(R.mipmap.pass_green);
        }
        textRate.setText("正确率：" + mExerciseResponseBean.getPassedExerciseNum() + "/" + mExerciseResponseBean.getTotalExerciseNum());
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

    @OnClick(R.id.retry_linear_layout)
    public void retryOnClick() {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, course);
        intent.setClass(this, ExerciseActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.complete)
    public void completeClick() {
        this.finish();
        //TODO 回到主界面
    }
}