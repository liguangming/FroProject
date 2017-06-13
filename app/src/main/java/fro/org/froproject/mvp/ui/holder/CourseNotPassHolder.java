package fro.org.froproject.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import org.fro.common.widgets.tab.roundtextview.RoundRelativeLayout;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.model.entity.CourseBean;

/**
 * Created by Lgm on 2017/6/13 0013.
 */

public class CourseNotPassHolder  extends BaseHolder<CourseBean>{
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.status_layout)
    RoundRelativeLayout statusLayout;
    @BindView(R.id.status_text)
    TextView statusText;
    public CourseNotPassHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(CourseBean data, int position) {
        courseName.setText(MyApplication.getInstance().getString(R.string.order_left) + data.getOrder() + MyApplication.getInstance().getString(R.string.order_right) + "  " + data.getCourseName());
        if (data.getLearnStatus() == -1) {
            statusLayout.setVisibility(View.GONE);
        } else {
            statusLayout.setVisibility(View.VISIBLE);
            statusLayout.getDelegate().setBackgroundColor(MyApplication.getInstance().getResources().getColor(R.color.not_pass_red));
            statusText.setText(R.string.not_passed_tips);
            statusText.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.not_pass_red_text));
        }
    }
}
