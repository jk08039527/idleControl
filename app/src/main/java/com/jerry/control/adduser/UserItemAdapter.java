package com.jerry.control.adduser;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.jerry.baselib.common.base.BaseRecyclerAdapter;
import com.jerry.baselib.common.base.RecyclerViewHolder;
import com.jerry.control.R;
import com.jerry.control.bean.ResposenseUser;

/**
 * @author Jerry
 * @createDate 2019-05-25
 * @copyright www.aniu.tv
 * @description
 */
class UserItemAdapter extends BaseRecyclerAdapter<ResposenseUser> {

    public UserItemAdapter(Context context, List<ResposenseUser> data) {
        super(context, data);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_title_content;
    }

    @Override
    public void convert(RecyclerViewHolder holder, int position, int viewType, ResposenseUser bean) {
        TextView title = holder.getView(R.id.tv_title);
        TextView content = holder.getView(R.id.tv_content);
        title.setText(bean.getUsername());
        content.setText(bean.getUserpwd());
    }
}
