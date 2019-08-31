package com.jerry.baselib.common.retrofit.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author：OTMAGIC
 * WeChat：Longalei888
 * Date：2018/6/11
 * Signature:每一个Bug修改,每一次充分思考,都会是一种进步.
 * Describtion: 请求前拦截---添加公共参数/签名和验证字段
 */

public class CommonInterceptor implements Interceptor {

    /**
     *   添加签名时的公共参数公共参数
     * */
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
            .newBuilder()
            .addHeader("User-Token", "a92bb460-cb30-11e9-a9f3-11ed978d72b7")
            .addHeader("Content-Type", "application/json")
            .addHeader("cache-control", "no-cache")
            .build();
        return chain.proceed(request);
    }
}
