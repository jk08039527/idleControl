package com.jerry.control.adduser;

import java.util.List;

import android.content.Context;
import android.widget.TextView;

import com.jerry.baselib.common.base.BaseRecyclerAdapter;
import com.jerry.baselib.common.base.RecyclerViewHolder;
import com.jerry.control.R;
import com.jerry.control.bean.SelectBean;

/**
 * @author Jerry
 * @createDate 2019-05-25
 * @copyright www.aniu.tv
 * @description
 */
public class TagItemAdapter extends BaseRecyclerAdapter<SelectBean> {
    public TagItemAdapter(Context context, List<SelectBean> data) {
        super(context, data);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.item_select_text;
    }

    @Override
    public void convert(RecyclerViewHolder holder, int position, int viewType, SelectBean bean) {
        TextView textView = holder.getView(R.id.textView);
        textView.setText(bean.getTitle());
        textView.setSelected(bean.isSelected());
    }
}
