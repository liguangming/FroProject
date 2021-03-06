package fro.org.froproject.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;


import java.util.List;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.OrgBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

public interface CommonOrgContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setAdapter(List<OrgBean> nature);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {

        Observable<BaseJson<List<OrgBean>>> getNatureList();

        Observable<BaseJson<List<OrgBean>>> getWorkYearList();

        Observable<BaseJson<List<OrgBean>>> getCredentials();

        Observable<BaseJson<List<OrgBean>>> getOrgTypeList(int natureId);

        Observable<BaseJson<List<OrgBean>>> getOrgDetailList(int orgTypeId);
    }
}