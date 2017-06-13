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
 * Created by Administrator on 2017/4/21 0021.
 */

public class CoursePassAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater Inflate;
    private List<CourseBean> courseBeanList = new ArrayList<>();

    public CoursePassAdapter(Context context) {
        Inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;

    }

    @Override
    public int getCount() {
        return courseBeanList.size();
    }

    @Override
    public CourseBean getItem(int position) {
        return courseBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = Inflate.inflate(R.layout.course_passed_item, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
            AutoUtils.auto(convertView);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.courseName.setText(context.getString(R.string.order_left) + getItem(position).getOrder() + context.getString(R.string.order_right) + "  " + getItem(position).getCourseName());
        myViewHolder.passTime.setText(context.getString(R.string.pass_time1) + TimeUtils.parseTimeToYM2(Long.parseLong(getItem(position).getPassedDate())));
        if (getItem(position).getLearnStatus() == 0) {
            myViewHolder.passTime.setVisibility(View.VISIBLE);
            myViewHolder.passStatusLayout.setVisibility(View.VISIBLE);
            myViewHolder.passStatusLayout.getDelegate().setBackgroundColor(context.getResources().getColor(R.color.not_pass_red));
            myViewHolder.status.setText(R.string.not_passed_tips);
            myViewHolder.status.setTextColor(context.getResources().getColor(R.color.not_pass_red_text));
        } else if (getItem(position).getLearnStatus() == 1) {
            myViewHolder.status.setTextColor(context.getResources().getColor(R.color.pass_green_text));
            myViewHolder.passStatusLayout.getDelegate().setBackgroundColor(context.getResources().getColor(R.color.pass_green));
            myViewHolder.status.setText(R.string.passed);
            myViewHolder.passStatusLayout.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.passTime.setVisibility(View.VISIBLE);
            myViewHolder.passStatusLayout.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setList(List<CourseBean> courseBeanList) {
        this.courseBeanList = courseBeanList;
        notifyDataSetChanged();
    }

    public void addList(List<CourseBean> courseBeanList) {
        this.courseBeanList.addAll(courseBeanList);
        notifyDataSetChanged();
    }
    class MyViewHolder {
        @BindView(R.id.status_text)
        public TextView status;
        @BindView(R.id.status_layout)
        public RoundRelativeLayout passStatusLayout;
        public MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.pass_time)
        TextView passTime;
    }
}
