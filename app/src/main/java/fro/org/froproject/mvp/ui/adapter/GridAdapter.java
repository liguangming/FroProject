package fro.org.froproject.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import fro.org.froproject.R;
import fro.org.froproject.mvp.ui.holder.GridHolder;


/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class GridAdapter extends DefaultAdapter {

    public GridAdapter(List infos) {
        super(infos);
    }

    @Override
    public BaseHolder getHolder(View v, int viewType) {
        return new GridHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.main_grid_item;
    }

}
