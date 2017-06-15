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
 * Created by Administrator on 2017/4/24 0024.
 */

public class SearchResultAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflate;
    private List<CourseBean> courseList = new ArrayList<>();

    public SearchResultAdapter(Context context) {
        this.context = context;
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public CourseBean getItem(int position) {
        return courseList.get(position);
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
        viewHolder.courseName.setText(context.getString(R.string.order_left) + item.getOrder() + context.getString(R.string.order_right) + "  " + item.getCourseName());
        viewHolder.courseName1.setText(context.getString(R.string.order_left) + item.getOrder() + context.getString(R.string.order_right) + "  " + item.getCourseName());
        viewHolder.passTime.setText(context.getString(R.string.pass_time1) + TimeUtils.parseTimeToYM2(Long.parseLong(item.getPassedDate())));
        if (item.getLearnStatus() == 0) {//未通过
            viewHolder.courseName.setVisibility(View.GONE);
            viewHolder.courseName1.setVisibility(View.VISIBLE);
            viewHolder.statusLayout.setVisibility(View.VISIBLE);
            viewHolder.statusLayout.getDelegate().setBackgroundColor(context.getResources().getColor(R.color.not_pass_red));
            viewHolder.status.setText(R.string.not_passed_tips);
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.not_pass_red_text));
            viewHolder.passTime.setVisibility(View.GONE);
        } else if (item.getLearnStatus() == 1) {
            viewHolder.courseName1.setVisibility(View.GONE);
            viewHolder.courseName.setVisibility(View.VISIBLE);
            viewHolder.status.setTextColor(context.getResources().getColor(R.color.pass_green_text));
            viewHolder.statusLayout.getDelegate().setBackgroundColor(context.getResources().getColor(R.color.pass_green));
            viewHolder.status.setText(R.string.passed);
            viewHolder.statusLayout.setVisibility(View.VISIBLE);
            viewHolder.passTime.setVisibility(View.VISIBLE);
        } else {
            viewHolder.courseName1.setVisibility(View.VISIBLE);
            viewHolder.courseName.setVisibility(View.GONE);
            viewHolder.passTime.setVisibility(View.GONE);
            viewHolder.statusLayout.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setList(List<CourseBean> courseList) {
        this.courseList = courseList;
        notifyDataSetChanged();
    }

    public void addList(List<CourseBean> courseList) {
        this.courseList.addAll(courseList);
        notifyDataSetChanged();
    }

    class ViewHolder {
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.course_name1)
        TextView courseName1;
        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.pass_time)
        TextView passTime;
        @BindView(R.id.pass_status)
        TextView status;
        @BindView(R.id.pass_status_layout)
        RoundRelativeLayout statusLayout;

    }
}
