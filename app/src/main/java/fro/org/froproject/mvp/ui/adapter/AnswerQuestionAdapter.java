package fro.org.froproject.mvp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import fro.org.froproject.R;
import fro.org.froproject.mvp.model.entity.ExerciseAnswerBean;

/**
 * 答题界面adapter
 * Created by Administrator on 2017/4/18 0018.
 */

public class AnswerQuestionAdapter extends BaseAdapter {
//    private DisplayImageOptions myDisplayImageOptions;
    private List<ExerciseAnswerBean> arrayList = new ArrayList<>();
    private Context context;
    private LayoutInflater inflate;

    public AnswerQuestionAdapter(Context context) {
        this.context = context;
        inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        myDisplayImageOptions = new DisplayImageOptions.Builder()
//                .cacheOnDisk(false)
//                .displayer(new FlexibleRoundedBitmapDisplayer(10, CORNER_BOTTOM_RIGHT))
//                .considerExifParams(true)
//                .build();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public ExerciseAnswerBean getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflate.inflate(R.layout.answer_question_item, parent, false);
            convertView.setTag(viewHolder);
            AutoUtils.autoSize(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.question = (TextView) convertView.findViewById(R.id.answer);
        viewHolder.image = (ImageView) convertView.findViewById(R.id.image_select);
        viewHolder.mRoundRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.list_item);
        viewHolder.question.setText(arrayList.get(position).getContent());
        String uri = "drawable://" + R.drawable.ic_answer_select;
        if (arrayList.get(position).isSelected()) {//选中
//            ImageLoader.getInstance().displayImage(uri.toString(), viewHolder.image, myDisplayImageOptions);
            viewHolder.image.setVisibility(View.VISIBLE);
            viewHolder.question.setTextColor(context.getResources().getColor(R.color.text_select_color));
            viewHolder.mRoundRelativeLayout.setBackgroundResource(R.drawable.answer_quesion_item_select_bg);
        } else {
//            ImageLoader.getInstance().displayImage(uri.toString(), viewHolder.image, myDisplayImageOptions);
            viewHolder.mRoundRelativeLayout.setBackgroundResource(R.drawable.answer_quesion_item_normal_bg);
            viewHolder.question.setTextColor(context.getResources().getColor(R.color.text_normal_color));
          viewHolder.image.setVisibility(View.GONE);
        }
        return convertView;
    }

    public void setList(List<ExerciseAnswerBean> courseExerCises) {
        this.arrayList = courseExerCises;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        public TextView question;
        public ImageView image;
        public RelativeLayout mRoundRelativeLayout;
    }
}
