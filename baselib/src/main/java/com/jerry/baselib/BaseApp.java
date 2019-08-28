package com.jerry.baselib;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.jerry.baselib.common.util.ForegroundCallbacks;
import com.tencent.bugly.beta.Beta;
import com.tencent.tinker.entry.DefaultApplicationLike;

import androidx.multidex.MultiDex;

/**
 * @author Jerry
 * @createDate 2019/4/10
 * @description
 */
public abstract class BaseApp extends DefaultApplicationLike {

    @SuppressLint("StaticFieldLeak")
    private static Application mInstance;
    private ForegroundCallbacks mForegroundCallbacks;

    public static Application getInstance() {
        return mInstance;
    }

    public BaseApp(Application application, int tinkerFlags, boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
        long applicationStartMillisTime, Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);
        MultiDex.install(base);
        Beta.installTinker(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = getApplication();
        initConfig();

        mForegroundCallbacks = new ForegroundCallbacks();
        getApplication().registerActivityLifecycleCallbacks(mForegroundCallbacks);
    }


    @Override
    public void onTerminate() {
        if (mForegroundCallbacks != null) {
            getApplication().unregisterActivityLifecycleCallbacks(mForegroundCallbacks);
        }
        super.onTerminate();
    }

    protected abstract void initConfig();

    public static class Config {

        public static boolean DEBUG;
        public static String APPLICATION_ID;
        public static int VERSION_CODE;
        public static String VERSION_NAME;
        public static String BUGLY_APP_ID;
        public static String FILE_PROVIDER;
        public static String SIGN;
        public static Class<?> ACCESS_CLASS;
    }
}
