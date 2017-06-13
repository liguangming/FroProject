package fro.org.froproject.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import org.fro.common.widgets.tab.roundtextview.RoundRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fro.org.froproject.R;
import fro.org.froproject.mvp.model.entity.CourseBean;

/**
 * Created by Lgm on 2017/6/13 0013.
 */

public class CourseNotPassAdapter extends BaseAdapter {


    private Context context;
    private LayoutInflater Inflate;
    private List<CourseBean> courseBeanList = new ArrayList<>();

    public CourseNotPassAdapter(Context context) {
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
            convertView = Inflate.inflate(R.layout.course_not_pass_item, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
            AutoUtils.auto(convertView);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }
        myViewHolder.courseName.setText(context.getString(R.string.order_left) + getItem(position).getOrder() + context.getString(R.string.order_right) + "  " + getItem(position).getCourseName());
        if (getItem(position).getLearnStatus() == -1) {
            myViewHolder.statusLayout.setVisibility(View.GONE);
        } else {
            myViewHolder.statusLayout.setVisibility(View.VISIBLE);
            myViewHolder.statusLayout.getDelegate().setBackgroundColor(context.getResources().getColor(R.color.not_pass_red));
            myViewHolder.statusText.setText(R.string.not_passed_tips);
            myViewHolder.statusText.setTextColor(context.getResources().getColor(R.color.not_pass_red_text));
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
        public MyViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @BindView(R.id.course_name)
        TextView courseName;
        @BindView(R.id.status_layout)
        RoundRelativeLayout statusLayout;
        @BindView(R.id.status_text)
        TextView statusText;

    }
}
