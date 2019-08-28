package com.jerry.control.adduser;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.jerry.baselib.common.base.BaseRecyclerAdapter;
import com.jerry.baselib.common.base.RecyclerViewHolder;
import com.jerry.baselib.common.util.DateUtils;
import com.jerry.baselib.common.util.SpannableStringUtils;
import com.jerry.control.R;
import com.jerry.control.bean.User;

import androidx.core.content.ContextCompat;

/**
 * @author Jerry
 * @createDate 2019-05-25
 * @copyright www.aniu.tv
 * @description
 */
class UserItemAdapter extends BaseRecyclerAdapter<User> {

    public UserItemAdapter(Context context, List<User> data) {
        super(context, data);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_title_content;
    }

    @Override
    public void convert(RecyclerViewHolder holder, int position, int viewType, User bean) {
        TextView title = holder.getView(R.id.tv_title);
        TextView content = holder.getView(R.id.tv_content);
        SpannableStringUtils.Builder builder = new SpannableStringUtils.Builder("");
        title.setText(builder.build());
        content.setText(bean.getUserpwd());
    }
}
