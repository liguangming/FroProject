package fro.org.froproject.mvp.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindArray;
import butterknife.BindView;
import fro.org.froproject.R;
import io.reactivex.Observable;

/**
 * Created by Lgm on 2017/6/7 0007.
 */

public class GridHolder extends BaseHolder {
    @BindView(R.id.name_chinese)
    TextView nameChina;
    @BindView(R.id.name_english)
    TextView nameEnglish;
    @BindView(R.id.image)
    ImageView imageView;

    @BindArray(R.array.name_english)
    String[] englishName;
    private int[] drawId = {R.mipmap.ic_bj, R.mipmap.ic_kc, R.mipmap.ic_fw, R.mipmap.ic_zh};

    public GridHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(Object data, int position) {
        imageView.setImageResource(drawId[position]);
        nameEnglish.setText(englishName[position]);
        Observable.just(data)
                .subscribe(s -> nameChina.setText(data.toString()));
    }
}
