package fro.org.froproject.mvp.ui.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLinearLayout;

import fro.org.froproject.R;


/**
 * 自定义View
 * Created by Administrator on 2017/4/21 0021.
 */

public class SearchView extends AutoLinearLayout {
    private LinearLayout layout;
    private EditText editText;
    private ImageView rightSearchIcon;

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_view, this);
        layout = (LinearLayout) view.findViewById(R.id.layout);
        rightSearchIcon = (ImageView) view.findViewById(R.id.search_img_right);
        editText = (EditText) view.findViewById(R.id.search_edit);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                layout.setVisibility(s.toString().isEmpty() ? VISIBLE : GONE);
            }
        });

    }

    public void setSerach(TextView.OnEditorActionListener onEditorActionListener) {
        if (onEditorActionListener != null)
            editText.setOnEditorActionListener(onEditorActionListener);
    }

    public void setRightImageVisible(boolean visible) {
        rightSearchIcon.setVisibility(visible ? VISIBLE : GONE);
        layout.setVisibility(visible ? GONE : VISIBLE);
    }

    public void setEditText(String str) {
        editText.setText(str);
    }

    public String getEditTextStr() {
        return editText.getText().toString();
    }

    public void setEditTextColor(int white) {
        editText.setTextColor(getResources().getColor(white));
    }
}
