package fro.org.froproject.mvp.ui.holder;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import fro.org.froproject.R;
import io.reactivex.Observable;

/**
 * Created by Lgm on 2017/6/2 0002.
 */

public class CommonActivityHolder extends BaseHolder {

    @Nullable
    @BindView(R.id.name)
    TextView mName;

    public CommonActivityHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        Observable.just(1)
                .subscribe(s -> mName.setText(s));
    }
}
