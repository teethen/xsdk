package com.teethen.xsdk.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.teethen.sdk.xbanner.XBanner;
import com.teethen.sdk.xbanner.transformer.TransitionEffect;
import com.teethen.xsdk.R;
import com.teethen.xsdk.test.PhotoViewTest;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends BaseActivity implements XBanner.Adapter<ImageView, String>, XBanner.Delegate<ImageView, String> {

    private XBanner mBanner;
    private XBanner mBannerDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);

        initToolBar(R.id.toolbar, "BannerTest");

        initBanner();
        loadBannerData();
        loadDefaultBannerData();
    }

    @Override
    public void fillBannerItem(XBanner banner, ImageView itemView, @Nullable String model, int position) {
        Glide.with(this)
                .load(model)
                .placeholder(R.drawable.ic_banner_placehoder)
                .into(itemView);
    }

    @Override
    public void onBannerItemClick(XBanner banner, ImageView itemView, @Nullable String model, int position) {
        showToast("点击了第" + (position + 1) + "页", Gravity.BOTTOM);
    }

    private void initBanner() {
        mBanner = findViewById(R.id.banner_test);
        mBanner.setAdapter(this);
        mBanner.setDelegate(this);

        mBannerDefault = findViewById(R.id.banner_default);
        mBannerDefault.setAdapter(this);
        mBannerDefault.setDelegate(this);
    }

    /**
     * 加载头部广告条的数据
     */
    private void loadBannerData() {
        BannerModel bannerModel = new BannerModel();
        List<String> imgs = PhotoViewTest.getBaiduImgs().subList(0, 5);
        List<String> tips = new ArrayList<>();
        tips.add("第1页提示文案");
        tips.add("第2页提示文案");
        tips.add("第3页提示文案");
        tips.add("第4页提示文案");
        tips.add("第5页提示文案");
        bannerModel.setImgs(imgs);
        bannerModel.setTips(tips);

        mBanner.setData(bannerModel.imgs, bannerModel.tips);
    }

    /**
     * 加载头部广告条的数据
     */
    private void loadDefaultBannerData() {
        BannerModel bannerModel = new BannerModel();
        List<String> imgs = PhotoViewTest.getBaiduImgs().subList(1, 5);
        List<String> tips = new ArrayList<>();
        tips.add("第1条文案");
        tips.add("第2条文案");
        tips.add("第3条文案");
        tips.add("第4条文案");
        bannerModel.setImgs(imgs);
        bannerModel.setTips(tips);

        mBannerDefault.setData(bannerModel.imgs, bannerModel.tips);
    }

    private class BannerModel {
        public List<String> imgs;
        public List<String> tips;

        public List<String> getImgs() {
            return imgs;
        }

        public void setImgs(List<String> imgs) {
            this.imgs = imgs;
        }

        public List<String> getTips() {
            return tips;
        }

        public void setTips(List<String> tips) {
            this.tips = tips;
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_main_enable_auto_play:
                /**
                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                 */
                mBannerDefault.setAutoPlayAble(true);
                break;
            case R.id.tv_main_disable_auto_play:
                /**
                 * 设置是否开启自动轮播，需要在 setData 方法之前调用，并且调了该方法后必须再调用一次 setData 方法
                 * 例如根据图片当图片数量大于 1 时开启自动轮播，等于 1 时不开启自动轮播
                 */
                mBannerDefault.setAutoPlayAble(false);
                break;
            case R.id.tv_main_start_auto_play:
                // 仅在 autoPlayAble 为 true 时才会生效「开发者使用该库时不用调用该方法，这里只是为了演示而已，界面可见时在 BGABanner 内部已经帮开发者调用了该方方法」
                mBannerDefault.startAutoPlay();
                break;
            case R.id.tv_main_stop_auto_play:
                // 仅在 autoPlayAble 为 true 时才会生效「开发者使用该库时不用调用该方法，这里只是为了演示而已，界面不可见时在 BGABanner 内部已经帮开发者调用了该方方法」
                mBannerDefault.stopAutoPlay();
                break;
            case R.id.tv_main_cube:
                mBannerDefault.setTransitionEffect(TransitionEffect.Cube);
                break;
            case R.id.tv_main_depth:
                mBanner.setTransitionEffect(TransitionEffect.Depth);
                break;
            case R.id.tv_main_flip:
                mBannerDefault.setTransitionEffect(TransitionEffect.Flip);
                break;
            case R.id.tv_main_rotate:
                mBannerDefault.setTransitionEffect(TransitionEffect.Rotate);
                break;
            case R.id.tv_main_alpha:
                mBannerDefault.setTransitionEffect(TransitionEffect.Alpha);
                break;
            default:
                break;
        }
    }
}
