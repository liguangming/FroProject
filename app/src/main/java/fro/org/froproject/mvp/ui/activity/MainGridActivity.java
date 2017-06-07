package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindArray;
import butterknife.BindView;
import butterknife.OnItemClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.di.component.DaggerMainGridComponent;
import fro.org.froproject.di.module.MainGridModule;
import fro.org.froproject.mvp.contract.MainGridContract;
import fro.org.froproject.mvp.presenter.MainGridPresenter;
import fro.org.froproject.mvp.ui.adapter.GridAdapter;
import fro.org.froproject.mvp.ui.view.SCommonItemDecoration;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class MainGridActivity extends BaseActivity<MainGridPresenter> implements MainGridContract.View {
    @BindView(R.id.gridView)
    RecyclerView recyclerView;
    @BindArray(R.array.name_chinese)
    String[] chineseName;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainGridComponent
                .builder()
                .appComponent(appComponent)
                .mainGridModule(new MainGridModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main_grid;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(this, 2);
        mGridLayoutManager.setSpanCount(2);
        UiUtils.configRecycleView(recyclerView, mGridLayoutManager);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < chineseName.length; i++) {
            list.add(chineseName[i]);
        }
        GridAdapter adapter = new GridAdapter(list);
        recyclerView.setAdapter(adapter);
        initItemDecoration(recyclerView, adapter);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            launchActivity(new Intent(this, MainActivity.class).putExtra(Constants.CURRENT_PAGE, position));
        });

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

    public void initItemDecoration(RecyclerView recyclerView, GridAdapter adapter) {
        int verticalSpace = 40;
        int horizontalSpace = 40;
        SparseArray<SCommonItemDecoration.ItemDecorationProps> propMap = new SparseArray<>();
        SCommonItemDecoration.ItemDecorationProps prop1 =
                new SCommonItemDecoration.ItemDecorationProps(horizontalSpace, verticalSpace, false, false);
        propMap.put(0, prop1);

        verticalSpace = 40;
        horizontalSpace = 40;
        SCommonItemDecoration.ItemDecorationProps prop2 =
                new SCommonItemDecoration.ItemDecorationProps(horizontalSpace, verticalSpace, false, false);
        propMap.put(1, prop2);
        recyclerView.addItemDecoration(new SCommonItemDecoration(propMap));
    }
}