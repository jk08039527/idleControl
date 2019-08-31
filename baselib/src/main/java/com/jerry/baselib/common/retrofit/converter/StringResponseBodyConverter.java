package com.jerry.baselib.common.retrofit.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Author：OTMAGIC
 * WeChat：Longalei888
 * Date：2018/6/12
 * Signature:每一个Bug修改,每一次充分思考,都会是一种进步.
 * Describtion:
 */

public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String  convert(ResponseBody value) throws IOException {
        try {
            return value.string();

        } finally {
            value.close();
        }
    }
}
