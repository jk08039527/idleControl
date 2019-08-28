package com.jerry.baselib.common.okhttp.callback;

import java.io.IOException;

import com.jerry.baselib.common.okhttp.OkHttpUtils;
import com.jerry.baselib.common.util.LogUtils;

import okhttp3.Request;
import okhttp3.Response;

public class Callback<T> {

    protected String errorMsg = OkHttpUtils.ERROR_MSG;

    /**
     * UI Thread
     */
    public void onBefore(Request request) {}

    /**
     * UI Thread
     */
    public void onAfter() {}

    /**
     * UI Thread
     */
    public void inProgress(float progress) {

    }

    public T parseNetworkResponse(Response response) throws IOException {
        try {
            String df = response.body().string();
            LogUtils.d(df);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void onError(Request request, Exception e) {}

    public void onResponse(T response) {}

    public void onError(String msg) {}

    public static Callback CALLBACK_DEFAULT = new Callback() {

        @Override
        public Object parseNetworkResponse(Response response) {
            return null;
        }

        @Override
        public void onError(Request request, Exception e) {

        }

        @Override
        public void onResponse(Object response) {

        }

        @Override
        public void onError(String msg) {

        }
    };

}