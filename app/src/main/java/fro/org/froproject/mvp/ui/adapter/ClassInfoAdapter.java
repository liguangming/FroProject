package fro.org.froproject.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import org.fro.common.util.TimeUtils;
import org.fro.common.widgets.tab.roundtextview.RoundRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fro.org.froproject.R;
import fro.org.froproject.mvp.model.entity.CourseBean;

/**
 * Created by Administrator on 2017/4/26.
 */

public class ClassInfoAdapter extends BaseAdapter {
    private String str;
    private Context context;
    private LayoutInflater inflate;
    private List<CourseBean> courses = new ArrayList<>();

    public ClassInfoAdapter(Context context) {
        this.context = context;
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        str = context.getString(R.string.order_course);

    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public CourseBean getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflate.inflate(R.layout.search_result_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            AutoUtils.auto(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CourseBean item = getItem(position);
        String courseName = String.format(str, item.getOrder(), item.getCourseName());
        viewHolder.courseName1.setText(courseName);
        viewHolder.courseName.setText(courseName);
        if (getItem(position).getLearnStatus() == 0) {//未通过
            viewHolder.status.setText(R.string.not_passed_tips);
            viewHolder.passStatusLayout.getDelegate().setBackgroundColor(context.getResources().getColor(R.color.not_pass_red));//设置状态文字颜色
            viewHolder.passStatusLayout.setVisibility(View.VISIBLE);
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.not_pass_red_text));
            viewHolder.passTime.setVisibility(View.GONE);
            viewHolder.courseName1.setVisibility(View.VISIBLE);
            viewHolder.courseName.setVisibility(View.GONE);
        } else if (getItem(position).getLearnStatus() == 1) {//已通过
            viewHolder.passStatusLayout.setVisibility(View.VISIBLE);
            viewHolder.status.setText(R.string.passed);
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.pass_green_text));

            viewHolder.passTime.setVisibility(View.VISIBLE);
            viewHolder.passStatusLayout.getDelegate().setBackgroundColor(context.getResources().getColor(R.color.pass_green));
            viewHolder.passStatusLayout.setVisibility(View.VISIBLE);
            viewHolder.courseName1.setVisibility(View.GONE);
            viewHolder.courseName.setVisibility(View.VISIBLE);
        } else {
            viewHolder.courseName.setVisibility(View.GONE);
            viewHolder.courseName1.setVisibility(View.VISIBLE);
            viewHolder.passTime.setVisibility(View.GONE);
            viewHolder.passStatusLayout.setVisibility(View.GONE);
        }

        viewHolder.passTime.setText(context.getString(R.string.pass_time1) + TimeUtils.parseTimeToYM(Long.valueOf(getItem(position).getPassedDate())));
        return convertView;
    }

    public void setList(List<CourseBean> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    public void addList(List dataList) {
        this.courses.addAll(dataList);
        notifyDataSetChanged();
    }

    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.pass_status_layout)
        RoundRelativeLayout passStatusLayout;
        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.course_name1)
        TextView courseName1;
        @BindView(R.id.pass_time)
        TextView passTime;
        @BindView(R.id.pass_status)
        TextView status;

    }
}
