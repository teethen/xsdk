package com.teethen.sdk.xwidget.baseadapter.recyclergroup;

import android.support.v7.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xingq on 2018/2/8.
 */
abstract public class ExpandableGroupRecyclerViewAdapter<T> extends GroupRecyclerViewAdapter<T> {

    private Map<Integer, List<T>> cache = new HashMap<>();

    public ExpandableGroupRecyclerViewAdapter(RecyclerView recyclerView) {
        super(recyclerView);
    }

    public ExpandableGroupRecyclerViewAdapter(RecyclerView recyclerView, T[][] groups) {
        super(recyclerView, groups);
    }

    public ExpandableGroupRecyclerViewAdapter(RecyclerView recyclerView, List<List<T>> groups) {
        super(recyclerView, groups);
    }

    public boolean isExpand(int groupPosition) {
        if (0 > groupPosition || 0 == cache.size() || !cache.containsKey(groupPosition)) {
            return true;
        }
        return GroupAdapterUtils.isEmpty(cache.get(groupPosition));
    }

    public boolean expandGroup(int groupPosition) {
        return expandGroup(groupPosition, true);
    }

    public boolean expandGroup(int groupPosition, boolean withAnim) {
        if (checkGroupPosition(groupPosition)) {
            List<T> items = cache.get(groupPosition);
            if (!GroupAdapterUtils.isEmpty(items)) {
                boolean isSuccess = insertItems(groupPosition, 1, items, withAnim);
                if (isSuccess) {
                    cache.remove(groupPosition);
                }
                return isSuccess;
            }
        }
        return false;
    }

    public boolean collapseGroup(int groupPosition) {
        return collapseGroup(groupPosition, true);
    }

    public boolean collapseGroup(int groupPosition, boolean withAnim) {
        if (checkGroupPosition(groupPosition)) {
            List<T> items = showFooter() ? getGroupItemsWithoutHeaderFooter(groupPosition) : getGroupItemsWithoutHeader(groupPosition);
            if (GroupAdapterUtils.isEmpty(items)) {
                return false;
            }

            boolean isSuccess = removeItems(groupPosition, 1, items.size(), withAnim);
            if (isSuccess) {
                cache.put(groupPosition, items);
            }
            return isSuccess;
        }
        return false;
    }

}
