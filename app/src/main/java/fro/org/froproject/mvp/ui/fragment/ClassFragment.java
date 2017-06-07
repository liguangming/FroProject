package fro.org.froproject.mvp.ui.fragment;

import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;
import com.paginate.Paginate;

import org.fro.common.util.TimeUtils;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.di.component.DaggerClassComponent;
import fro.org.froproject.di.module.ClassModule;
import fro.org.froproject.mvp.contract.ClassContract;
import fro.org.froproject.mvp.model.api.Api;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.presenter.ClassPresenter;
import fro.org.froproject.mvp.ui.view.GlideRoundTransform;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class ClassFragment extends BaseFragment<ClassPresenter> implements ClassContract.View, SwipeRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Paginate mPaginate;

    public static ClassFragment newInstance() {
        ClassFragment fragment = new ClassFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
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
        mPresenter.getMyClassList();
    }


    @Override
    public void setData(Object data) {
        UserInfoBean userInfo = MyApplication.getInstance().getUserInfoBean();
        userName.setText(userInfo.getNickName());
        if (userInfo.getOrganizationResponse() != null && TextUtils.isEmpty(userInfo.getOrganizationResponse().getName())) {
            bankName.setText(userInfo.getOrganizationResponse().getName());
        } else {
            bankName.setText(userInfo.getCategoryResponse().getName());
        }
        yearMonth.setText(TimeUtils.getcurrentYear() + "/" + TimeUtils.getCurrentMonth());
        date.setText(TimeUtils.getCurrentDay());
        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
            Glide.with(this)
                    .load(Api.APP_URL + userInfo.getAvatar())
                    .transform(new GlideRoundTransform(getActivity(), 360))
                    .into(userImage);
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

    }


    @Override
    public void setAdapter(DefaultAdapter adapter) {

    }

    @Override
    public void startLoadMore() {

    }

    @Override
    public void endLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.getMyClassList();
                }

                @Override
                public boolean isLoading() {
                    return true;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }
    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        UiUtils.configRecycleView(mRecyclerView, new LinearLayoutManager(getActivity()));
    }

}