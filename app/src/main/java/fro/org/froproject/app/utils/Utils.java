package fro.org.froproject.app.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.andview.refreshview.XRefreshView;
import com.jess.arms.integration.AppManager;

import org.fro.common.widgets.locationview.entity.ProvinceData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
     */
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 获取地区数据
     */
    public static ArrayList<ProvinceData> getLoctionData(Context context) {
        StringBuffer sb = new StringBuffer();
        try {
            // 字节流
            InputStream is = context.getAssets().open("region.json");// 打开assets文件夹中的文件
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");// 字符流，编码要与指定字节流一样啊
            BufferedReader bfr = new BufferedReader(isr);
            // bfr.readLine();//读取文件中的一行数据
            String in;
            while ((in = bfr.readLine()) != null) {
                sb.append(new StringBuffer(in));
            }
            is.close();
            isr.close();
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<ProvinceData> array_Province = (ArrayList<ProvinceData>) com.alibaba.fastjson.JSONObject.parseArray(sb.toString(), ProvinceData.class);
        return array_Province;
    }

    public static void initFreshView(XRefreshView refreshView) {
        refreshView.setPullLoadEnable(true);
        refreshView.setPullRefreshEnable(false);
        //当下拉刷新被禁用时，调用这个方法并传入false可以不让头部被下拉
        refreshView.setMoveHeadWhenDisablePullRefresh(false);
        refreshView.setMoveFootWhenDisablePullLoadMore(false);
        refreshView.enablePullUpWhenLoadCompleted(false);
        // 设置时候可以自动刷新
        refreshView.setAutoRefresh(false);
    }

}
