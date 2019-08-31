package com.jerry.baselib.common.retrofit.response;

/**
 * @author Jerry
 * @createDate 2019-08-31
 * @copyright www.aniu.tv
 * @description
 */
public class ResponseResult<T> {

    /**
     * code : 0 msg : data : true trace : a2863690-cb3b-11e9-9dec-3fd26e873263
     */

    private int code;
    private String msg;
    private T data;
    private String trace;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }
}
