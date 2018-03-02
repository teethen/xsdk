package com.teethen.sdk.xutil;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by xingq on 2018/3/1.
 */

public class LayoutUtil {

    public static LinearLayoutManager getLinearLayoutManager(Context context) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.scrollToPositionWithOffset(5, 0);
        return layoutManager;
    }

    public static GridLayoutManager getGridLayoutManager(Context context, int columnNum) {
        if (columnNum <= 0) columnNum = 4;
        GridLayoutManager layoutManager = new GridLayoutManager(context, columnNum);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);
        return layoutManager;
    }

    public static void setupRecyclerView(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

}
