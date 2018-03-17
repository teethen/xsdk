package com.teethen.xsdk.recyvlergroup.adapter;

import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teethen.sdk.xwidget.baseadapter.recyclergroup.GroupViewHolder;
import com.teethen.sdk.xwidget.baseadapter.recyclergroup.HeaderGroupRecyclerViewAdapter;
import com.teethen.xsdk.R;
import com.teethen.xsdk.recyvlergroup.DataSource;
import com.teethen.xsdk.recyvlergroup.bean.WeChatItem;

/**
 * Created by xingq on 2018/3/15.
 */

public class WeChatMeGroupAdapter extends HeaderGroupRecyclerViewAdapter<WeChatItem>{

    public WeChatMeGroupAdapter(RecyclerView recyclerView, WeChatItem[][] items) {
        super(recyclerView, items);
    }

    @Override
    public int getHeaderLayoutId(int viewType) {
        return R.layout.divider_20dp;
    }

    @Override
    public int getChildLayoutId(int viewType) {
        if (viewType == DataSource.VIEW_TYPE_WECHAT_PROFILE) {
            return R.layout.item_wechat_me_profile;
        }
        return R.layout.item_wechat_me_common;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return getItem(groupPosition, childPosition).getViewType();
    }

    @Override
    public void onBindHeaderViewHolder(GroupViewHolder holder, WeChatItem item, int groupPosition) {

    }

    @Override
    public void onBindChildViewHolder(GroupViewHolder holder, WeChatItem item, int groupPosition, int childPosition) {
        int viewType = getChildItemViewType(groupPosition, childPosition);

        if (viewType == DataSource.VIEW_TYPE_WECHAT_PROFILE) {
            TextView tvName = holder.get(R.id.tv_wechat_name);
            tvName.setText(item.getTitleId());
            holder.setImageResource(R.id.iv_avatar, R.mipmap.ic_me_avatar);
        } else {
            TextView tvTitle = holder.get(R.id.tv_title);
            tvTitle.setText(item.getTitleId());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(item.getImgId(), 0, 0, 0);
            } else {
                tvTitle.setCompoundDrawables(context.getResources().getDrawable(item.getImgId()), null, null, null);
            }
        }

        holder.setVisible(R.id.divider, !isGroupLastItem(groupPosition, childPosition));
    }

}
