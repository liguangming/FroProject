package fro.org.froproject.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;

import java.util.List;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.model.entity.ClassListBean;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public interface ClassContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void endLoadMore();

        void setJoinClassCount(String joinClassNumber);

        void add(List<ClassBean> list);

        void stopLoadMore();

        void setList(List<ClassBean> list);
    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<ClassListBean<ClassBean>>> getMyClassList(int page, int pageSize);
    }
}