package com.teethen.xsdk.ninegrid.complex.news;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teethen.sdk.xwidget.ninegrid.complex.ImageInfo;
import com.teethen.sdk.xwidget.ninegrid.complex.NineGridView;
import com.teethen.sdk.xwidget.ninegrid.preview.NineGridViewClickAdapter;
import com.teethen.xsdk.R;
import com.teethen.xsdk.ninegrid.complex.news.bean.NewsContent;
import com.teethen.xsdk.ninegrid.complex.news.bean.NewsImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsContentAdapter extends RecyclerView.Adapter<NewsContentAdapter.PostViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<NewsContent> mDataList;

    public NewsContentAdapter(Context context, List<NewsContent> data) {
        super();
        mContext = context;
        mDataList = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        holder.bind(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostViewHolder(mInflater.inflate(R.layout.item_news, parent, false));
    }

    public void setData(List<NewsContent> data) {
        mDataList = data;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.title) TextView title;
        @BindView(R.id.nineGrid) NineGridView nineGrid;
        @BindView(R.id.desc) TextView desc;
        @BindView(R.id.pubDate) TextView pubDate;
        @BindView(R.id.source) TextView source;
        private NewsContent item;
        private View itemView;

        public PostViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }

        public void bind(NewsContent item) {
            this.item = item;
            title.setText(item.getTitle());
            desc.setText(item.getDesc());
            pubDate.setText(item.getPubDate());
            source.setText(item.getSource());

            ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            List<NewsImage> images = item.getImageUrls();
            if (images != null) {
                for (NewsImage image : images) {
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(image.getUrl());
                    info.setBigImageUrl(image.getUrl());
                    imageInfo.add(info);
                }
            }
            nineGrid.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));

            if (images != null && images.size() == 1) {
                nineGrid.setSingleImageRatio(images.get(0).getWidth() * 1.0f / images.get(0).getHeight());
            }

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(mContext, NewsLinkActivity.class);
            intent.putExtra(NewsLinkActivity.TAG_LINK_URL, item.getLink());
            mContext.startActivity(intent);
        }
    }
}
