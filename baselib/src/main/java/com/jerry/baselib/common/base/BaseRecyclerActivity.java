package com.jerry.baselib.common.base;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.jerry.baselib.R;
import com.jerry.baselib.common.ptrlib.widget.PtrRecyclerView;
import com.jerry.baselib.common.util.NetworkUtil;

public abstract class BaseRecyclerActivity<T> extends BaseActivity implements BaseRecyclerAdapter.OnItemClickListener {

    protected PtrRecyclerView mPtrRecyclerView;
    protected BaseRecyclerAdapter<T> mAdapter;
    protected List<T> mData = new ArrayList<>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lazyLoad();
    }

    @Override
    protected int getContentViewResourceId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mAdapter = initAdapter();
        mAdapter.setOnItemClickListener(this);
        mPtrRecyclerView = findViewById(R.id.ptrRecyclerView);
        mPtrRecyclerView.setAdapter(mAdapter);
        mPtrRecyclerView.setOnRefreshListener(() -> {
            onPreRefresh();
            reload();
        });
    }

    protected abstract BaseRecyclerAdapter<T> initAdapter();

    private void lazyLoad() {
        if (mAdapter != null && mData != null && mData.size() != 0) {
            mAdapter.notifyDataSetChanged();
        } else {
            if (NetworkUtil.isNetworkAvailable() && mData.size() == 0) {
                loadingDialog();
            }
            getData();
        }
    }

    /**
     * 刷新前还原排序
     */
    protected void onPreRefresh() {}

    /**
     * 从网络端获取数据
     */
    protected abstract void getData();

    public void reload() {
        getData();
    }
}
