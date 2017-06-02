package fro.org.froproject.mvp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.AutoRelativeLayout;

import fro.org.froproject.R;


/**
 * 个人信息itemView
 * Created by Administrator on 2017/4/20 0020.
 */

public class PersonalItemView extends AutoRelativeLayout {

    private boolean bottomLineVissible;
    private boolean topLineVissible;
    private String contentType;
    private boolean rightArrowVissible;
    private String rightText;
    private TextView rightTextView;
    private View topLineView;
    private View bottomLineView;
    private TextView contextTypeView;
    private String rightEditText;
    private EditText contextEditView;
    private View middleLineView;
    private boolean middleLineVissible;
    private boolean ImageStarVissible;
    private TextView starImage;
    private String hint;
    private int imageResourceId;
    private ImageView image;
    private boolean ImageLeftVissible;
    private boolean textViewVissible;
    private boolean editTextVissible;
    private ImageView arrowView;
    private int maxLength;//输入框的最大输入长度
    private int inputType;

    public PersonalItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        init(context);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PersonalItemView);
            bottomLineVissible = a.getBoolean(R.styleable.PersonalItemView_BottomLine, false);
            maxLength = a.getInt(R.styleable.PersonalItemView_maxLength, 99);
            topLineVissible = a.getBoolean(R.styleable.PersonalItemView_TopLine, false);
            middleLineVissible = a.getBoolean(R.styleable.PersonalItemView_middleLine, false);
            contentType = a.getString(R.styleable.PersonalItemView_TypeContent);
            hint = a.getString(R.styleable.PersonalItemView_hint);
            ImageStarVissible = a.getBoolean(R.styleable.PersonalItemView_ImageIco, false);
            inputType = a.getInt(R.styleable.PersonalItemView_inputType, -99);
            ImageLeftVissible = a.getBoolean(R.styleable.PersonalItemView_leftImageVisible, true);

            imageResourceId = a.getResourceId(R.styleable.PersonalItemView_ImageSrc, R.drawable.cd_arrow);
            rightArrowVissible = a.getBoolean(R.styleable.PersonalItemView_rightArrow, false);
            textViewVissible = a.getBoolean(R.styleable.PersonalItemView_textViewVisible, false);
            editTextVissible = a.getBoolean(R.styleable.PersonalItemView_EditTextVisible, true);

            rightText = a.getString(R.styleable.PersonalItemView_rightText);
            rightEditText = a.getString(R.styleable.PersonalItemView_rightEditText);
            a.recycle();
        }
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.personal_item_view, this);
        image = (ImageView) view.findViewById(R.id.image);
        image.setImageResource(imageResourceId);
        image.setVisibility(ImageLeftVissible ? VISIBLE : INVISIBLE);

        starImage = (TextView) view.findViewById(R.id.image_star);

        starImage.setVisibility(ImageStarVissible ? VISIBLE : GONE);
        rightTextView = (TextView) view.findViewById(R.id.textView);
        if (rightText != null) {
            rightTextView.setText(rightText);
        }
        rightTextView.setVisibility(textViewVissible ? VISIBLE : GONE);


        topLineView = view.findViewById(R.id.top_line);
        topLineView.setVisibility(topLineVissible ? VISIBLE : GONE);

        middleLineView = view.findViewById(R.id.middle_line);
        middleLineView.setVisibility(middleLineVissible ? VISIBLE : GONE);

        bottomLineView = view.findViewById(R.id.bottom_line);
        bottomLineView.setVisibility(bottomLineVissible ? VISIBLE : GONE);

        contextEditView = (EditText) view.findViewById(R.id.content);
        contextEditView.setText(rightEditText);
        contextEditView.setHint(hint);
        contextEditView.setVisibility(editTextVissible ? VISIBLE : GONE);

        contextTypeView = (TextView) view.findViewById(R.id.type_content);
        contextTypeView.setText(contentType);
        contextTypeView.setHintTextColor(getResources().getColor(R.color.hint_color));
        contextEditView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        arrowView = (ImageView) view.findViewById(R.id.arrow);
        arrowView.setVisibility(rightArrowVissible ? VISIBLE : GONE);

    }

    /**
     * 返回输入框内容
     */
    public String getEditText() {
        return contextEditView.getText().toString();
    }

    /**
     * 返回输入框内容
     */
    public void setEditText(String str) {
        contextEditView.setText(str);
    }

    /**
     * 返回文字框内容
     */
    public String getTextViewText() {
        return rightTextView.getText().toString();
    }

    /**
     * 设置文字显示内容
     */
    public void setTextViewText(String text) {

        rightTextView.setText(text);
    }

    public void setTextViewColor(int colorId) {
        rightTextView.setTextColor(getResources().getColor(colorId));
    }

    public void setNumInput() {
        contextEditView.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                return new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
            }
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_NUMBER;
            }
        });
    }
}
