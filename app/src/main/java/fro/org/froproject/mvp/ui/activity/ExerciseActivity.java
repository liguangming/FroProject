package fro.org.froproject.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import org.fro.common.widgets.LoadingView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnItemClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.di.component.DaggerExerciseComponent;
import fro.org.froproject.di.module.ExerciseModule;
import fro.org.froproject.mvp.contract.ExerciseContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.model.entity.ExerciseBean;
import fro.org.froproject.mvp.model.entity.ResultAnswer;
import fro.org.froproject.mvp.model.entity.ResultCommitBean;
import fro.org.froproject.mvp.presenter.ExercisePresenter;
import fro.org.froproject.mvp.ui.adapter.AnswerQuestionAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/14 0014.
 */

public class ExerciseActivity extends BaseActivity<ExercisePresenter> implements ExerciseContract.View {
    @BindView(android.R.id.list)
    ListView listView;
    @BindView(R.id.exercise_up)
    TextView exerciseUp;
    @BindView(R.id.exercise_next)
    TextView exerciseNext;
    TextView question;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_number)
    TextView titleNumber;
    @BindView(R.id.middle_line)
    View middleLine;
    private View listHead;
    private CourseBean course;
    private final static String TAG = "ExerciseActivity";
    int exerciseOrder;//题目
    private List<ExerciseBean> exerciseBean = new ArrayList<>();
    private Map<String, Object> resultMap = new HashMap();//用来存储题目和答案


    private AnswerQuestionAdapter adapter;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerExerciseComponent
                .builder()
                .appComponent(appComponent)
                .exerciseModule(new ExerciseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_exercise;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        course = (CourseBean) getIntent().getSerializableExtra(Constants.COURSE);
        adapter = new AnswerQuestionAdapter(this);
        LayoutInflater Inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listHead = Inflater.inflate(R.layout.excerise_headview, null);
        question = (TextView) listHead.findViewById(R.id.question);
        listView.addHeaderView(listHead);
        listView.setAdapter(adapter);
        mPresenter.getQuestionList(course.getClassId(), course.getId());
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

    @OnItemClick(android.R.id.list)
    void itemClick(int position) {
        if (position == 0)
            return;
        position = position - 1;
        clearSelected();
        exerciseBean.get(exerciseOrder).getCourseExerCises().get(position).setSelected(true);
        adapter.setList(exerciseBean.get(exerciseOrder).getCourseExerCises());
        ResultCommitBean resultCommitBean = new ResultCommitBean();
        resultCommitBean.setId(exerciseBean.get(exerciseOrder).getId());//设置题的ID
        resultCommitBean.getCourseExerCiseAnsweres().add(new ResultAnswer(adapter.getItem(position).getId()));//设置答案的ID
        resultMap.put(String.valueOf(exerciseBean.get(exerciseOrder).getId()), resultCommitBean);
        adapter.notifyDataSetChanged();
    }

    /**
     * 清除选中状态
     */
    public void clearSelected() {
        for (int i = 0; i < adapter.getCount(); i++) {
            adapter.getItem(i).setSelected(false);
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 上一题
     */
    @OnClick(R.id.exercise_up)
    public void exerciseUp() {
        exerciseOrder--;
        adapter.setList(exerciseBean.get(exerciseOrder).getCourseExerCises());
        upDateUi();
    }

    /**
     * 下一题
     */
    @OnClick(R.id.exercise_next)
    public void exerciseNext() {
        if (resultMap.size() > exerciseOrder) {
            if (exerciseNext.getText().equals(getString(R.string.next_question))) {
                exerciseOrder++;
                adapter.setList(exerciseBean.get(exerciseOrder).getCourseExerCises());
                title.setText(exerciseOrder + 1 + "/" + exerciseBean.size());
                upDateUi();
            } else {
                //提交答案
                mPresenter.commit(course, resultMap);
            }
        } else {
            showMessage("还有题没答，请完成后进入下一题");
        }
    }

    /**
     * 点击的时候改变界面状态
     */

    public void upDateUi() {
        if (exerciseOrder == 0) {//第一题
            if (exerciseBean.size() == 1) {
                exerciseUp.setVisibility(View.GONE);
                exerciseNext.setText("交卷");
            } else {
                exerciseUp.setVisibility(View.GONE);
                middleLine.setVisibility(View.GONE);
                exerciseNext.setText(R.string.next_question);
            }
            middleLine.setVisibility(View.GONE);
        } else if (exerciseOrder == exerciseBean.size() - 1) {//最后一题
            exerciseUp.setVisibility(View.VISIBLE);
            middleLine.setVisibility(View.VISIBLE);
            exerciseNext.setText("交卷");
        } else {//中间
            exerciseUp.setVisibility(View.VISIBLE);
            middleLine.setVisibility(View.VISIBLE);
            exerciseNext.setText(R.string.next_question);
        }
        question.setText(exerciseBean.get(exerciseOrder).getText());
        title.setText(exerciseOrder + 1 + "/" + exerciseBean.size());
        titleNumber.setText(String.valueOf(exerciseOrder + 1));
    }

    @Override
    public void setList(List<ExerciseBean> exerciseBean) {
        this.exerciseBean = exerciseBean;
        adapter.setList(exerciseBean.get(exerciseOrder).getCourseExerCises());
        upDateUi();
    }

    /**
     * 关闭
     */
    @OnClick(R.id.leftImage)
    public void exit() {
        finish();
    }

}