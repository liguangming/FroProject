package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import org.fro.common.util.TimeUtils;
import org.fro.common.widgets.LoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerClassInfoComponent;
import fro.org.froproject.di.module.ClassInfoModule;
import fro.org.froproject.mvp.contract.ClassInfoContract;
import fro.org.froproject.mvp.model.api.Api;
import fro.org.froproject.mvp.model.entity.ClassInfoBean;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.presenter.ClassInfoPresenter;
import fro.org.froproject.mvp.ui.adapter.ClassInfoAdapter;
import fro.org.froproject.mvp.ui.view.SearchView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

public class ClassInfoActivity extends BaseActivity<ClassInfoPresenter> implements ClassInfoContract.View {
    @BindView(R.id.list)
    ListView listView;
    TextView passCourseNum;
    TextView className;
    TextView courseTotalNum;
    TextView classDescriptionText;
    ImageView titleImage;
    RelativeLayout parent;
    SearchView searchView;
    TextView textView;
    RelativeLayout titleLayout;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;
    private int classId;
    private ClassInfoAdapter adapter;
    private static final String TAG = "ClassInfoActivity";
    private RelativeLayout hearderViewLayout;
    int page = 0;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerClassInfoComponent.builder()
                .appComponent(appComponent)
                .classInfoModule(new ClassInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_class_info;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        adapter = new ClassInfoAdapter(this);
        hearderViewLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.class_info_headview, null);
        passCourseNum = (TextView) hearderViewLayout.findViewById(R.id.pass_course_num);
        className = (TextView) hearderViewLayout.findViewById(R.id.class_name);
        courseTotalNum = (TextView) hearderViewLayout.findViewById(R.id.course_total_num);
        classDescriptionText = (TextView) hearderViewLayout.findViewById(R.id.class_description_text);
        titleImage = (ImageView) hearderViewLayout.findViewById(R.id.title_bg);
        (hearderViewLayout.findViewById(R.id.back_image)).setOnClickListener(v -> killMyself());
        parent = (RelativeLayout) hearderViewLayout.findViewById(R.id.parent_layout);
        titleLayout = (RelativeLayout) hearderViewLayout.findViewById(R.id.title_layout);
        searchView = (SearchView) hearderViewLayout.findViewById(R.id.search_view);
        textView = (TextView) hearderViewLayout.findViewById(R.id.class_start_time);
        listView.addHeaderView(hearderViewLayout);
        listView.setAdapter(adapter);
        View view = new View(this);
        view.setMinimumHeight(10);
        listView.addFooterView(view, null, false);
        classId = getIntent().getIntExtra(Constants.CLASS_ID, -99);
        initFreshView();
        mPresenter.getClassInfo(page, classId);
        searchView.setSerach(onEditorActionListener);
    }

    @OnItemClick(R.id.list)
    public void itemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, adapter.getItem(position - 1));
        intent.setClass(this, CourseStudyActivity.class);
        startActivity(intent);

    }

    @Override
    public void showLoading() {
        LoadingView.showLoading(getApplication());
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

    public void initFreshView() {
        Utils.initFreshView(refreshView);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                mPresenter.getClassInfo(page, classId);
            }
        });
    }

    @Override
    public void stopLoadMore() {
        page++;
        refreshView.stopLoadMore();
    }

    @Override
    public void endLoadMore() {
        refreshView.setLoadComplete(true);
    }

    @Override
    public void updateUi(ClassInfoBean classInfo) {
        textView.setText("开班时间：" + TimeUtils.parseTimeToYM(classInfo.getStartDate()) + "-" + TimeUtils.parseTimeToYM(classInfo.getEndDate()));
        passCourseNum.setText("已通过课程:" + classInfo.getPassedCourseNum() + "课");
        className.setText(classInfo.getClassName());
        courseTotalNum.setText("共" + classInfo.getTotalCourseNum() + "节课");
        classDescriptionText.setText(classInfo.getSampleDescription());
        Glide.with(this).load(Api.APP_URL + classInfo.getClassPic()).into(titleImage);
    }

    @Override
    public void setList(List<CourseBean> dataList) {
        adapter.setList(dataList);
    }

    @Override
    public void addList(List<CourseBean> dataList) {
        adapter.addList(dataList);
    }

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent();
                intent.setClass(ClassInfoActivity.this, SearchResultActivity.class);
                intent.putExtra(Constants.SEARCH_CONTENT, searchView.getEditTextStr());
                intent.putExtra(Constants.CLASS_ID, String.valueOf(classId));
                startActivity(intent);
            }
            return false;
        }
    };
}