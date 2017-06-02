package fro.org.froproject.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import fro.org.froproject.R;
import fro.org.froproject.mvp.ui.holder.CommonActivityHolder;


/**
 * Created by Lgm on 2017/6/2 0002.
 */

public class CommonActivityAdapter<T> extends DefaultAdapter {

    public CommonActivityAdapter(List<T> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<T> getHolder(View v, int viewType) {
        return new CommonActivityHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_common_activity_item;
    }

}