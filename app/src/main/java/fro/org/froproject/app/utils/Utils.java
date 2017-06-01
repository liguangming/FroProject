package fro.org.froproject.app.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import fro.org.froproject.app.Constants;

/**
 * Created by Lgm on 2017/6/1 0001.
 */

public class Utils {
    /**
     * 加密密码
     */
    public static String encodePassword(String source) {
        System.out.println("加密前文字：" + source);
        byte[] data = source.getBytes();
        try {
            byte[] encodedData = RSAUtils.encryptByPublicKey(data, Constants.key);
            String base64encode = Base64Utils.encode(encodedData);
            System.out.println("加密后文字：" + base64encode);
            return base64encode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 隐藏输入法
     * */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
