package fro.org.froproject.app;

import com.jess.arms.base.BaseApplication;

import fro.org.froproject.mvp.model.entity.UserInfoBean;

/**
 * Created by Lgm on 2017/6/2 0002.
 */

public class MyApplication extends BaseApplication {
    private static MyApplication instance;
    private UserInfoBean userInfoBean;
    private String token;
    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
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
        instance = this;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
