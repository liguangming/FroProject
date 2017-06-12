package fro.org.froproject.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import fro.org.froproject.R;
import fro.org.froproject.mvp.model.entity.HistoryClassBean;
import fro.org.froproject.mvp.ui.holder.HistoryClassHolder;

/**
 * Created by Lgm on 2017/6/12 0012.
 */

public class HistoryClassAdapter extends DefaultAdapter<HistoryClassBean> {

    public HistoryClassAdapter(List<HistoryClassBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder getHolder(View v, int viewType) {
        return new HistoryClassHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.history_list_item;
    }

    @Override
    public void onBindViewHolder(BaseHolder<HistoryClassBean> holder, int position) {
        super.onBindViewHolder(holder, position);
        RecyclerView.ViewHolder itemViewHolder = holder;
        ViewGroup.LayoutParams layoutParams = itemViewHolder.itemView.getLayoutParams();
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
    }
}
