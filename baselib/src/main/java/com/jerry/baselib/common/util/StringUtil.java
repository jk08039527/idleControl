package com.jerry.baselib.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.text.TextUtils;

import com.jerry.baselib.Key;

/**
 * 字符串相关工具类
 *
 * @author my
 * @time 2017/2/24 15:31
 */
public class StringUtil {

    public static void main(String[] strings) {
        System.out.println(getRandomInt(0, 2));
    }

    /**
     * 分割字段
     */
    public static String[] safeSplit(String str) {
        return safeSplit(str, " ");
    }

    /**
     * 分割字段
     */
    public static String[] safeSplit(String str, String regularExpression) {
        if (TextUtils.isEmpty(str)) {
            return new String[]{Key.NIL};
        }
        return str.split(regularExpression);
    }

    public static int getRandomInt(int min, int max) {
        if (min > max) {
            max = max + min;
            min = max - min;
            max = max - min;
        }
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    public static List<String> getPicsFromStr(String str) {
        List<String> pics = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String[] sdfs = safeSplit(str);
        for (String sdf : sdfs) {
            sb.append(sdf);
            if (sdf.contains(".jpg") || sdf.contains(".png") || sdf.contains(".jpeg")|| sdf.contains(".mp4")) {
                pics.add(sb.toString());
                sb.delete(0, sb.length());
            } else {
                sb.append(" ");
            }
        }
        return pics;
    }
}
