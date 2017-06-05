package fro.org.froproject.mvp.presenter;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.integration.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.widget.imageloader.ImageLoader;

import org.fro.common.widgets.LoadingView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fro.org.froproject.app.MyApplication;
import fro.org.froproject.app.utils.CheckUtils;
import fro.org.froproject.app.utils.RxUtils;
import fro.org.froproject.app.utils.ToastUtils;
import fro.org.froproject.app.utils.Utils;
import fro.org.froproject.mvp.contract.LoginContract;
import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.UserInfoBean;
import fro.org.froproject.mvp.ui.activity.ForgetPasswordActivity;
import fro.org.froproject.mvp.ui.activity.PersonalInforActivity;
import fro.org.froproject.mvp.ui.activity.RegisterActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

import javax.inject.Inject;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */


/**
 * Created by Lgm on 2017/5/31 0031.
 */

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void login(String phoneNum, String password) {
        mModel.login(phoneNum, Utils.encodePassword(password))
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mRootView.showLoading())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(() -> mRootView.hideLoading())
                .compose(RxUtils.<BaseJson>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson>(mErrorHandler) {
                    @Override
                    public void onNext(@NonNull BaseJson baseJson) {
                        if (baseJson.isSuccess()) {//登录成功
                            UserInfoBean userInfoBean = (UserInfoBean) baseJson.getD();
                            Intent intent = new Intent(mApplication, ForgetPasswordActivity.class);
                            MyApplication.getInstance().setUserInfoBean(userInfoBean);
                            mRootView.launchActivity(intent);
                            mRootView.killMyself();
                        }
                    }
                });
    }

    public void gotoForgetPassWordActivity() {
        Intent intent = new Intent(mApplication, ForgetPasswordActivity.class);
        mRootView.launchActivity(intent);
    }

    public void gotoRegisterPassWord() {
        Intent intent = new Intent(mApplication, PersonalInforActivity.class);
        mRootView.launchActivity(intent);
    }
}