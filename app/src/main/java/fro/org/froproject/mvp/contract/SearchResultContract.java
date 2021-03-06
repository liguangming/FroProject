package fro.org.froproject.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import fro.org.froproject.mvp.model.entity.BaseJson;
import fro.org.froproject.mvp.model.entity.CourseBean;
import fro.org.froproject.mvp.model.entity.CourseResponseBean;
import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * Created by Lgm on 2017/6/15 0015.
 */

public interface SearchResultContract {
    //对于经常使用的关于UI的方法可以定义到BaseView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {

        void setLoadComplete(boolean b);

        void stopLoadMore(boolean b);

        void setList(List<CourseBean> courseList);

        void addList(List<CourseBean> courseList);


    }

    //Model层定义接口,外部只需关心model返回的数据,无需关心内部细节,及是否使用缓存
    interface Model extends IModel {
        Observable<BaseJson<CourseResponseBean>> getCourseList(int page, String courseName, String type);

        Observable<BaseJson<CourseResponseBean>> getClassCourseList(int page, String searchContetn, String classID);

    }
}