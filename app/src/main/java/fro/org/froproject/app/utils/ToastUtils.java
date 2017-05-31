package fro.org.froproject.app.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import fro.org.froproject.R;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ToastUtils {

    private static final String TAG = "ToastUtil";

    private static final boolean blnCertainToast = false;

    private static WeakReference<Toast> sToast;
    private static TextView mTextView;

    /**
     * @param context
     * @param text
     * @param duration
     * @param gravity
     */
    private static void show(Context context, String text, int duration, int gravity) {
        if (context != null && !TextUtils.isEmpty(text)) {
            Toast toast;
            View toastRoot = LayoutInflater.from(context).inflate(R.layout.toast, null);
            mTextView = (TextView) toastRoot.findViewById(R.id.toast_text);
            mTextView.setText(text);
            if (sToast == null || (toast = sToast.get()) == null || toast.getView() == null) {
                toast = new Toast(context);
                toast.setView(toastRoot);
                sToast = new WeakReference<>(toast);
            } else {
                if (sToast.get() != null)
                    sToast.get().cancel();
                toast = new Toast(context);
                toast.setView(toastRoot);
                sToast = new WeakReference<>(toast);
            }
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            int height = wm.getDefaultDisplay().getHeight();
            toast.setGravity(gravity, 0, height / 8);
            toast.setDuration(duration);
            toast.show();
        }
    }

    /**
     * Duration:LENGTH_SHORT，Gravity: BOTTOM
     *
     * @param context
     * @param text
     */
    public static void show2Center(Context context, String text) {
        show2Center(context, text, Toast.LENGTH_SHORT);
    }

    public static void show2Center(Context context, int resId) {
        show2Center(context, context.getResources().getText(resId).toString(), Toast.LENGTH_SHORT);
    }

    public static void show2CenterTemp(Context context, int resId) {
        if (blnCertainToast) {
            show2Center(context, context.getResources().getText(resId).toString(), Toast.LENGTH_SHORT);
        }
    }

    public static void show2Center(Context context, int resId, int duration) {
        show2Center(context, context.getResources().getText(resId).toString(), duration);
    }

    /**
     * @param context
     * @param text
     * @param duration
     */
    public static void show2Center(Context context, String text, int duration) {
        show(context, text, duration, Gravity.CENTER);
    }

    public static void show2Bottom(Context context, String text, int duration) {
        show(context, text, duration, Gravity.BOTTOM);
    }

    public static void show2Bottom(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void show2Bottom(Context context, int resId) {
        show(context, context.getString(resId), Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    /**
     * Duration:LENGTH_SHORT，Gravity: TOP
     *
     * @param context
     * @param text
     */
    public static void show2Top(Context context, String text) {
        show2Top(context, text, Toast.LENGTH_SHORT);
    }

    public static void show2Top(Context context, int textResid) {
        show2Top(context, context.getString(textResid), Toast.LENGTH_SHORT);
    }

    /**
     * Gravity: TOP
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void show2Top(Context context, String text, int duration) {
        show(context, text, duration, Gravity.TOP);
    }

    /**
     * Duration:LENGTH_SHORT
     *
     * @param context
     * @param text
     * @param gravity
     */
    public static void show(Context context, String text, int gravity) {
        show(context, text, Toast.LENGTH_SHORT, gravity);
    }

    /**
     * 显示Toast（显示到底部，时间为 Toast.LENGTH_SHORT）
     *
     * @param context 上下文
     * @param text    文本内容
     */
    public static void show(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    /**
     * 显示Toast（显示到底部，时间为 Toast.LENGTH_SHORT）
     *
     * @param context 上下文
     * @param resId   文本资源Id
     */
    public static void show(Context context, int resId) {
        show(context, context.getString(resId), Toast.LENGTH_SHORT, Gravity.BOTTOM);
    }

    public static void cancel() {
        Toast toast;
        if (sToast != null && (toast = sToast.get()) != null) {
            toast.cancel();
        }
    }

    public static void showNullToast(Context context, String content) {
        show(context, content + context.getString(R.string.not_null), Toast.LENGTH_SHORT, Gravity.CENTER);
    }
}
