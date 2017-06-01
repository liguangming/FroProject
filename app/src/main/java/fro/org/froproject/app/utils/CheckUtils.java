package fro.org.froproject.app.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fro.org.froproject.R;

/**
 * Created by Administrator on 2017/5/31.
 */

public class CheckUtils {

    /**
     * 验证码校验
     */
    public static boolean authCodeValible(Context context, String authCode) {
        if (authCode.length() < 4) {
            ToastUtils.show(context, "请输入正确验证码");
            return false;
        }
        return true;
    }

    /**
     * 密码是否是6-16位
     */
    public static final String REGEX_PASSWORD = "^[0-9A-Za-z]{6,16}$";

    /**
     * 判断密码是否符合规定
     *
     * @param password
     * @return
     */
    public static boolean passwordRight(Context context,String password) {
        if (TextUtils.isEmpty(password))
            return false;
        Pattern p = Pattern.compile(REGEX_PASSWORD);
        Matcher m = p.matcher(password);
        if (m.matches()) {
            return true;
        } else {
            ToastUtils.show(context, "请输入6-16位密码");
            return false;
        }
    }

    /**
     * 是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(Context context, String mobiles) {
        if (TextUtils.isEmpty(mobiles))
            return false;
        Pattern p = Pattern.compile("^(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        if (m.matches()) {
            return true;
        } else {
            ToastUtils.show(context, R.string.phone_num_tips);
            return false;
        }
    }
}