package fro.org.froproject.mvp.contract;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.io.File;
import java.util.List;
import java.util.Map;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CommonBean;
import fro.org.froproject.mvp.model.entity.OrgBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/16 0016.
 */

public interface ModifyUserInfoContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void launchActivityForResult(Intent intent, int requestCode);

        void setLocation(String s);

        void setName(int result, String name);

        void startCropActivity(Uri uri);

        void setAtvorImage(Bitmap roundBitmap);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {

        Observable<BaseJson<List<OrgBean>>> getOrgDetailList(int orgTypeId);

        Observable<BaseJson> commit(Map<String, String> data);

        Observable<BaseJson<CommonBean>> uploadImg(File file);
    }
}