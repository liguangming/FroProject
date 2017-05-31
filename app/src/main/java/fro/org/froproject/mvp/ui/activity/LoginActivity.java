package fro.org.froproject.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.UiUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import fro.org.froproject.R;
import fro.org.froproject.di.component.DaggerLoginComponent;
import fro.org.froproject.mvp.presenter.LoginPresenter;
import fro.org.froproject.di.module.LoginModule;
import fro.org.froproject.mvp.contract.LoginContract;

import static com.jess.arms.utils.Preconditions.checkNotNull;

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

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.password_edit)
    EditText password_edit;
    @BindView(R.id.forget_passwrod)
    TextView forgetPassword;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this)) //请将LoginModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.login)
    public void click() {
        String phoneNum = phone_edit.getText().toString();
        String passWord = password_edit.getText().toString();
        mPresenter.login(phoneNum, passWord);
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


}