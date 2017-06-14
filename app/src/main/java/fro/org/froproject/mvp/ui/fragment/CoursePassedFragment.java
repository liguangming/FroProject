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
import fro.org.froproject.di.component.DaggerCoursePassedComponent;
import fro.org.froproject.di.module.CoursePassedModule;
import fro.org.froproject.mvp.contract.CoursePassedContract;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.presenter.CoursePassedPresenter;
import fro.org.froproject.mvp.ui.activity.CourseStudyActivity;
import fro.org.froproject.mvp.ui.adapter.CoursePassAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/13 0013.
 */

public class CoursePassedFragment extends BaseFragment<CoursePassedPresenter> implements CoursePassedContract.View {
    @BindView(android.R.id.list)
    ListView listView;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    private CoursePassAdapter coursePassAdapter;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;
    private int page = 0;

    public static CoursePassedFragment newInstance() {
        CoursePassedFragment fragment = new CoursePassedFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCoursePassedComponent
                .builder()
                .appComponent(appComponent)
                .coursePassedModule(new CoursePassedModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.course_pass, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        coursePassAdapter = new CoursePassAdapter(getContext());
        listView.setEmptyView(emptyView);
        listView.setAdapter(coursePassAdapter);
        View view = new View(getActivity());
        view.setMinimumHeight(3);
        listView.addFooterView(view, null, false);
        Utils.initFreshView(refreshView);
        refreshView.setPullRefreshEnable(true);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                page = 0;
                mPresenter.getCourseList(page, Constants.PAGE_SIZE);
                refreshView.setLoadComplete(false);
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                mPresenter.getCourseList(page, Constants.PAGE_SIZE);
            }

            @Override
            public void onRelease(float direction) {
                super.onRelease(direction);
            }
        });
    }

    @Override
    public void setData(Object data) {

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
        coursePassAdapter.setList(dataList);
        refreshView.stopRefresh();
    }

    @Override
    public void addList(List<CourseBean> dataList) {
        coursePassAdapter.addList(dataList);
    }

    @Override
    public void endLoadMore() {
        refreshView.setLoadComplete(true);
    }

    @Override
    public void stopLoadMore() {
        refreshView.stopLoadMore();
        page++;
    }
    @OnItemClick(android.R.id.list)
    public void gotoActivity(int position) {
        Intent intent = new Intent();
        intent.putExtra(Constants.COURSE, coursePassAdapter.getItem(position));
        intent.setClass(getActivity(), CourseStudyActivity.class);
        startActivity(intent);
    }


}