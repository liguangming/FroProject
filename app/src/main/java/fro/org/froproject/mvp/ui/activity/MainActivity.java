package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;


import org.fro.common.widgets.tab.tablayout.CommonTabLayout;
import org.fro.common.widgets.tab.tablayout.TabEntity;
import org.fro.common.widgets.tab.tablayout.listener.CustomTabEntity;
import org.fro.common.widgets.tab.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.Constants;
import fro.org.froproject.di.component.DaggerMainComponent;
import fro.org.froproject.di.module.MainModule;
import fro.org.froproject.mvp.contract.MainContract;
import fro.org.froproject.mvp.presenter.MainPresenter;
import fro.org.froproject.mvp.ui.fragment.ClassFragment;
import fro.org.froproject.mvp.ui.fragment.CourseFragment;
import fro.org.froproject.mvp.ui.fragment.PersonalFragment;
import fro.org.froproject.mvp.ui.fragment.ServiceFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.tab_layout_main)
    CommonTabLayout tabLayout;
    private ArrayList<CustomTabEntity> tabEntities = new ArrayList<>();
    private int currentIndex;
    private ClassFragment classFragment;
    private CourseFragment courseFragment;
    private PersonalFragment personalFragment;
    private ServiceFragment serviceFragment;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initTabs();
    }

    private void initTabs() {
        String[] tabTitles = {"1", "2", "3", "4"};  //titles at the bottom
        int[] iconUnselectedIds = {
                R.mipmap.tab_class_normal, R.mipmap.tab_course_normal, R.mipmap.tab_service_normal,
                R.mipmap.tab_personalcenter_normal};// R.mipmap.sa_tab_shop_normal,
        int[] iconSelectedIds = {
                R.mipmap.tab_class_select, R.mipmap.tab_course__select, R.mipmap.tab_service_select,
                R.mipmap.tab_personalcenter_select};// R.mipmap.sa_tab_shop_select,
        for (int i = 0; i < tabTitles.length; i++) {
            tabEntities.add(new TabEntity(tabTitles[i], iconSelectedIds[i], iconUnselectedIds[i]));
        }
        tabLayout.setTabData(tabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                currentIndex = position;
                changeFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        currentIndex = getIntent().getIntExtra(Constants.CURRENT_PAGE, 0);
        changeFragment(currentIndex);
    }

    public void changeFragment(int index) {
        tabLayout.setCurrentTab(index);
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (index) {
            case 0:
                if (classFragment == null) {
                    classFragment = ClassFragment.newInstance();
                }
                showFragment(transaction, classFragment);
                break;
            case 1:
                if (courseFragment == null) {
                    courseFragment = CourseFragment.newInstance();
                }
                showFragment(transaction, courseFragment);
                break;
            case 2:
                if (serviceFragment == null) {
                    serviceFragment = ServiceFragment.newInstance();
                }
                showFragment(transaction, serviceFragment);
                break;
            case 3:
                if (personalFragment == null) {
                    personalFragment = PersonalFragment.newInstance();
                }
                showFragment(transaction, personalFragment);
                break;
            default:
                return;
        }

        transaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();//避免快速切换fragment，报fragment already added问题
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

    private void hideFragment(FragmentTransaction transaction) {
        if (classFragment != null) {
            transaction.hide(classFragment);
        }
        if (courseFragment != null) {
            transaction.hide(courseFragment);
        }
        if (serviceFragment != null) {
            transaction.hide(serviceFragment);
        }
        if (personalFragment != null) {
            transaction.hide(personalFragment);
        }
    }
    private void showFragment(FragmentTransaction transaction, Fragment showFragment) {
        if (!showFragment.isAdded()) {
            if (showFragment.getTag() == null) {
                transaction.add(R.id.tab_fragment_layout, showFragment, showFragment.getClass().getName());
            } else {
                transaction.add(R.id.tab_fragment_layout, showFragment);
            }
        } else {
            if (showFragment instanceof IFragment) {
                ((IFragment) showFragment).setData(null);
            }
            transaction.show(showFragment);
        }

    }
}