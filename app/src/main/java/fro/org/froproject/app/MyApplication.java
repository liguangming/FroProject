package fro.org.froproject.app;

import android.app.Application;

import com.jess.arms.base.App;
import com.jess.arms.base.delegate.AppDelegate;
import com.jess.arms.di.component.AppComponent;

import org.fro.common.widgets.locationview.entity.ProvinceData;

import java.util.ArrayList;

import fro.org.froproject.mvp.model.entity.UserInfoBean;

/**
 * Created by Lgm on 2017/6/2 0002.
 */

public class MyApplication extends Application implements App {
    private AppDelegate mAppDelegate;
    private static MyApplication instance;
    private UserInfoBean userInfoBean;
    private String token;
    private ArrayList<ProvinceData> provinceDataList;

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public ArrayList<ProvinceData> getProvinceDataList() {
        return provinceDataList;
    }

    public void setProvinceDataList(ArrayList<ProvinceData> provinceDataList) {
        this.provinceDataList = provinceDataList;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }

    public
    static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mAppDelegate = new AppDelegate(this);
        this.mAppDelegate.onCreate();
        instance = this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        this.mAppDelegate.onTerminate();
    }

    /**
     * 将AppComponent返回出去,供其它地方使用, AppComponent接口中声明的方法返回的实例,在getAppComponent()拿到对象后都可以直接使用
     *
     * @return
     */
    @Override
    public AppComponent getAppComponent() {
        return mAppDelegate.getAppComponent();
    }

}
