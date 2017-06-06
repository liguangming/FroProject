package fro.org.froproject.mvp.ui.view;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import fro.org.froproject.R;
import fro.org.froproject.app.utils.Utils;


/**
 * 底部弹窗列表控件
 */
public class PopupListView extends PopupWindow {
    private Activity activity;
    private PopupWindow popupWindow;
    private ViewGroup.LayoutParams paramsLine;
    private ViewGroup.LayoutParams paramsItem;
    private WindowManager.LayoutParams paramsWindow;

    public PopupListView(Activity activity) {
        super(activity);
        this.activity = activity;
        paramsLine = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.dip2px(activity, 0.5f));
        paramsItem = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.dip2px(activity, 45f));
        paramsWindow = activity.getWindow().getAttributes();
    }

    public void show(String[] items, ICallBack callback) {
        show(items, null, callback);
    }

    public void show(String[] items, View headView, final ICallBack callback) {
        LinearLayout lin = new LinearLayout(activity);
        lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        lin.setOrientation(LinearLayout.VERTICAL);
        lin.setGravity(Gravity.BOTTOM);
        if (headView != null) {
            lin.addView(headView);
        }
        lin.addView(createLine());
        for (int i = 0; i < items.length; i++) {
            TextView textView = createItem(items[i], new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.performClick((Integer) v.getTag());
                    dismiss();
                }
            });
            textView.setTag(i);
            lin.addView(textView);
            lin.addView(createLine());
        }
        lin.addView(createEmptyView());
        lin.addView(createLine());
        TextView cancel = createItem("取消", null);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        lin.addView(cancel);
        lin.addView(createLine());
        lin.setBackgroundColor(Color.TRANSPARENT);
        popupWindow = new PopupWindow(lin, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paramsWindow.alpha = 0.6f;
        activity.getWindow().setAttributes(paramsWindow);
        popupWindow.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        popupWindow.setAnimationStyle(R.style.FLCAnimDialog);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                paramsWindow.alpha = 1f;
                activity.getWindow().setAttributes(paramsWindow);
            }
        });
        popupWindow.update();
    }

    private TextView createItem(String str, View.OnClickListener listener) {
        TextView textView = new TextView(activity);
        textView.setLayoutParams(paramsItem);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundResource(R.drawable.selector_item_white);
        textView.setTextSize(15);
        ColorStateList stateList = activity.getResources().getColorStateList(R.color.selector_item_text_color);
        textView.setTextColor(stateList);
        textView.setGravity(Gravity.CENTER);
        textView.setText(str);
        textView.setOnClickListener(listener);
        return textView;
    }

    private View createLine() {
        View view = new View(activity);
        view.setLayoutParams(paramsLine);
        view.setBackgroundResource(R.color.line_color);
        return view;
    }

    private View createEmptyView() {
        View view = new View(activity);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UiUtils.dip2px(activity, 5f)));
        view.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }

    public interface ICallBack {
        void performClick(int position);
    }

    public void dismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }
}
