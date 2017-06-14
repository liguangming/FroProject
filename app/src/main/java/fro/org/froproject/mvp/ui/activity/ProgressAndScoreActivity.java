package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import org.fro.common.widgets.LoadingView;

import java.util.List;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerProgressAndScoreComponent;
import fro.org.froproject.di.module.ProgressAndScoreModule;
import fro.org.froproject.mvp.contract.ProgressAndScoreContract;
import fro.org.froproject.mvp.model.entity.ScoreClassBean;
import fro.org.froproject.mvp.presenter.ProgressAndScorePresenter;
import fro.org.froproject.mvp.ui.adapter.ScoreAdapter;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 进度与成绩
 * Created by Lgm on 2017/6/13 0013.
 */

public class ProgressAndScoreActivity extends BaseActivity<ProgressAndScorePresenter> implements ProgressAndScoreContract.View {
    @BindView(android.R.id.list)
    ListView listView;
    ScoreAdapter adapter;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;

    private int page = 0;


    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerProgressAndScoreComponent
                .builder()
                .appComponent(appComponent)
                .progressAndScoreModule(new ProgressAndScoreModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.progress_and_score;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        emptyImage.setImageResource(R.mipmap.class_blank);
        emptyText.setText(R.string.no_info);
        adapter = new ScoreAdapter(this);
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyView);
        LoadingView.showLoading(this);
        initFreshView();
        mPresenter.getScoreClassList(page);
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
    public void setList(List<ScoreClassBean> pagedResult) {
        adapter.setList(pagedResult);
    }

    @Override
    public void endLoadMore() {
        refreshView.setLoadComplete(true);
    }

    @Override
    public void stopLoadMore() {
        page++;
        refreshView.stopLoadMore();
    }

    @Override
    public void addList(List<ScoreClassBean> pagedResult) {
        adapter.addList(pagedResult);
    }

    public void initFreshView() {
        Utils.initFreshView(refreshView);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                mPresenter.getScoreClassList(page);
            }
        });

    }
}