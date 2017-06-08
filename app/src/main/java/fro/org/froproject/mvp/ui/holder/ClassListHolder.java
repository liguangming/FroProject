package fro.org.froproject.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.ui.view.RoundProgressBar;

/**
 * Created by Lgm on 2017/6/8 0008.
 */

public class ClassListHolder extends BaseHolder<ClassBean> {
    @BindView(R.id.round_progress)
    RoundProgressBar round_progress;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.course_total_num)
    TextView couserTotalNum;
    @BindView(R.id.pass_course_num)
    TextView passCourseNum;
    @BindView(R.id.bottom_line)
    View bottomLine;

    public ClassListHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(ClassBean data, int position) {
        courseName.setText(data.getClassName());
        couserTotalNum.setText(MyApplication.getInstance().getString(R.string.total) + data.getTotalCourseNum() + MyApplication.getInstance().getString(R.string.course_unit));
        passCourseNum.setText(MyApplication.getInstance().getString((R.string.pass_course)) + data.getPassedCourseNum() + MyApplication.getInstance().getString(R.string.order_right));
        round_progress.setProgress(data.getProcess());
//        ImageLoader.getInstance().displayImage(UrlConfig.URL + getItem(position).getClassPic(), image);
//        if (position == getCount() - 1) {
//            bottomLine.setVisibility(View.GONE);
//        } else {
//            bottomLine.setVisibility(View.VISIBLE);
//        }
    }
}
