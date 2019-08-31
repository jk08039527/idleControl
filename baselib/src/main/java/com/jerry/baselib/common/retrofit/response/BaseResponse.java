package com.jerry.baselib.common.retrofit.response;


public class BaseResponse<T> {

    /**
     * jsonrpc : 2.0
     * id : cd0876cbf1e6b09d10b7ad40e4de9169
     * result : {"code":"0","msg":"","data":true,"trace":"a2863690-cb3b-11e9-9dec-3fd26e873263"}
     */

    private String jsonrpc;
    private String id;
    private ResponseResult<T> result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResponseResult<T> getResult() {
        return result;
    }

    public void setResult(final ResponseResult<T> result) {
        this.result = result;
    }
}