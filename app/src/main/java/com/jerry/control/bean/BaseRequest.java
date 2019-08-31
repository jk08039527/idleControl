package com.jerry.control.bean;

import java.util.List;

/**
 * @author Jerry
 * @createDate 2019-08-29
 * @copyright www.aniu.tv
 * @description
 */
public class BaseRequest<T> {

    private String jsonrpc = "2.0";
    private String method;
    private String id = "cd0876cbf1e6b09d10b7ad40e4de9169";
    private List<T> params;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<T> getParams() {
        return params;
    }

    public void setParams(List<T> params) {
        this.params = params;
    }
}
