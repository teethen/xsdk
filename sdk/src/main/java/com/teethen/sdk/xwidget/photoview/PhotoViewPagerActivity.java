package com.teethen.sdk.xwidget.photoview;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.teethen.sdk.R;
import com.teethen.sdk.base.BaseActivity;
import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xwidget.photoview.widget.PhotoView;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewPagerActivity extends BaseActivity {

    /**
     * 图片资源数据(List<Integer> or List<Uri>)
     */
    public static String TAG_IMG_RES_DATA = "img_resource_data";
    /**
     * 图片资源类型(XConstant.IMG_RES_TYPE_ID / XConstant.IMG_RES_TYPE_URL / XConstant.IMG_RES_TYPE_URI)
     */
    public static String TAG_IMG_RES_TYPE = "img_resource_type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photoview_pager);

        try {
            ViewPager viewPager = findViewById(R.id.iv_photoview_pager);
            int imgResourceType = getIntent().getIntExtra(TAG_IMG_RES_TYPE, XConstant.IMG_RES_TYPE_ID);

            PhotoViewPagerAdapter adapter = null;
            if (imgResourceType == XConstant.IMG_RES_TYPE_ID) {
                List<Integer> idList = getIntent().getIntegerArrayListExtra(TAG_IMG_RES_DATA);
                adapter = new PhotoViewPagerAdapter(idList);
            } else if (imgResourceType == XConstant.IMG_RES_TYPE_URL) {
                List<String> urlList = getIntent().getStringArrayListExtra(TAG_IMG_RES_DATA);
                adapter = new PhotoViewPagerAdapter(imgResourceType, urlList);
            } else if (imgResourceType == XConstant.IMG_RES_TYPE_URI) {
                List<Uri> uriList = (ArrayList<Uri>) getIntent().getSerializableExtra(TAG_IMG_RES_DATA);
                adapter = new PhotoViewPagerAdapter(uriList, imgResourceType);
            }

            viewPager.setAdapter(adapter);
        } catch (Exception e) {
            showToast(e.toString());
        }
    }

    static class PhotoViewPagerAdapter extends PagerAdapter {

        private Context mContext;
        private int mImgResType = XConstant.IMG_RES_TYPE_ID;
        private List<Integer> mImgIdList;
        private List<String> mImgUrlList;
        private List<Uri> mImgUriList;

        public PhotoViewPagerAdapter(){ }

        public PhotoViewPagerAdapter(List<Integer> imgIdList){
            mImgResType = XConstant.IMG_RES_TYPE_ID;
            mImgIdList = imgIdList;
        }
        public PhotoViewPagerAdapter(int imgResType, List<String> imgUrlList){
            mImgResType = imgResType;
            mImgUrlList = imgUrlList;
        }
        public PhotoViewPagerAdapter(List<Uri> imgUriList, int imgResType){
            mImgResType = imgResType;
            mImgUriList = imgUriList;
        }

        @Override
        public int getCount() {
            if (mImgResType == XConstant.IMG_RES_TYPE_ID) {
                return mImgIdList == null ? 0 : mImgIdList.size();
            } else if (mImgResType == XConstant.IMG_RES_TYPE_URL) {
                return mImgUrlList == null ? 0 : mImgUrlList.size();
            } else if (mImgResType == XConstant.IMG_RES_TYPE_URI) {
                return mImgUriList == null ? 0 : mImgUriList.size();
            } else {
                return 0;
            }
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            if(mContext == null) mContext = container.getContext();
            PhotoView photoView = new PhotoView(mContext);

            if (mImgResType == XConstant.IMG_RES_TYPE_ID) {
                photoView.setImageResource(mImgIdList.get(position));
            } else if (mImgResType == XConstant.IMG_RES_TYPE_URL) {
                photoView.setImageUrl(mContext, mImgUrlList.get(position));
            } else if (mImgResType == XConstant.IMG_RES_TYPE_URI) {
                photoView.setImageURI(mImgUriList.get(position));
            }

            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
