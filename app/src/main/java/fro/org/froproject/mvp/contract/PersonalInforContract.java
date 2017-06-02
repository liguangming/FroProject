package fro.org.froproject.mvp.contract;

import android.content.Intent;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

public interface PersonalInforContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void setUserInfo();

        void launchActivityForResult(Intent intent, int i);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {

    }
}