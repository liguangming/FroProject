package org.fro.common.util;

import android.text.TextUtils;


import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class JSONUtils {

    static final String TAG = "JsonUtil";

    public static final String RESP_CODE = "resp_code";

    public static final String RESP_MSG = "resp_msg";

    public static final String VDATA = "vdata";

    public static final String RESP_CONTENT = "content";

    public static String toJSONString(Map<String, Object> jsonMap) {
        JSONObject result = new JSONObject();

        try {
            if (jsonMap != null && !jsonMap.isEmpty()) {

                String key = null;
                Object value = null;
                for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                    if (entry != null) {
                        key = entry.getKey();
                        value = entry.getValue();

                        if (!TextUtils.isEmpty(key)) {
                            result.put(key, value);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    public static JSONObject toJSONObject(String jsonString) {
        JSONObject jsonObject = null;
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                jsonObject = new JSONObject(jsonString);
            } catch (JSONException e) {
//                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    public static JSONArray toJSONArray(String jsonString) {
        JSONArray jsonObject = null;
        if (!TextUtils.isEmpty(jsonString)) {
            try {
                jsonObject = new JSONArray(jsonString);
            } catch (JSONException e) {
//                e.printStackTrace();
            }
        }

        return jsonObject;
    }

    public static JSONObject toJSONObject(Object obj) {
        return obj instanceof JSONObject ? (JSONObject) obj : null;
    }

    public static JSONArray toJSONArray(Object obj) {
        return obj instanceof JSONArray ? (JSONArray) obj : null;
    }

    /**
     * JSON转Bean
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> T toBean(String json, Class<T> cls) {
        try {
            return JSON.parseObject(json, cls);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * JSON转List<Bean>
     *
     * @param json
     * @param cls
     * @return
     */
    public static <T> List<T> toListBean(String json, Class<T> cls) {
        try {
            return JSON.parseArray(json, cls);
        } catch (Exception e) {
            return null;
        }
    }
}
