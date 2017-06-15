package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.util.TimeUtils;
import org.fro.common.widgets.LoadingView;
import org.fro.common.widgets.tab.roundtextview.RoundRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.StatusBarUtil;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerHistoryClassInfoComponent;
import fro.org.froproject.di.module.HistoryClassInfoModule;
import fro.org.froproject.mvp.contract.HistoryClassInfoContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.model.entity.HistoryClassBean;
import fro.org.froproject.mvp.presenter.HistoryClassInfoPresenter;
import fro.org.froproject.mvp.ui.adapter.SearchResultAdapter;
import fro.org.froproject.mvp.ui.view.SearchView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

public class HistoryClassInfoActivity extends BaseActivity<HistoryClassInfoPresenter> implements HistoryClassInfoContract.View {
    @BindView(R.id.status_text)
    TextView classStatus;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.round_layout)
    RoundRelativeLayout roundLayout;
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(android.R.id.list)
    ListView listView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;

    @BindView(R.id.custom_view)
    XRefreshView refreshView;
    private int page = 0;

    private SearchResultAdapter adapter;
    private HistoryClassBean historyClass;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerHistoryClassInfoComponent
                .builder()
                .appComponent(appComponent)
                .historyClassInfoModule(new HistoryClassInfoModule(this))
                .build()
                .inject(this);
    }
    @OnItemClick(android.R.id.list)
    public void itemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, adapter.getItem(position));
        intent.setClass(this, CourseStudyActivity.class);
        launchActivity(intent);
    }
    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_history_class_info;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.title_bg), 0);
        adapter = new SearchResultAdapter(this);
        historyClass = (HistoryClassBean) getIntent().getSerializableExtra(Constants.CLASS);
        if (!TextUtils.isEmpty(historyClass.getClassName()))
            courseName.setText(historyClass.getClassName());
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
        if (historyClass.getLearnStatus() == 1) {
            classStatus.setText(R.string.passed);
            roundLayout.getDelegate().setBackgroundColor(getResources().getColor(R.color.history_class_status_pass));
        } else {
            classStatus.setText(R.string.not_passed_tips);
            roundLayout.getDelegate().setBackgroundColor(getResources().getColor(R.color.history_class_status_not_pass));
        }
        mPresenter.getCourseList(page, historyClass.getId());
        String startTime = TimeUtils.parseTimeToYM(Long.parseLong(historyClass.getStartDate()));
        String endTime = TimeUtils.parseTimeToYM(Long.parseLong(historyClass.getEndDate()));
        date.setText(startTime + "-" + endTime);
        searchView.setSerach(onEditorActionListener);
        initFreshView();
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

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent();
                intent.setClass(HistoryClassInfoActivity.this, SearchResultActivity.class);
                intent.putExtra(Constants.SEARCH_CONTENT, searchView.getEditTextStr());
                intent.putExtra(Constants.CLASS_ID, String.valueOf(historyClass.getId()));
                startActivity(intent);
            }
            return false;
        }
    };

    public void initFreshView() {
        Utils.initFreshView(refreshView);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mPresenter.getCourseList(page, historyClass.getId());
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
            }
        });
    }
    @Override
    public void setLoadComplete(boolean b) {
        refreshView.setLoadComplete(true);
    }

    @Override
    public void stopLoadMore(boolean b) {
        refreshView.stopLoadMore();
    }

    @Override
    public void setList(List<CourseBean> dataList) {
        adapter.setList(dataList);
    }

    @Override
    public void addList(List<CourseBean> dataList) {
        adapter.addList(dataList);
    }
}