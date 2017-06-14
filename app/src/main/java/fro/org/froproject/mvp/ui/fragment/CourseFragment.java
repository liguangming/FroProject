package fro.org.froproject.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jess.arms.base.BaseFragment;
import com.jess.arms.base.delegate.IFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.di.component.DaggerCourseComponent;
import fro.org.froproject.di.module.CourseModule;
import fro.org.froproject.mvp.contract.CourseContract;
import fro.org.froproject.mvp.presenter.CoursePresenter;
import fro.org.froproject.mvp.ui.activity.ProgressAndScoreActivity;
import fro.org.froproject.mvp.ui.view.HeadView;
import fro.org.froproject.mvp.ui.view.SearchView;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class CourseFragment extends BaseFragment<CoursePresenter> implements CourseContract.View {
    private int currIndex;//当前页卡编号
    private int offset;//两个相邻页面的偏移量
    @BindView(R.id.headView)
    HeadView headView;
    @BindView(R.id.pass_btn)
    TextView passedBtn;
    @BindView(R.id.not_pass_btn)
    TextView notPassedBtn;
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.line_view)
    View lineView;
    @BindView(R.id.search_view)
    SearchView searchView;
    private ArrayList<Fragment> fragmentList = new ArrayList<>();
    MyFragmentPagerAdapter pagerAdapter;

    public static CourseFragment newInstance() {
        return new CourseFragment();
    }


    @OnClick(R.id.rightText)
    public void gotoScoreActivity() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ProgressAndScoreActivity.class);
        launchActivity(intent);

    }
    @Override
    public void setupFragmentComponent(AppComponent appComponent) {
        DaggerCourseComponent
                .builder()
                .appComponent(appComponent)
                .courseModule(new CourseModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mycourse, container, false);
    }


    @OnClick({R.id.pass_btn, R.id.not_pass_btn})
    public void click(View view) {
        int id = view.getId();
        if (id == R.id.pass_btn) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.not_pass_btn) {
            viewPager.setCurrentItem(1);
        }
    }
    @Override
    public void initData(Bundle savedInstanceState) {
        headView.setTitleStr(R.string.my_course);
        headView.setRightText(R.string.progress_score);
        headView.getLeftImageView().setVisibility(View.GONE);
        fragmentList.add(CoursePassedFragment.newInstance());
        fragmentList.add(CourseNotPassedFragment.newInstance());
        pagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = screenW / 2;//两个相邻页面的偏移量
        searchView.setSerach(onEditorActionListener);
    }

    /**
     * @param data
     */

    @Override
    public void setData(Object data) {

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

    /**
     * ViewPagerAdapter
     */
    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> arrayList;
        ArrayList<String> mTags;

        public MyFragmentPagerAdapter(FragmentManager supportFragmentManager, ArrayList<Fragment> fragmentList) {
            super(supportFragmentManager);
            this.arrayList = fragmentList;
            mTags = new ArrayList<>();
        }

        @Override
        public Fragment getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            String tag = fragment.getTag();
            if (!mTags.contains(tag)) {
                mTags.add(tag);
            }
            return fragment;
        }
    }

    /**
     * 1.改变Tab文字颜色，选中或者未选中
     * 2.显示动画
     */
    private void changeView(int newIndex) {
        Animation animation = new TranslateAnimation(currIndex * offset, newIndex * offset, 0, 0);//平移动画
        currIndex = newIndex;
        animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
        animation.setDuration(200);//动画持续时间0.2秒
        lineView.startAnimation(animation);//是用ImageView来显示动画的
        switch (newIndex) {
            case 0:
                passedBtn.setTextColor(getResources().getColor(R.color.pass));
                notPassedBtn.setTextColor(getResources().getColor(R.color.notpass));
                break;
            case 1:
                notPassedBtn.setTextColor(getResources().getColor(R.color.pass));
                passedBtn.setTextColor(getResources().getColor(R.color.notpass));
                break;
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageSelected(int newIndex) {
//            if (fragmentList.get(currIndex) instanceof IFragment) {
//                ((IFragment) fragmentList.get(currIndex)).setData(null);
//            }
//            if (fragmentList.get(newIndex) instanceof IFragment) {
//                ((IFragment) fragmentList.get(newIndex)).setData(null);
//            }
            changeView(newIndex);
        }
    }
    TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                Intent intent = new Intent();
                //TODO 跳转到搜索结果界面
//                intent.setClass(getActivity(), SearchResultActivity.class);
//                intent.putExtra(Constants.SEARCH_STRING, searchView.getEditTextStr());
//                intent.putExtra(Constants.SEARCH_TYPE, currIndex == 0 ? "1" : "0");
//                startActivity(intent);
            }
            return false;
        }
    };

}