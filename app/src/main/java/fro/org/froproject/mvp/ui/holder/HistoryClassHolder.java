package fro.org.froproject.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.App;
import com.jess.arms.base.BaseHolder;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.widget.imageloader.ImageLoader;
import com.jess.arms.widget.imageloader.glide.GlideImageConfig;

import org.fro.common.util.TimeUtils;
import org.fro.common.widgets.tab.roundtextview.RoundRelativeLayout;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.app.MyApplication;
import fro.org.froproject.mvp.model.api.Api;
import fro.org.froproject.mvp.model.entity.HistoryClassBean;
import fro.org.froproject.mvp.ui.view.GlideRoundTransform;

/**
 * Created by Lgm on 2017/6/12 0012.
 */

public class HistoryClassHolder extends BaseHolder<HistoryClassBean> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;
    @BindView(R.id.pass_status)
    TextView passStatus;
    @BindView(R.id.course_total_num)
    TextView couserTotalNum;
    @BindView(R.id.round_text)
    RoundRelativeLayout roundText;
    @BindView(R.id.course_name)
    TextView courseName;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.image)
    ImageView image;

    public HistoryClassHolder(View itemView) {
        super(itemView);
        mAppComponent = ((App) itemView.getContext().getApplicationContext()).getAppComponent();
        mImageLoader = mAppComponent.imageLoader();
    }

    @Override
    public void setData(HistoryClassBean data, int position) {
        courseName.setText(data.getClassName());
        mImageLoader.loadImage(mAppComponent.appManager().getCurrentActivity() == null
                        ? mAppComponent.Application() : mAppComponent.appManager().getCurrentActivity(),
                GlideImageConfig
                        .builder()
                        .url(Api.APP_URL + data.getClassPic())
                        .transformation(new GlideRoundTransform(MyApplication.getInstance(), 360))
                        .imageView(image)
                        .build());
        couserTotalNum.setText(String.valueOf(MyApplication.getInstance().getString(R.string.total) + data.getTotalCourseNum() + MyApplication.getInstance().getString(R.string.course_unit)));

        if (data.getLearnStatus() == 1) {
            passStatus.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.history_pass_text));
            roundText.getDelegate().setBackgroundColor(MyApplication.getInstance().getResources().getColor(R.color.history_pass));
            passStatus.setText(R.string.passed);
        } else {
            roundText.getDelegate().setBackgroundColor(MyApplication.getInstance().getResources().getColor(R.color.history_not_pass));
            passStatus.setText(R.string.not_passed_tips);
            passStatus.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.history_not_pass_text));
        }
        String startTime = TimeUtils.parseTimeToYM(Long.parseLong(data.getStartDate()));
        String endTime = TimeUtils.parseTimeToYM(Long.parseLong(data.getEndDate()));
        date.setText(startTime + "-" + endTime);
    }
}
