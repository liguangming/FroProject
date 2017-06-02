package fro.org.froproject.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.LoadingView;

import java.util.List;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.di.component.DaggerCommonOrgComponent;
import fro.org.froproject.di.module.CommonOrgModule;
import fro.org.froproject.mvp.contract.CommonOrgContract;
import fro.org.froproject.mvp.model.entity.OrgBean;
import fro.org.froproject.mvp.presenter.CommonOrgPresenter;
import fro.org.froproject.mvp.ui.adapter.CommonActivityAdapter;
import fro.org.froproject.mvp.ui.view.HeadView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * 选择公用类
 * Created by Lgm on 2017/6/2 0002.
 */

public class CommonActivity extends BaseActivity<CommonOrgPresenter> implements CommonOrgContract.View {

    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerCommonOrgComponent
                .builder()
                .appComponent(appComponent)
                .commonOrgModule(new CommonOrgModule(this)) //请将CommonOrgModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_common_activity;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        headView.setRightText("完成");
        Intent intent=getIntent();
        if(intent.getStringExtra(Constants.REQUEST).equals(Constants.ORG_NATURE)){ //机构性质
            headView.setTitleStr(R.string.org_nature);
            mPresenter.getNatureList();
        }else if(intent.getStringExtra(Constants.REQUEST).equals(Constants.ORG_TYPE)){//机构类别
            int orgNatureId = intent.getIntExtra(Constants.ORG_NATURE, -999);
            MyApplication.getInstance().setUrl("traininggorganization/category/list/"+orgNatureId);
        }else if(intent.getStringExtra(Constants.REQUEST).equals(Constants.ORG_DETAIL)){//机构详情
            int orgTypeId = intent.getIntExtra(Constants.ORG_TYPE, -999);
            MyApplication.getInstance().setUrl("traininggorganization/category/list/"+orgTypeId);
        }
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
    public void setAdapter(List<OrgBean> nature) {
        CommonActivityAdapter adapter=new CommonActivityAdapter(nature);
        mRecyclerView.setAdapter(adapter);
    }
}