package fro.org.froproject.mvp.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

import fro.org.froproject.app.utils.StatusBarUtil;
import fro.org.froproject.app.utils.Utils;


/**
 * desc : 自定义头部view
 * auther : lgm
 * date :  16/6/20.
 */
public class HeadView extends RelativeLayout implements View.OnClickListener {
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    private Context context;

    private String mTitleStr, mSubtitleStr, mLeftStr, mRightStr;

    private int mTitleColor, mSubtitleColor;

    private Drawable mDrawableLeft, mDrawableRight;

    private TextView mTitle, mSubtitle;

    private TextView mTextViewLeft, mTextViewRight;

    private ImageView mImageViewLeft, mImageViewRight;

    private OnClickListener mTitleListener, mLeftListener, mRightListener;

    public HeadView(Context context) {
        this(context, null);
    }

    public HeadView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        init(context);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, org.fro.common.R.styleable.CDHeadView);
            mTitleStr = a.getString(org.fro.common.R.styleable.CDHeadView_cdTitle);
            mSubtitleStr = a.getString(org.fro.common.R.styleable.CDHeadView_cdSubtitle);
            mTitleColor = a.getColor(org.fro.common.R.styleable.CDHeadView_cdTitleColor, -1);
            mSubtitleColor = a.getColor(org.fro.common.R.styleable.CDHeadView_cdSubtitleColor, -1);
            mDrawableLeft = a.getDrawable(org.fro.common.R.styleable.CDHeadView_cdDrawableLeft);
            mDrawableRight = a.getDrawable(org.fro.common.R.styleable.CDHeadView_cdDrawableRight);
            mLeftStr = a.getString(org.fro.common.R.styleable.CDHeadView_cdTextLeft);
            mRightStr = a.getString(org.fro.common.R.styleable.CDHeadView_cdTextRight);
            a.recycle();
        }
    }
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoRelativeLayout.LayoutParams(getContext(), attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private void init(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(org.fro.common.R.layout.cd_head_view, this, true);
        view.findViewById(org.fro.common.R.id.headView).setBackgroundResource(org.fro.common.R.color.cd_transparent);
        setBackgroundResource(org.fro.common.R.color.cd_headview_bg);

        mTitle = (TextView) view.findViewById(org.fro.common.R.id.title);
        mSubtitle = (TextView) view.findViewById(org.fro.common.R.id.subtitle);

        mImageViewLeft = (ImageView) view.findViewById(org.fro.common.R.id.leftImage);
        mImageViewRight = (ImageView) view.findViewById(org.fro.common.R.id.rightImage);

        mTextViewLeft = (TextView) view.findViewById(org.fro.common.R.id.leftText);
        mTextViewRight = (TextView) view.findViewById(org.fro.common.R.id.rightText);

        if (mTitleStr != null) {
            mTitle.setText(mTitleStr);
        }

        if (mSubtitleStr != null) {
            mSubtitle.setText(mSubtitleStr);
            mSubtitle.setVisibility(VISIBLE);
        }

        if (mTitleColor != -1) {
            mTitle.setTextColor(mTitleColor);
        }

        if (mSubtitleColor != -1) {
            mSubtitle.setTextColor(mSubtitleColor);
        }

        if (mDrawableLeft != null) {
            mImageViewLeft.setImageDrawable(mDrawableLeft);
        }

        if (mDrawableRight != null) {
            mImageViewRight.setImageDrawable(mDrawableRight);
            mImageViewRight.setVisibility(VISIBLE);
        }

        if (mLeftStr != null) {
            mTextViewLeft.setText(mLeftStr);
            mTextViewLeft.setVisibility(VISIBLE);
            mImageViewLeft.setVisibility(GONE);
        }

        if (mRightStr != null) {
            mTextViewRight.setText(mRightStr);
            mTextViewRight.setVisibility(VISIBLE);
            mImageViewRight.setVisibility(GONE);
        }

        mTitle.setOnClickListener(this);
        mImageViewLeft.setOnClickListener(this);
        mImageViewRight.setOnClickListener(this);
        mTextViewLeft.setOnClickListener(this);
        mTextViewRight.setOnClickListener(this);
    }

    public void setTitleStr(String TitleStr) {
        mTitle.setText(TitleStr);
    }

    public void setTitleStr(@StringRes int resId) {
        mTitle.setText(resId);
    }

    public void setSubtitleStr(String SubtitleStr) {
        if (TextUtils.isEmpty(SubtitleStr)) {
            mSubtitle.setVisibility(GONE);
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        } else {
            mSubtitle.setText(SubtitleStr);
            mSubtitle.setVisibility(VISIBLE);
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }
    }

    public void setSubtitleStr(@StringRes int resId) {
        if (resId <= 0) {
            mSubtitle.setVisibility(GONE);
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        } else {
            mSubtitle.setText(resId);
            mSubtitle.setVisibility(VISIBLE);
            mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        }
    }

    public void setTitleColor(int TitleColor) {
        mTitle.setTextColor(TitleColor);
    }

    public void setSubtitleColor(int mSubtitleColor) {
        mSubtitle.setTextColor(mSubtitleColor);
    }

    public void setLeftImage(@DrawableRes int resId) {
        if (resId <= 0) {
            mImageViewLeft.setVisibility(GONE);
        } else {
            mImageViewLeft.setVisibility(VISIBLE);
            mTextViewLeft.setVisibility(GONE);
        }
        mImageViewLeft.setImageResource(resId);
    }

    public void setLeftImage(Drawable DrawableLeft) {
        if (DrawableLeft == null) {
            mImageViewLeft.setVisibility(GONE);
        } else {
            mImageViewLeft.setVisibility(VISIBLE);
            mTextViewLeft.setVisibility(GONE);
        }
        mImageViewLeft.setImageDrawable(DrawableLeft);
    }

    public void setLeftText(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            mTextViewLeft.setVisibility(GONE);
        } else {
            mTextViewLeft.setVisibility(VISIBLE);
            mImageViewLeft.setVisibility(GONE);
        }
        mTextViewLeft.setText(text);
    }

    public void setLeftText(@StringRes int resId) {
        if (resId <= 0) {
            mTextViewLeft.setVisibility(GONE);
        } else {
            mTextViewLeft.setVisibility(VISIBLE);
            mImageViewLeft.setVisibility(GONE);
        }
        mTextViewLeft.setText(resId);
    }

    public void setRightImage(@DrawableRes int resId) {
        if (resId <= 0) {
            mImageViewRight.setVisibility(GONE);
        } else {
            mImageViewRight.setVisibility(VISIBLE);
            mTextViewRight.setVisibility(GONE);
        }
        mImageViewRight.setImageResource(resId);
    }


    public void setRightImage(Drawable DrawableRight) {
        if (DrawableRight == null) {
            mImageViewRight.setVisibility(GONE);
        } else {
            mImageViewRight.setVisibility(VISIBLE);
            mTextViewRight.setVisibility(GONE);
        }
        mImageViewRight.setImageDrawable(DrawableRight);
    }

    public void setRightText(CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            mTextViewRight.setVisibility(GONE);
        } else {
            mTextViewRight.setVisibility(VISIBLE);
            mImageViewRight.setVisibility(GONE);
        }
        mTextViewRight.setText(text);
    }

    public void setRightText(@StringRes int resId) {
        if (resId <= 0) {
            mTextViewRight.setVisibility(GONE);
        } else {
            mTextViewRight.setVisibility(VISIBLE);
            mImageViewRight.setVisibility(GONE);
        }
        mTextViewRight.setText(resId);
    }

    public void setTitleListener(OnClickListener TitleListener) {
        this.mTitleListener = TitleListener;
    }

    public void setLeftListener(OnClickListener LeftListener) {
        this.mLeftListener = LeftListener;
    }

    public void setRightListener(OnClickListener RightListener) {
        this.mRightListener = RightListener;
    }

    public ImageView getRightImageView() {
        return mImageViewRight;
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getSubtitle() {
        return mSubtitle;
    }

    public ImageView getLeftImageView() {
        return mImageViewLeft;
    }

    public TextView getLeftTextView() {
        return mTextViewLeft;
    }

    public TextView getRightTextView() {
        return mTextViewRight;
    }

    /**
     * 配置HeadView的背景色，并对应配置statusBar的颜色
     * NOTE: 如果不希望配置statusBar的颜色，请直接调用setBackgroundColor
     *
     * @param activity 包含HeadView的Activity
     * @param color    颜色值
     */
    public void setBgColor(Activity activity, @ColorInt int color) {
        super.setBackgroundColor(color);
        StatusBarUtil.setColor(activity, color, 0);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == org.fro.common.R.id.title) {
            if (mTitleListener != null) {
                mTitleListener.onClick(v);
            }
        } else if (v.getId() == org.fro.common.R.id.leftImage || v.getId() == org.fro.common.R.id.leftText) {
            if (mLeftListener != null) {
                mLeftListener.onClick(v);
            } else {
                try {
                    Utils.hideKeyboard(context, v);
                } catch (Exception e) {

                } finally {
                    if (context instanceof BaseActivity) {
                        ((BaseActivity) context).finish();
//                    } else if (context instanceof BaseFragment) {
//                        ((BaseFragment) context).finish();
                    } else if (context instanceof Activity) {
                        ((Activity) context).finish();
                    }

                }
            }
        } else if (v.getId() == org.fro.common.R.id.rightImage || v.getId() == org.fro.common.R.id.rightText) {
            if (mRightListener != null) {
                mRightListener.onClick(v);
            }
        }
    }
}
