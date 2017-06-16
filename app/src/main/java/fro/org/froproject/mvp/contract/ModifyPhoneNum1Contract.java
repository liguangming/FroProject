package fro.org.froproject.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CommonBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

public interface ModifyPhoneNum1Contract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void showCountView();

    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {

        Observable<BaseJson> getAuthCode(String phone);

        Observable<BaseJson<CommonBean>> commitModifyPhone1(String authCode, String phone);
    }
}