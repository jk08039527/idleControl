package com.jerry.control;

import java.util.List;

import com.jerry.baselib.common.retrofit.calladapter.RCall;
import com.jerry.baselib.common.retrofit.response.BaseResponse;
import com.jerry.control.bean.BaseRequest;
import com.jerry.control.bean.User;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author Jerry
 * @createDate 2019-08-31
 * @copyright www.aniu.tv
 * @description
 */
public interface Api {

    String API = "http://47.107.32.113:81";

    /**
     * 添加用户
     */
    @POST(API)
    RCall<BaseResponse<Boolean>> addUser(@Body BaseRequest body);

    /**
     * 查询列表
     */
    @POST(API)
    RCall<BaseResponse<List<User>>> getList(@Body BaseRequest body);
}
