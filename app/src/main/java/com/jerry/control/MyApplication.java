package com.jerry.control;

import java.util.List;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.jerry.baselib.BaseApp;
import com.jerry.baselib.Key;
import com.jerry.baselib.common.util.AppUtils;
import com.jerry.control.adduser.MainActivity;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.beta.Beta;

/**
 * @author Jerry
 * @createDate 2019/4/10
 * @description
 */
public class MyApplication extends BaseApp {

    public MyApplication(final Application application, final int tinkerFlags, final boolean tinkerLoadVerifyFlag,
        final long applicationStartElapsedTime,
        final long applicationStartMillisTime, final Intent tinkerResultIntent) {
        super(application, tinkerFlags, tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent);
    }

    @Override
    protected void initConfig() {
        Config.DEBUG = BuildConfig.DEBUG;
        Config.SIGN = BuildConfig.SIGN;
        Config.APPLICATION_ID = BuildConfig.APPLICATION_ID;
        Config.VERSION_CODE = BuildConfig.VERSION_CODE;
        Config.VERSION_NAME = BuildConfig.VERSION_NAME;
        Config.BUGLY_APP_ID = BuildConfig.BUGLY_APP_ID;
        Config.FILE_PROVIDER = BuildConfig.FILE_PROVIDER;
        Beta.canShowUpgradeActs.add(MainActivity.class);
        BuglyStrategy strategy = new BuglyStrategy();
        strategy.setAppVersion(Config.VERSION_NAME + (Config.DEBUG ? "test" : Key.NIL));
        strategy.setAppChannel(AppUtils.getChannel());
        Bugly.init(getInstance(), Config.BUGLY_APP_ID, Config.DEBUG, strategy);
    }

    public static int getXyState() {
        PackageManager pckMan = BaseApp.getInstance().getPackageManager();
        List<PackageInfo> packageInfo = pckMan.getInstalledPackages(0);
        // 0：未安装，1：已安装 版本正确，2：已安装 版本不正确
        int state = 0;
        for (PackageInfo info : packageInfo) {
            if ("com.taobao.idlefish".equals(info.packageName)) {
                int version = AppUtils.getVersionCode(info.versionName);
                if (version > 60104 && version < 60200) {
                    state = 1;
                } else {
                    state = 2;
                }
                break;
            }
        }
        return state;
    }
}
