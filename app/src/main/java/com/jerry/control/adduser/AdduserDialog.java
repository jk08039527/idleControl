package com.jerry.control.adduser;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.jerry.baselib.common.base.BaseRecyclerAdapter;
import com.jerry.baselib.common.retrofit.RetrofitHelper;
import com.jerry.baselib.common.retrofit.callback.RetrofitCallBack;
import com.jerry.baselib.common.retrofit.response.BaseResponse;
import com.jerry.baselib.common.retrofit.response.ResponseResult;
import com.jerry.baselib.common.util.LogUtils;
import com.jerry.baselib.common.util.OnDataChangedListener;
import com.jerry.baselib.common.util.ToastUtil;
import com.jerry.baselib.common.weidgt.BaseDialog;
import com.jerry.control.bean.Api;
import com.jerry.control.R;
import com.jerry.control.bean.BaseRequest;
import com.jerry.control.bean.RequestUser;
import com.jerry.control.bean.SelectBean;

/**
 * @author Jerry
 * @createDate 2019-05-25
 * @copyright www.aniu.tv
 * @description
 */
public class AdduserDialog extends BaseDialog implements BaseRecyclerAdapter.OnItemClickListener {

    private String[] pate = {"一天", "一个月", "半年", "一年"};
    private EditText etPhone;
    private EditText etPwd;
    private TagItemAdapter mAdapter;
    private List<SelectBean> mData = new ArrayList<>();
    private OnDataChangedListener<RequestUser> mOnDataChangedListener;

    public AdduserDialog(Context context) {
        super(context);
        mAdapter = new TagItemAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int getContentId() {
        return R.layout.dialog_adduser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etPhone = findViewById(R.id.et_phone);
        etPwd = findViewById(R.id.et_pwd);
        RecyclerView rvTags = findViewById(R.id.rv_tags);
        rvTags.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvTags.setAdapter(mAdapter);
        for (String s : pate) {
            SelectBean selectBean = new SelectBean();
            selectBean.setTitle(s);
            mData.add(selectBean);
        }
        mData.get(1).setSelected(true);
        mAdapter.notifyDataSetChanged();

        setPositiveText(R.string.add);
        setPositiveListener(v -> {
            String phone = etPhone.getText().toString().trim();
            if (phone.length() != 11) {
                ToastUtil.showShortText("手机号格式错误");
                return;
            }
            int selext = -1;
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).isSelected()) {
                    selext = i;
                    break;
                }
            }
            List<RequestUser> users = new ArrayList<>();
            RequestUser user = new RequestUser();
            user.setUsername(etPhone.getText().toString());
            user.setUserpwd(etPwd.getText().toString());
            user.setLevel(selext + 1);
            users.add(user);
            BaseRequest<RequestUser> userBaseRequest = new BaseRequest<>();
            userBaseRequest.setMethod("User\\Login.createAbc123");
            userBaseRequest.setParams(users);
            RetrofitHelper.getInstance().getApi(Api.class).addUser(userBaseRequest)
                .execute(new RetrofitCallBack<BaseResponse>() {
                    @Override
                    public void onResponse(final BaseResponse response) {
                        ResponseResult result = response.getResult();
                        if (result == null) {
                            return;
                        }
                        int code = result.getCode();
                        if (code == 0) {
                            ToastUtil.showLongText("添加成功");
                            dismiss();
                            if (mOnDataChangedListener != null) {
                                mOnDataChangedListener.onDataChanged(null);
                            }
                            return;
                        }
                        ToastUtil.showLongText("错误码：" + result.getCode() + "\nMsg：" + result.getMsg());
                    }

                    @Override
                    public void onError(final String msg) {
                        LogUtils.e("error");
                    }
                });
        });
    }

    public void setOnDataChangedListener(OnDataChangedListener<RequestUser> onDataChangedListener) {
        mOnDataChangedListener = onDataChangedListener;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        for (SelectBean mDatum : mData) {
            mDatum.setSelected(false);
        }
        mData.get(position).setSelected(true);
        mAdapter.notifyDataSetChanged();
    }
}
