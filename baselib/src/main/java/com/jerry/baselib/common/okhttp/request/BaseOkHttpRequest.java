package com.jerry.baselib.common.okhttp.request;

import java.util.LinkedHashMap;
import java.util.Map;

import com.jerry.baselib.common.okhttp.OkHttpUtils;
import com.jerry.baselib.common.okhttp.callback.Callback;

import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;

public abstract class BaseOkHttpRequest {

    protected String url;
    protected Object tag;
    protected Map<String, String> params;
    protected Map<String, String> headers;

    protected Request.Builder builder = new Request.Builder();

    protected BaseOkHttpRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers) {
        this.url = url;
        this.tag = tag;
        this.params = params;
        this.headers = headers;
        if (this.headers == null) {
            this.headers = new LinkedHashMap<>();
        }
        this.headers.put("user_token", "a92bb460-cb30-11e9-a9f3-11ed978d72b7");
        this.headers.put("Content-Type", "application/json");
        this.headers.put("cache-control", "no-cache");
        if (url == null) {
            throw new IllegalArgumentException("url can not be null.");
        }
    }

    protected abstract RequestBody buildRequestBody();

    protected RequestBody wrapRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null) {
            return requestBody;
        }
        return new CountingRequestBody(requestBody, (bytesWritten, contentLength) -> OkHttpUtils.getInstance().getDelivery().post(
            () -> callback.inProgress(bytesWritten * 1.0f / contentLength)));
    }

    protected abstract Request buildRequest(Request.Builder builder, RequestBody requestBody);

    public RequestCall build() {
        return new RequestCall(this);
    }

    public Request generateRequest(Callback callback) {
        RequestBody requestBody = wrapRequestBody(buildRequestBody(), callback);
        prepareBuilder();
        return buildRequest(builder, requestBody);
    }

    private void prepareBuilder() {
        builder.url(url).tag(tag);
        appendHeaders();
    }

    protected void appendHeaders() {
        Headers.Builder headerBuilder = new Headers.Builder();
        if (headers == null || headers.isEmpty()) {
            return;
        }

        for (String key : headers.keySet()) {
            headerBuilder.add(key, headers.get(key));
        }
        builder.headers(headerBuilder.build());
    }

    @Override
    public String toString() {
        return "BaseOkHttpRequest{" + "url='" + url + '\'' + ", tag=" + tag + ", params=" + params + ", headers=" + headers + '}';
    }
}
