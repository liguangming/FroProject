package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerSearchResultComponent;
import fro.org.froproject.di.module.SearchResultModule;
import fro.org.froproject.mvp.contract.SearchResultContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.presenter.SearchResultPresenter;
import fro.org.froproject.mvp.ui.adapter.SearchResultAdapter;
import fro.org.froproject.mvp.ui.view.SearchView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

public class SearchResultActivity extends BaseActivity<SearchResultPresenter> implements SearchResultContract.View {
    @BindView(R.id.search_view)
    SearchView searchView;
    @BindView(android.R.id.list)
    ListView listView;
    private String searchContetn;
    private SearchResultAdapter adapter;
    private String searchType;
    private String classID;
    int page;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerSearchResultComponent
                .builder()
                .appComponent(appComponent)
                .searchResultModule(new SearchResultModule(this))
                .build()
                .inject(this);
    }
    @OnItemClick(android.R.id.list)
    public void itemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, adapter.getItem(position));
        intent.setClass(this, CourseStudyActivity.class);
        startActivity(intent);

    }
    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_search_result;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        searchView.setEditTextColor(R.color.white);
        searchView.setRightImageVisible(true);
        adapter = new SearchResultAdapter(this);
        listView.setAdapter(adapter);
        searchType = getIntent().getStringExtra(Constants.SEARCH_TYPE);
        searchContetn = getIntent().getStringExtra(Constants.SEARCH_CONTENT);
        searchView.setEditText(searchContetn);
        searchView.setSerach(onEditorActionListener);
        classID = getIntent().getStringExtra(Constants.CLASS_ID);
        initFreshView();
        if (TextUtils.isEmpty(classID)) {
            mPresenter.getCourseList(page, searchContetn, searchType);
        } else {
            mPresenter.getClassCourseList(classID, page, searchContetn);
        }
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

    public void initFreshView() {
        Utils.initFreshView(refreshView);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                if (TextUtils.isEmpty(classID)) {
                    mPresenter.getCourseList(page, searchContetn, searchType);
                } else {
                    mPresenter.getClassCourseList(classID, page, searchContetn);
                }
            }

        });

    }

    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchContetn = searchView.getEditTextStr();
                page = 0;
                if (TextUtils.isEmpty(classID)) {
                    mPresenter.getCourseList(page, searchContetn, searchType);
                } else {
                    mPresenter.getClassCourseList(classID, page, searchContetn);
                }
            }
            return false;
        }
    };

    @Override
    public void setLoadComplete(boolean b) {
        refreshView.setLoadComplete(true);
    }

    @Override
    public void stopLoadMore(boolean b) {
        refreshView.stopLoadMore();
        page++;
    }

    @Override
    public void setList(List<CourseBean> courseList) {
        adapter.setList(courseList);
    }

    @Override
    public void addList(List<CourseBean> courseList) {
        adapter.addList(courseList);
    }
}