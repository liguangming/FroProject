package fro.org.froproject.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import fro.org.froproject.R;
import fro.org.froproject.mvp.model.entity.ClassBean;
import fro.org.froproject.mvp.ui.holder.ClassListHolder;

/**
 * Created by Lgm on 2017/6/8 0008.
 */

public class ClassListAdapter extends DefaultAdapter<ClassBean> {

    public ClassListAdapter(List<ClassBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ClassBean> getHolder(View v, int viewType) {
        return new ClassListHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.class_list_item;
    }

    @Override
    public void onBindViewHolder(BaseHolder<ClassBean> holder, int position) {
        super.onBindViewHolder(holder, position);
        RecyclerView.ViewHolder itemViewHolder = holder;
        ViewGroup.LayoutParams layoutParams = itemViewHolder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
