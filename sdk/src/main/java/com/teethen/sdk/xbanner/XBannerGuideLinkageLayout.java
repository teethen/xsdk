package com.teethen.sdk.xbanner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 描述:引导界面联动布局，将每一个触摸事件分发给所有的子控件。
 */

public class XBannerGuideLinkageLayout extends FrameLayout {
    public XBannerGuideLinkageLayout(Context context) {
        super(context);
    }

    public XBannerGuideLinkageLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XBannerGuideLinkageLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            try {
                child.dispatchTouchEvent(ev);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
