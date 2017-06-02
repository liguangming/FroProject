package fro.org.froproject.mvp.model.entity;

import java.io.Serializable;

import fro.org.froproject.mvp.model.api.Api;

/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用BaseJson
 * Created by Lgm on 2017/5/31 0031.
 */

public class BaseJson<T> implements Serializable {
    private T d;
    private int c = -999;
    private String m;

    public T getD() {
        return d;
    }

    public int getC() {
        return c;
    }

    public String getM() {
        return m;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        if (c == Api.RequestSuccess) {
            return true;
        } else {
            return false;
        }
    }
}
