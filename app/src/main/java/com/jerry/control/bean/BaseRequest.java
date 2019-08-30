package com.jerry.control.bean;

import java.util.List;

/**
 * @author Jerry
 * @createDate 2019-08-29
 * @copyright www.aniu.tv
 * @description
 */
public class BaseRequest<T> {

    /**
     * jsonrpc : 2.0
     * method : User\Login.createAbc123
     * id : cd0876cbf1e6b09d10b7ad40e4de9169
     * params : [{"username":"oyp1212","userpwd":"1234567"}]
     */

    private String jsonrpc;
    private String method;
    private String id;
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
