package fro.org.froproject.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fro.org.froproject.R;
import fro.org.froproject.mvp.model.api.Api;
import fro.org.froproject.mvp.model.entity.ScoreClassBean;
import fro.org.froproject.mvp.ui.view.GlideRoundTransform;
import fro.org.froproject.mvp.ui.view.RoundProgressBar;

/**
 * Created by Administrator on 2017/5/3 0003.
 */

public class ScoreAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflate;
    private List<ScoreClassBean> classes = new ArrayList<>();

    public ScoreAdapter(Context context) {
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }

    @Override
    public int getCount() {
        return classes.size();
    }

    @Override
    public ScoreClassBean getItem(int position) {
        return classes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.score_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.auto(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.courseName.setText(classes.get(position).getClassName());
        viewHolder.couserTotalNum.setText(context.getString(R.string.total) + classes.get(position).getTotalCourseNum() + context.getString(R.string.order_right));
        viewHolder.passCourseNum.setText(context.getString(R.string.passed_) + classes.get(position).getPassedCourseNum() + context.getString(R.string.order_right));
        // TODO: 2017/6/13 0013 设置圆角图片
        Glide.with(context).load(Api.APP_URL + classes.get(position).getClassPic()).transform(new GlideRoundTransform(context, 360)).into(viewHolder.image);
        //ImageLoader.getInstance().displayImage(UrlConfig.URL + classes.get(position).getClassPic(), viewHolder.image, myDisplayImageOptions);
        viewHolder.round_progress.setProgress(getItem(position).getProcess());
        viewHolder.notPassCourseNum.setText(context.getString(R.string.not_passed_) + getItem(position).getFailedCourseNum() + context.getString(R.string.order_right));
        viewHolder.notStudyCourseNum.setText(context.getString(R.string.not_study_) + getItem(position).getNotLearnNum() + context.getString(R.string.order_right));
        return convertView;
    }


    class ViewHolder {
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
        @BindView(R.id.not_pass_course_num)
        TextView notPassCourseNum;
        @BindView(R.id.not_study_course_num)
        TextView notStudyCourseNum;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
        }
    }

    public void setList(List<ScoreClassBean> classes) {
        this.classes = classes;
        notifyDataSetChanged();
    }

    public void addList(List<ScoreClassBean> classes) {
        this.classes.addAll(classes);
        notifyDataSetChanged();
    }
}
