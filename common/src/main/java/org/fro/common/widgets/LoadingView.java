package org.fro.common.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.fro.common.R;


/**
 * Created by Administrator on 2017/5/6.
 */

public class LoadingView {
    private static volatile Dialog dlg;
    private static ImageView imageView;

    public static void showLoading(Context context) {
        try {
            if (context != null) {
                if (dlg == null) {
                    synchronized (LoadingView.class) {
                        if (dlg == null) {
                            dlg = new Dialog(context, R.style.Loading);
                            dlg.setCanceledOnTouchOutside(false);
                            dlg.setCancelable(false);
                            View view = View.inflate(context, R.layout.loading_progress_view, null);
                            AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView) view.findViewById(R.id.image)).getDrawable();
                            animationDrawable.start();
                            dlg.setContentView(view);
                        }
                    }
                }
                dlg.show();
            }
        } catch (Exception e) {
            Log.d("showLoadingException", "" + e);
        }
    }

    public static void showLoading(Context context, boolean isCancelable) {

        try {
            if (context != null) {
                dlg = new Dialog(context, R.style.Loading);
                dlg.setCanceledOnTouchOutside(false);
                dlg.setCancelable(isCancelable);
                View view = View.inflate(context, R.layout.loading_progress_view, null);
                AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView) view.findViewById(R.id.image)).getDrawable();
                animationDrawable.start();
                dlg.setContentView(view);
                dlg.show();
            }
        } catch (Exception e) {
            Log.d("isCancelableException", "" + e);
        }
    }

    public static Dialog markLoading(Context context, boolean isCancelable) {
        if (context != null) {
            Dialog dlg = new Dialog(context, R.style.Loading);
            dlg.setCanceledOnTouchOutside(false);
            dlg.setCancelable(isCancelable);
            View view = View.inflate(context, R.layout.loading_progress_view, null);
            AnimationDrawable animationDrawable = (AnimationDrawable) ((ImageView) view.findViewById(R.id.image)).getDrawable();
            animationDrawable.start();
            dlg.setContentView(view);
            return dlg;
        } else {
            return null;
        }
    }


    public static void dismissLoading() {
        try {
            if (dlg != null && dlg.isShowing()) {
                dlg.dismiss();
                dlg = null;
            }
        } catch (Exception e) {
            Log.d("DialogException", "" + e);
        }
    }

}
