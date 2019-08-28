package com.jerry.control.adduser;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.jerry.baselib.common.base.BaseRecyclerAdapter;
import com.jerry.baselib.common.okhttp.OkHttpUtils;
import com.jerry.baselib.common.okhttp.callback.Callback;
import com.jerry.baselib.common.util.LogUtils;
import com.jerry.baselib.common.util.OnDataChangedListener;
import com.jerry.baselib.common.util.ToastUtil;
import com.jerry.baselib.common.weidgt.BaseDialog;
import com.jerry.control.R;
import com.jerry.control.bean.SelectBean;
import com.jerry.control.bean.User;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Jerry
 * @createDate 2019-05-25
 * @copyright www.aniu.tv
 * @description
 */
public class AdduserDialog extends BaseDialog implements BaseRecyclerAdapter.OnItemClickListener {

    private String[] pate = {"一天", "一个月", "三个月", "半年", "一年", "永久"};
    private EditText etPhone;
    private TagItemAdapter mAdapter;
    private List<SelectBean> mData = new ArrayList<>();
    private OnDataChangedListener<User> mOnDataChangedListener;
    private String phone;
    private String passwd;

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
        etPhone.setText(phone);
        if (TextUtils.isEmpty(passwd)) {
            findViewById(R.id.tv_code).setVisibility(View.GONE);
        } else {
            TextView tvCode = findViewById(R.id.tv_code);
            tvCode.setText(passwd);
            tvCode.setVisibility(View.VISIBLE);
            tvCode.setOnLongClickListener(v -> {
                ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                if (cm == null) {
                    ToastUtil.showShortText("复制失败");
                    return true;
                }
                ClipData mClipData = ClipData.newPlainText("Label", tvCode.getText().toString());
                //将ClipData内容放到系统剪贴板里
                cm.setPrimaryClip(mClipData);
                ToastUtil.showShortText("激活码已复制");
                return true;
            });
        }
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
            long time;
            long daytime = 1000 * 60 * 60 * 24;
            switch (mData.get(selext).getTitle()) {
                case "一天":
                    time = System.currentTimeMillis() + daytime;
                    break;
                case "一个月":
                    time = System.currentTimeMillis() + daytime * 30;
                    break;
                case "三个月":
                    time = System.currentTimeMillis() + daytime * 92;
                    break;
                case "半年":
                    time = System.currentTimeMillis() + daytime * 183;
                    break;
                case "一年":
                    time = System.currentTimeMillis() + daytime * 365;
                    break;
                case "永久":
                default:
                    time = System.currentTimeMillis() + daytime * 99999;
                    break;
            }
            TreeMap<String, String> params = new TreeMap<>();
            params.put("jsonrpc", "2.0");
            params.put("method", "User\\\\Login.createAbc123");
            params.put("id", "cd0876cbf1e6b09d10b7ad40e4de9169");
            List<User> users = new ArrayList<>();
            User user = new User();
            user.setUsername(etPhone.getText().toString());
            user.setUserpwd("J123456");
            users.add(user);
            params.put("params", JSON.toJSONString(users));
            OkHttpUtils.post().url("http://47.107.32.113:81").params(params).build().enqueue(new Callback() {
                @Override
                public void onResponse(final Object response) {
                    LogUtils.d("fsdfs");
                }

                @Override
                public void onError(final String msg) {
                    LogUtils.e("error");
                }
            });
        });
    }

    public void setPhonePassWd(String phone, String passwd) {
        this.phone = phone;
        this.passwd = passwd;
    }

    public void setOnDataChangedListener(OnDataChangedListener<User> onDataChangedListener) {
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
