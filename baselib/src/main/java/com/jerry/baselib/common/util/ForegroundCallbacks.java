package com.jerry.baselib.common.util;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

import java.lang.ref.WeakReference;

/**
 * Created by th on 2017/7/18. 类说明:监听应用前后台切换
 */
public class ForegroundCallbacks implements ActivityLifecycleCallbacks {

    private int mStartCount; //当前已经启动的activity数量
    private WeakReference<Activity> mActivityWeakReference;

    @Override
    public void onActivityStarted(Activity activity) {
        if (mStartCount == 0) {
            LogUtils.d("================================    切到到前台    ================================");
            Activity lastActivity = null;
            if (mActivityWeakReference != null) {
                lastActivity = mActivityWeakReference.get();
            }
            if (lastActivity instanceof ForegroundListener) {
                LogUtils.d("================================    onForeground exec    ================================");
                ((ForegroundListener) lastActivity).onForeground();
            }
        }
        mStartCount++;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        mStartCount--;
        if (mStartCount == 0) {
            LogUtils.d("================================    切换到后台    ================================");
            mActivityWeakReference = new WeakReference<>(activity);
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (mStartCount == 0 && mActivityWeakReference != null) {
            LogUtils.d("================================    exit app    ================================");
            mActivityWeakReference.clear();
        }
    }
}
