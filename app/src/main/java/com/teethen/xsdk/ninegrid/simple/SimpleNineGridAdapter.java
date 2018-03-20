package com.teethen.xsdk.ninegrid.simple;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teethen.sdk.xutil.ToastUtil;
import com.teethen.sdk.xwidget.ninegrid.simple.ItemImageClickListener;
import com.teethen.sdk.xwidget.ninegrid.simple.ItemImageLongClickListener;
import com.teethen.sdk.xwidget.ninegrid.simple.NineGridImageView;
import com.teethen.sdk.xwidget.ninegrid.simple.NineGridImageViewAdapter;
import com.teethen.sdk.xwidget.photoview.PhotoViewUtil;
import com.teethen.xsdk.R;

import java.util.List;

/**
 * Created by xingq on 2018/3/1.
 */

public class SimpleNineGridAdapter extends RecyclerView.Adapter<SimpleNineGridAdapter.ViewHolder> {

    public Context mContext;
    public List<String> urlList;

    public SimpleNineGridAdapter(Context context, List<String> list){
        this.mContext = context;
        this.urlList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_ninegrid_simple, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(urlList);
    }

    @Override
    public int getItemCount() {
        return urlList == null ? 0 : urlList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        NineGridImageView<String> nineGridView;

        public ViewHolder(View itemView) {
            super(itemView);
            nineGridView = (NineGridImageView) itemView.findViewById(R.id.ninegrid_iv);

            NineGridImageViewAdapter<String> adapter = new NineGridImageViewAdapter<String>() {
                @Override
                protected void onDisplayImage(Context context, ImageView imageView, String s) {
                    Glide.with(context).load(s).placeholder(R.drawable.default_image).error(R.drawable.error_image).into(imageView);
                }
            };

            nineGridView.setAdapter(adapter);

            nineGridView.setItemImageClickListener(new ItemImageClickListener<String>() {
                @Override
                public void onItemImageClick(Context context, ImageView imageView, int index, List<String> list) {
                    PhotoViewUtil.startViewWithGlide(context, list.get(index));
                    //PhotoViewUtil.startViewPagerWithUrl(context, list);
                }
            });

            nineGridView.setItemImageLongClickListener(new ItemImageLongClickListener<String>() {
                @Override
                public void onItemImageLongClick(Context context, ImageView imageView, int index, List<String> list) {
                    ToastUtil.showToast(mContext, "长按功能暂未写");
                }
            });
        }

        public void bind(List<String> urls) {
            nineGridView.setImagesData(urls, 0);
        }
    }

}
