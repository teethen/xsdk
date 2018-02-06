package com.teethen.sdk.xdialog.dialogui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teethen.sdk.R;
import com.teethen.sdk.xdialog.dialogui.bean.TieBean;
import com.teethen.sdk.xdialog.dialogui.holder.SuperItemHolder;
import com.teethen.sdk.xdialog.dialogui.holder.TieItemHolder;

import java.util.List;

public class TieAdapter extends SuperAdapter<TieBean> {

    private boolean isItemType;

    public TieAdapter(Context mContext, List<TieBean> list, boolean isItemType) {
        super(mContext, list);
        this.isItemType = isItemType;
    }

    public TieAdapter(Context mContext, List<TieBean> list) {
        super(mContext, list);
    }

    @Override
    public SuperItemHolder getItemHolder(ViewGroup parent, int viewType) {
        return new TieItemHolder(mContext, mListener, LayoutInflater.from(mContext).
                inflate(R.layout.dialogui_holder_item_tie, parent, false));
    }

    /**
     * 1top 2midle 3bottom 4all
     */
    protected int countPosition(int position) {
        if (isItemType) {
            if (mDatas.size() == 1) {
                return 4;
            }
            if (mDatas.size() > 1) {
                if (position == 0) {
                    return 1;
                } else if (position == mDatas.size() - 1) {
                    return 3;
                }
            }
            return 2;
        } else {
            return super.countPosition(position);
        }
    }
}
