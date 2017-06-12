package fro.org.froproject.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.HistoryClassBean;
import fro.org.froproject.mvp.model.entity.HistoryClassListBean;
import fro.org.froproject.mvp.model.entity.PagedResult;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/12 0012.
 */

public interface HistoryContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void setList(List<HistoryClassBean> pagedResult);

        void addList(List<HistoryClassBean> pagedResult);

        void stopLoadMore();

        void endLoadMore();

    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<HistoryClassListBean<HistoryClassBean>>> getHistoryClassList(int page, int size);
    }
}