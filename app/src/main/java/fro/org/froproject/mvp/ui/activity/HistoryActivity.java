package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerHistoryComponent;
import fro.org.froproject.di.module.HistoryModule;
import fro.org.froproject.mvp.contract.HistoryContract;
import fro.org.froproject.mvp.model.entity.HistoryClassBean;
import fro.org.froproject.mvp.model.entity.PagedResult;
import fro.org.froproject.mvp.presenter.HistoryPresenter;
import fro.org.froproject.mvp.ui.adapter.HistoryClassAdapter;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/12 0012.
 */

public class HistoryActivity extends BaseActivity<HistoryPresenter> implements HistoryContract.View {
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(android.R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;
    private HistoryClassAdapter adapter;
    private int page;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerHistoryComponent
                .builder()
                .appComponent(appComponent)
                .historyModule(new HistoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_history_class;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        emptyImage.setImageResource(R.mipmap.class_blank);
        emptyText.setText("暂无历史班级");
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(this));
        initFreshView();
        adapter = new HistoryClassAdapter(new ArrayList<>());
        mRecyclerView.setAdapter(adapter);
        setEmptyView(true);
        mPresenter.getHistoryClassList(page, Constants.PAGE_SIZE);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Intent intent = new Intent();
            intent.putExtra(Constants.CLASS, adapter.getItem(position));
            intent.setClass(HistoryActivity.this, HistoryClassInfoActivity.class);
            launchActivity(intent);
        });

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

    public void initFreshView() {
        Utils.initFreshView(refreshView);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                mPresenter.getHistoryClassList(page, Constants.PAGE_SIZE);
            }
        });

    }

    @Override
    public void setList(List<HistoryClassBean> pagedResult) {
        adapter.setmInfos(pagedResult);
        if (adapter.getInfos() != null && adapter.getInfos().size() != 0) {
            setEmptyView(false);
        }else{
            setEmptyView(true);
        }
    }

    @Override
    public void addList(List<HistoryClassBean> pagedResult) {
        adapter.getInfos().addAll(pagedResult);
        adapter.notifyDataSetChanged();
        setEmptyView(false);
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

    public void setEmptyView(boolean visble) {
        emptyView.setVisibility(visble ? View.VISIBLE : View.GONE);
    }
}