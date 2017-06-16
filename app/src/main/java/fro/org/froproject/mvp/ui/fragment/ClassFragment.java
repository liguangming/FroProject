package fro.org.froproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.XRefreshView;
import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;
import com.paginate.Paginate;

import org.fro.common.util.TimeUtils;
import org.fro.common.widgets.LoadingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnItemClick;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.di.component.DaggerClassComponent;
import fro.org.froproject.di.module.ClassModule;
import fro.org.froproject.mvp.contract.ClassContract;
import fro.org.froproject.mvp.model.api.Api;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.presenter.ClassPresenter;
import fro.org.froproject.mvp.ui.activity.ClassInfoActivity;
import fro.org.froproject.mvp.ui.activity.HistoryActivity;
import fro.org.froproject.mvp.ui.adapter.ClassListAdapter;
import fro.org.froproject.mvp.ui.view.GlideRoundTransform;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class ClassFragment extends BaseFragment<ClassPresenter> implements ClassContract.View {
    @BindView(android.R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.join_class_count)
    TextView joinClassCount;
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.year_month)
    TextView yearMonth;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.empty_image)
    ImageView emptyImage;
    @BindView(R.id.empty_text)
    TextView emptyText;
    @BindView(R.id.userImage)
    ImageView userImage;
    @BindView(R.id.empty_layout)
    LinearLayout emptyView;
    @BindView(R.id.listfoot)
    LinearLayout listFoot;
    @BindView(R.id.custom_view)
    XRefreshView refreshView;
    private int page;
    private ClassListAdapter adapter;
    private AppComponent mAppComponent;

    public static ClassFragment newInstance() {
        ClassFragment fragment = new ClassFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        this.mAppComponent = appComponent;
        DaggerClassComponent
                .builder()
                .appComponent(appComponent)
                .classModule(new ClassModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_class, container, false);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initRecycleView();
        adapter = new ClassListAdapter(new ArrayList<>());
        setEmptyView(true);
        mRecyclerView.setAdapter(adapter);
        mPresenter.getMyClassList(page);
        headView.setRightText(R.string.history_class);
        headView.setRightListener(v -> UiUtils.startActivity(HistoryActivity.class));
        setData(null);
        adapter.setOnItemClickListener((view, viewType, data, position) -> {
            Intent intent = new Intent();
            intent.putExtra(Constants.CLASS_ID, adapter.getItem(position).getId());
            intent.setClass(getActivity(), ClassInfoActivity.class);
            launchActivity(intent);
        });

    }

    @Override
    public void setData(Object data) {
        page = 0;
        UserInfoBean userInfo = MyApplication.getInstance().getUserInfoBean();
        userName.setText(userInfo.getNickName());
        if (userInfo.getOrganizationResponse() != null && !TextUtils.isEmpty(userInfo.getOrganizationResponse().getName())) {
            bankName.setText(userInfo.getOrganizationResponse().getName());
        } else {
            bankName.setText(userInfo.getCategoryResponse().getName());
        }
        yearMonth.setText(TimeUtils.getcurrentYear() + "/" + TimeUtils.getCurrentMonth());
        date.setText(TimeUtils.getCurrentDay());
        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
            mAppComponent.imageLoader().loadImage(mAppComponent.appManager().getCurrentActivity() == null
                    ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(), GlideImageConfig
                    .builder()
                    .url(Api.APP_URL1 + userInfo.getAvatar())
                    .errorPic(R.mipmap.head_image)
//                    .transformation(new GlideRoundTransform(getActivity()))
                    .imageView(userImage)
                    .build());
        }
        mPresenter.getMyClassList(page);
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
    public void stopLoadMore() {
        page++;
        refreshView.stopLoadMore();
    }

    @Override
    public void setList(List<ClassBean> list) {
        adapter.setmInfos(list);
        if (list != null && list.size() > 0)
            setEmptyView(false);
        else setEmptyView(true);

    }

    @Override
    public void endLoadMore() {
        refreshView.setLoadComplete(true);
    }


    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));
        Utils.initFreshView(refreshView);
        refreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onLoadMore(boolean isSilence) {
                mPresenter.getMyClassList(page);
            }
        });
    }

    @Override
    public void setJoinClassCount(String classCount) {
        joinClassCount.setText("已加入" + classCount + "个班级");
    }

    @Override
    public void add(List<ClassBean> list) {
        adapter.getInfos().addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setEmptyView(boolean visible) {
        emptyView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}