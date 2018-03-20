package com.teethen.xsdk.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teethen.sdk.xutil.LayoutUtil;
import com.teethen.xsdk.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

public class ImagePickerActivity extends BaseActivity {

    private int REQUEST_CODE_CHOOSE = 24;
    private String AUTHORITY;
    private List<Uri> mSelected;

    private Button button1, button2;
    private RecyclerView recyclerView;
    private UriAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        initToolBar(R.id.toolbar, "ImagePicker Test");

        initView();
        setupListener();
    }

    private void initView() {
        AUTHORITY = getApplicationInfo().packageName + ".fileProvider";//BuildConfig.APPLICATION_ID + ".fileProvider"
        button1 = findViewById(R.id.btn_zhihu);
        button2 = findViewById(R.id.btn_dracula);

        recyclerView = findViewById(R.id.rv_matisse_imglist);
        recyclerView.setLayoutManager(LayoutUtil.getLinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter = new UriAdapter());
    }

    private void setupListener() {
        button1.setOnClickListener((v)->{

            Log.d("Matisse", AUTHORITY);

            Matisse.from(this)
                    .choose(MimeType.allOf()) //选择 mime 的类型
                    .countable(true)
                    .capture(true) //启用相机
                    .captureStrategy(new CaptureStrategy(true, AUTHORITY)) //自定义FileProvider
                    .maxSelectable(9) //图片选择的最多数量
                    .theme(R.style.Matisse_Zhihu) //R.style.Matisse_Zhihu | R.style.Matisse_Dracula
                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.image120)) //相册中图片显示大小
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f) //缩略图的比例
                    .imageEngine(new GlideEngine()) //使用的图片加载引擎
                    .forResult(REQUEST_CODE_CHOOSE); //设置作为标记的请求码
        });

        button2.setOnClickListener((v -> {
            Matisse.from(this)
                    .choose(MimeType.allOf())
                    .countable(false)
                    .maxSelectable(9)
                    .theme(R.style.Matisse_Dracula) //R.style.Matisse_Zhihu | R.style.Matisse_Dracula
                    .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.image120)) //相册中图片显示大小
                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                    .thumbnailScale(0.85f)
                    .imageEngine(new GlideEngine())
                    .forResult(REQUEST_CODE_CHOOSE);
        }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            mAdapter.setData(this, mSelected);
            Log.d("Matisse", "mSelected: " + mSelected);
        }
    }

    private static class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

        private Context mContext;
        private List<Uri> mUris;

        void setData(Context context, List<Uri> uris) {
            mContext = context;
            mUris = uris;
            notifyDataSetChanged();
        }

        @Override
        public UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new UriViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_matisse_uri, parent, false));
        }

        @Override
        public void onBindViewHolder(UriViewHolder holder, int position) {
            holder.mUri.setText(mUris.get(position).toString());
            Glide.with(mContext).load(mUris.get(position)).placeholder(R.drawable.default_image).into(holder.mImageView);
            holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
        }

        @Override
        public int getItemCount() {
            return mUris == null ? 0 : mUris.size();
        }

        static class UriViewHolder extends RecyclerView.ViewHolder {
            private TextView mUri;
            private ImageView mImageView;

            UriViewHolder(View contentView) {
                super(contentView);
                mUri = contentView.findViewById(R.id.uri_matisse);
                mImageView = contentView.findViewById(R.id.iv_matisse);
            }
        }
    }

}
