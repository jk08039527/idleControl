package com.jerry.baselib.common.retrofit;

import java.lang.reflect.Field;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import android.os.Looper;
import android.util.ArrayMap;

import com.jerry.baselib.Key;
import com.jerry.baselib.common.retrofit.calladapter.RCallFactory;
import com.jerry.baselib.common.retrofit.callback.RetrofitCallBack;
import com.jerry.baselib.common.retrofit.converter.StringConverterFactory;
import com.jerry.baselib.common.retrofit.interceptor.CommonInterceptor;
import com.jerry.baselib.common.retrofit.interceptor.LoggingInterceptor;
import com.jerry.baselib.common.util.LogUtils;
import com.jerry.baselib.common.util.NetworkUtil;
import com.jerry.baselib.common.util.WeakHandler;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：zmf Date：2018/6/4 Describtion: 网络工具配置  dzcj接口地址的前缀有种，可以尝试用建造者的方式去构建请求，配置Base_URL和签名管理
 */

public class RetrofitHelper {

    public static String ERROR_MSG = Key.NIL;
    private static String TIMEOUT_MSG = "超时啦，请刷新重试！";
    private static String BROKEN_MSG = "网络不给力，请检查网络连接后再试！";
    private static RetrofitHelper mInstance;
    private WeakHandler mDelivery = new WeakHandler(Looper.getMainLooper());
    private ConcurrentHashMap<Object, Call> callMap = new ConcurrentHashMap<>();
    private ArrayMap<Class<?>, Object> map = new ArrayMap<>();

    /**
     * RetrofitHelper 初始化 currentFlag 是否需要再次初始化RetrofitHelper
     */
    public static RetrofitHelper getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitHelper.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitHelper();
                }
            }
        }
        return mInstance;
    }

    public <T> T getApi(Class<T> tClass) {
        Object api = map.get(tClass);
        if (api == null) {
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.connectTimeout(15, TimeUnit.SECONDS);
            Retrofit.Builder builder = new Retrofit.Builder()
                .addCallAdapterFactory(RCallFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(StringConverterFactory.create());
            List<Interceptor> interceptors = okBuilder.interceptors();
            interceptors.add(new LoggingInterceptor());
            interceptors.add(new CommonInterceptor());
            OkHttpClient okHttpClient = okBuilder.build();
            builder.client(okHttpClient);
            String apiStr;
            Field fieldd;
            try {
                fieldd = tClass.getDeclaredField("API");
                apiStr = (String) fieldd.get(null);
                api = builder.baseUrl(apiStr).build().create(tClass);
                map.put(tClass, api);
            } catch (NoSuchFieldException e) {
                LogUtils.e("no such static Field API");
            } catch (IllegalAccessException e) {
                LogUtils.e("Field API cannot access");
            }
        }
        return (T) api;
    }

    public <R, T> void execute(final Object tag, final Call<R> call, final RetrofitCallBack<T> callback) {
        if (tag != null) {
            callMap.put(tag, call);
        }
        call.enqueue(new retrofit2.Callback<R>() {
            @Override
            public void onResponse(Call<R> call, retrofit2.Response<R> response) {
                if (response.code() >= 400 && response.code() <= 599) {
                    try {
                        sendFailResultCallback(ERROR_MSG, callback);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        T t = callback.parseNetworkResponse(response.body());
                        sendSuccessResultCallback(t, callback);
                    } catch (Exception e) {
                        sendFailResultCallback(ERROR_MSG, callback);
                    }
                }
                if (tag != null) {
                    callMap.remove(tag);
                }
            }

            @Override
            public void onFailure(Call<R> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    sendFailResultCallback(TIMEOUT_MSG, callback);
                } else {
                    if (NetworkUtil.isNetworkAvailable(false)) {
                        sendFailResultCallback(ERROR_MSG, callback);
                    } else {
                        sendFailResultCallback(BROKEN_MSG, callback);
                    }
                }
                if (tag != null) {
                    callMap.remove(tag);
                }
            }
        });
    }

    public void sendFailResultCallback(String msg, RetrofitCallBack callback) {
        if (callback == null) {
            return;
        }
        mDelivery.post(() -> {
            callback.onError(msg);
            callback.onAfter();
        });
    }

    @SuppressWarnings("unchecked")
    public <T> void sendSuccessResultCallback(T t, RetrofitCallBack callback) {
        if (callback == null) {
            return;
        }
        mDelivery.post(() -> {
            callback.onResponse(t);
            callback.onAfter();
        });
    }

    /**
     * 进度回调
     *
     * @param progress 小数 eg:0.3, 0.45
     */
    public void sendProgressCallback(float progress, final RetrofitCallBack callback) {
        if (callback == null) {
            return;
        }
        mDelivery.post(() -> {
            callback.onProgress(progress);
        });
    }

    public void cancelTag(Object tag) {
        if (tag == null) {
            return;
        }
        Call call = callMap.get(tag);
        if (call != null) {
            call.cancel();
        }
    }
}
