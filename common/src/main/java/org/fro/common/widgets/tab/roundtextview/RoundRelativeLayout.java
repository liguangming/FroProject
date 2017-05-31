package org.fro.common.widgets.tab.roundtextview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoLayoutHelper;

/**
 * 用于需要圆角矩形框背景的RelativeLayout的情况,减少直接使用RelativeLayout时引入的shape资源文件
 */
public class RoundRelativeLayout extends RelativeLayout {
    private RoundViewDelegate delegate;
    private final AutoLayoutHelper mHelper = new AutoLayoutHelper(this);
    public RoundRelativeLayout(Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        delegate = new RoundViewDelegate(this, context, attrs);
    }

    /**
     * use delegate to set attr
     */
    public RoundViewDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            mHelper.adjustChildren();
        }
        if (delegate.isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (delegate.isRadiusHalfHeight()) {
            delegate.setCornerRadius(getHeight() / 2);
        } else {
            delegate.setBgSelector();
        }
    }
    @Override
    public RoundRelativeLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new AutoRelativeLayout.LayoutParams(getContext(), attrs);
    }

}
