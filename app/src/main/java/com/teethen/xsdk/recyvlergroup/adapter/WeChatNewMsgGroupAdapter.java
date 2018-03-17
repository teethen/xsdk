package com.teethen.xsdk.recyvlergroup.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Switch;

import com.teethen.sdk.xwidget.baseadapter.recyclergroup.GroupAdapterUtils;
import com.teethen.sdk.xwidget.baseadapter.recyclergroup.GroupViewHolder;
import com.teethen.sdk.xwidget.baseadapter.recyclergroup.HeaderGroupRecyclerViewAdapter;
import com.teethen.xsdk.R;
import com.teethen.xsdk.recyvlergroup.DataSource;
import com.teethen.xsdk.recyvlergroup.bean.WeChatItem;

import java.util.List;

/**
 * Created by xingq on 2018/3/15.
 */

public class WeChatNewMsgGroupAdapter extends HeaderGroupRecyclerViewAdapter<WeChatItem> {

    public WeChatNewMsgGroupAdapter(RecyclerView recyclerView, WeChatItem[][] items) {
        super(recyclerView, items);
    }

    @Override
    public int getHeaderLayoutId(int viewType) {
        return R.layout.divider_20dp;
    }

    @Override
    public int getChildLayoutId(int viewType) {
        if (viewType == DataSource.VIEW_TYPE_WECHAT_SWITCH) {
            return R.layout.item_wechat_newmsg_switch;
        }
        return R.layout.item_wechat_newmsg_text;
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
        holder.setText(R.id.tv_title, item.getTitleId());
        holder.setText(R.id.tv_subtitle, item.getSubtitleId());
        holder.setVisible(R.id.tv_subtitle, item.getSubtitleId() != 0);
        holder.setVisibility(R.id.divider, isGroupLastItem(groupPosition, childPosition) ? View.INVISIBLE : View.VISIBLE);

        int viewType = getChildItemViewType(groupPosition, childPosition);
        if (viewType == DataSource.VIEW_TYPE_WECHAT_SWITCH) {
            Switch switchButton = holder.get(R.id.switch_button);

            switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
                holder.itemView.post(() -> {
                    if (R.attr.key_new_message_notify == item.getKey()) {
                        if (isChecked) {
                            insertGroups(groupPosition + 1, DataSource.WeChatItemMsgNotifyItemsHolder);
                        } else {
                            removeGroups(groupPosition + 1, groupsCount());
                        }
                    } else if (R.attr.key_voice == item.getKey()) {
                        if (isChecked) {
                            if (!hasItem(groupPosition, R.attr.key_new_message_voice)) {
                                insertItem(groupPosition, childPosition + 1, DataSource.NEW_MESSAGE_VOICE);
                            }
                        } else {
                            if (hasItem(groupPosition, R.attr.key_new_message_voice)) {
                                removeItem(groupPosition, childPosition + 1);
                            }
                        }
                    }
                });
            });
        }
    }

    private boolean hasItem(int groupPosition, int key) {
        List<WeChatItem> items = getGroups().get(groupPosition);
        if (!GroupAdapterUtils.isEmpty(items)) {
            for (WeChatItem item : items) {
                if (item.getKey() == key) {
                    return true;
                }
            }
        }
        return false;
    }
}
