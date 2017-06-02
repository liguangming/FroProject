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
     * 验证码
     */
    public static final String REGEX_AUTH_CODE = "^[0-9]{4,8}$";

    /**
     * 验证码校验
     */
    public static boolean authCodeValible(String authCode) {
        if (TextUtils.isEmpty(authCode))
            return false;
        Pattern p = Pattern.compile(REGEX_PASSWORD);
        Matcher m = p.matcher(authCode);
        return m.matches();
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
    public static boolean passwordRight(String password) {
        if (TextUtils.isEmpty(password))
            return false;
        Pattern p = Pattern.compile(REGEX_PASSWORD);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * 是否是手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if (TextUtils.isEmpty(mobiles))
            return false;
        Pattern p = Pattern.compile("^(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}