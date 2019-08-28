package com.jerry.control.adduser;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;

import com.jerry.baselib.common.base.BaseRecyclerActivity;
import com.jerry.baselib.common.base.BaseRecyclerAdapter;
import com.jerry.control.R;
import com.jerry.control.bean.User;

public class MainActivity extends BaseRecyclerActivity<User> implements BaseRecyclerAdapter.OnItemLongClickListener {

    @Override
    protected void initView() {
        super.initView();
        setTitle("我的控制台");
        setRight("添加");
    }

    @Override
    protected BaseRecyclerAdapter<User> initAdapter() {
        UserItemAdapter userItemAdapter = new UserItemAdapter(this, mData);
        userItemAdapter.setOnItemLongClickListener(this);
        return userItemAdapter;
    }

    @Override
    protected void getData() {

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
        User user = mData.get(position);
        AdduserDialog adduserDialog = new AdduserDialog(this);
        adduserDialog.setPhonePassWd(user.getUsername(), user.getUserpwd());
        adduserDialog.setOnDataChangedListener(data -> reload());
        adduserDialog.show();
    }

    @Override
    public void onItemLongClick(View itemView, int position) {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm == null) {
            toast("复制失败");
            return;
        }
        ClipData mClipData = ClipData.newPlainText("Label", mData.get(position).getUserpwd());
        //将ClipData内容放到系统剪贴板里
        cm.setPrimaryClip(mClipData);
        toast("激活码已复制");
    }
}