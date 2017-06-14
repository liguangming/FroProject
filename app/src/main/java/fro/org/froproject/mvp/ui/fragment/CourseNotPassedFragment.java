package fro.org.froproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.jess.arms.base.BaseFragment;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.LoadingView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerCourseNotPassedComponent;
import fro.org.froproject.di.module.CourseNotPassedModule;
import fro.org.froproject.mvp.contract.CourseNotPassedContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.presenter.CourseNotPassedPresenter;
import fro.org.froproject.mvp.ui.activity.CourseStudyActivity;
import fro.org.froproject.mvp.ui.adapter.CourseNotPassAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

public class CourseNotPassedFragment extends BaseFragment<CourseNotPassedPresenter> implements CourseNotPassedContract.View {
    @BindView(android.R.id.list)
    ListView listView;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    private CourseNotPassAdapter courseNotPassAdapter;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;
    private int page = 0;

    public static CourseNotPassedFragment newInstance() {
        CourseNotPassedFragment fragment = new CourseNotPassedFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCourseNotPassedComponent
                .builder()
                .appComponent(appComponent)
                .courseNotPassedModule(new CourseNotPassedModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_not_pass, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initFreshView();
        courseNotPassAdapter = new CourseNotPassAdapter(getActivity());
        listView.setAdapter(courseNotPassAdapter);
        View view = new View(getActivity());
        view.setMinimumHeight(3);
        listView.addFooterView(view, null, false);
        emptyText.setText("暂无课程");
        emptyImage.setImageResource(R.mipmap.course_blank);
        listView.setEmptyView(emptyView);
        setData(null);
    }

    @Override
    public void setData(Object data) {
        page = 0;
        mPresenter.getNotPassCourseList(page, Constants.PAGE_SIZE);
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

    @Override
    public void setList(List<CourseBean> dataList) {
        courseNotPassAdapter.setList(dataList);
        refreshView.stopRefresh();
    }

    @Override
    public void addList(List<CourseBean> dataList) {
        courseNotPassAdapter.addList(dataList);
        page++;
    }

    @Override
    public void stopLoadMore() {
        refreshView.stopLoadMore();
    }

    @Override
    public void endLoadMore() {
        refreshView.setLoadComplete(true);
    }

    public void initFreshView() {
        Utils.initFreshView(refreshView);
        refreshView.setPullRefreshEnable(true);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                page = 0;
                mPresenter.getNotPassCourseList(page, Constants.PAGE_SIZE);
                refreshView.setLoadComplete(false);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mPresenter.getNotPassCourseList(page, Constants.PAGE_SIZE);
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
            }
        });
    }

    @OnItemClick(android.R.id.list)
    public void gotoActivity(int position) {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, courseNotPassAdapter.getItem(position));
        intent.setClass(getActivity(), CourseStudyActivity.class);
        startActivity(intent);
    }
}