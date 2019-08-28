package com.jerry.baselib.common.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jerry.baselib.BaseApp;
import com.jerry.baselib.Key;

/**
 * SharedPreference操作类
 */
public class PreferenceHelp {

    public static final String ISENTITY = "isentity";
    public static final String ISNEW = "isnew";
    public static final String AUTO_REPLAY = "auto_replay";
    public static final String PUBLISH_TIME = "publish_time";
    public static final String PUBLISH_COUNT = "publish_count";
    public static final String PRODUCT_TYPE = "product_type";
    public static final String RANDOM_ADDRESS = "random_address";
    public static final String RANDOM_PRODUCT = "random_product";
    public static final String PUBLISH_TIME_START = "publish_time_start";
    public static final String PUBLISH_TIME_END = "publish_time_end";
    public static final String PUBLISH_OFTEN = "publish_often";
    public static final String PUBLISH_OFTEN_START = "publish_often_start";
    public static final String PUBLISH_OFTEN_END = "publish_often_end";
    public static final String PUBLISH_TRANS_FEE = "publish_trans_fee";
    public static final String PUBLISH_ADDRESS = "publish_address";
    public static final String ADVERTISEMENTLINK = "advertisementlink";
    public static final String PDD_COOKIE = "pddCookie";
    public static final String DIRNAME = "dirname";
    public static final String FORCUS_REFRESH = "forcus_refresh";
    public static final String RANDOM_FISH = "random_fish";
    public static final String FISH_POND = "fish_pond";
    public static final String DELETE_OPTION = "DELETE_OPTION";
    public static final String LAST_QIANDAO = "last_qiandao";
    /**
     * 0：商品，1：免费送
     */
    public static final String FREESEND = "freesend";
    public static final String SLOW = "slow";

    private PreferenceHelp() {
    }

    private static SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseApp.getInstance());

    public static String getString(String strKey) {
        return sp.getString(strKey, Key.NIL);
    }

    public static String getString(String strKey, String strDefault) {
        return sp.getString(strKey, strDefault);
    }

    public static void putString(String strKey, String strData) {
        sp.edit().putString(strKey, strData).apply();
    }

    public static int getInt(String strKey) {
        return sp.getInt(strKey, 0);
    }

    public static int getInt(String strKey, int strDefault) {
        return sp.getInt(strKey, strDefault);
    }

    public static void putInt(String strKey, int strData) {
        sp.edit().putInt(strKey, strData).apply();
    }

    public static boolean getBoolean(String strKey) {
        return sp.getBoolean(strKey, false);
    }

    public static boolean getBoolean(String strKey, boolean bDefault) {
        return sp.getBoolean(strKey, bDefault);
    }

    public static void putBoolean(String strKey, boolean bValue) {
        sp.edit().putBoolean(strKey, bValue).apply();
    }
}