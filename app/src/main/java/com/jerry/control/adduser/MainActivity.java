package com.jerry.control.adduser;

import java.util.List;

import android.view.View;

import com.jerry.baselib.common.base.BaseRecyclerActivity;
import com.jerry.baselib.common.base.BaseRecyclerAdapter;
import com.jerry.baselib.common.retrofit.RetrofitHelper;
import com.jerry.baselib.common.retrofit.callback.RetrofitCallBack;
import com.jerry.baselib.common.retrofit.response.BaseResponse;
import com.jerry.baselib.common.retrofit.response.ResponseResult;
import com.jerry.baselib.common.util.LogUtils;
import com.jerry.control.R;
import com.jerry.control.bean.Api;
import com.jerry.control.bean.BaseRequest;
import com.jerry.control.bean.ResposenseUser;

public class MainActivity extends BaseRecyclerActivity<ResposenseUser> {

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的控制台");
        setRight("添加");
    }

    @Override
    protected BaseRecyclerAdapter<ResposenseUser> initAdapter() {
        return new UserItemAdapter(this, mData);
    }

    @Override
    protected void getData() {
        BaseRequest<ResposenseUser> userBaseRequest = new BaseRequest<>();
        userBaseRequest.setMethod("User\\Login.getListBzm123");
        RetrofitHelper.getInstance().getApi(Api.class).getList(userBaseRequest)
            .execute(new RetrofitCallBack<BaseResponse<List<ResposenseUser>>>() {
                @Override
                public void onResponse(final BaseResponse<List<ResposenseUser>> response) {
                    if (isFinishing()) {
                        return;
                    }
                    ResponseResult<List<ResposenseUser>> result = response.getResult();
                    if (result == null) {
                        return;
                    }
                    List<ResposenseUser> users = result.getData();
                    mData.clear();
                    mData.addAll(users);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void onError(final String msg) {
                    if (isFinishing()) {
                        return;
                    }
                    LogUtils.e("error:" + msg);
                }

                @Override
                public void onAfter() {
                    if (isFinishing()) {
                        return;
                    }
                    closeLoadingDialog();
                    mPtrRecyclerView.onRefreshComplete();
                }
            });
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_right) {
            AdduserDialog adduserDialog = new AdduserDialog(this);
            adduserDialog.setOnDataChangedListener(data -> reload());
            adduserDialog.show();
        }
    }

    @Override
    public void onItemClick(View itemView, int position) {
    }
}