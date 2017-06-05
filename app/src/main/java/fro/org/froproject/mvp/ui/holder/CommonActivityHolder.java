package fro.org.froproject.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import fro.org.froproject.R;
import fro.org.froproject.mvp.model.entity.OrgBean;
import io.reactivex.Observable;

/**
 * Created by Lgm on 2017/6/2 0002.
 */

public class CommonActivityHolder extends BaseHolder<OrgBean> {

    @Nullable
    @BindView(R.id.name)
    TextView mName;
    @Nullable
    @BindView(R.id.image_select)
    ImageView image;

    public CommonActivityHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(OrgBean data, int position) {
        if (data.isSelect()) {
            image.setVisibility(View.VISIBLE);
        } else {
            image.setVisibility(View.GONE);
        }
        Observable.just(data.getName())
                .subscribe(s -> mName.setText(s));
    }
}
