package com.teethen.sdk.xutil;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.teethen.sdk.R;

/**
 * Created by xingq on 2017/12/1.
 */

public class StatusBarUtil {

    private static int DEFAULT_COLOR_ALPHA = 112;
    /**
     * 全屏显示
     * @param activity 上下文
     * @param hideNavigationBar 是否隐藏底部导航栏
     * @param colorResId 状态栏和导航栏的颜色
     */
    public static void fullScreen(Activity activity, boolean hideNavigationBar, int... colorResId) {
        Window window = activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        int iView = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                //| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        if (hideNavigationBar) {
            iView |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        //控制底部虚拟键盘
        window.getDecorView().setSystemUiVisibility(iView);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if (colorResId != null && colorResId.length == 1) {
            setBarsColor(activity, colorResId[0]);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setNavigationBarColor(Color.TRANSPARENT);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                SystemBarUtil tintManager = new SystemBarUtil(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(Color.TRANSPARENT);
                tintManager.setNavigationBarTintEnabled(true);
                tintManager.setNavigationBarTintColor(Color.TRANSPARENT);
            }
        }
    }

    /**
     * 全屏显示
     * @param activity 上下文
     */
    public static void fullScreen(Activity activity) {
        Window window = activity.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        int iView = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN      // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        window.getDecorView().setSystemUiVisibility(iView);
    }

    /**
     * 设置状态栏和导航栏的颜色, 不传颜色值则默认为透明色
     * @param activity
     * @param colorId
     */
    public static void setBarsColor(Activity activity, int colorId, int... alpha) {
        int color = Color.TRANSPARENT;
        if (colorId != Color.TRANSPARENT) {
            color = activity.getResources().getColor(colorId);
            if (alpha != null && alpha.length == 1) {
                color = getAlphaColor(color, alpha[0]);
            } else {
                color = getAlphaColor(color, DEFAULT_COLOR_ALPHA);
            }
        }

        Window window = activity.getWindow();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(color);
                window.setNavigationBarColor(color);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (color == Color.TRANSPARENT) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                } else {
                    SystemBarUtil tintManager = new SystemBarUtil(activity);
                    tintManager.setStatusBarTintEnabled(true);
                    tintManager.setStatusBarTintColor(color);
                    tintManager.setNavigationBarTintEnabled(true);
                    tintManager.setNavigationBarTintColor(color);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Get alpha color
    private static int getAlphaColor(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }


    /**
     * 设置沉浸式状态栏
     * @param context
     * @param translucentStatusBar 状态栏是否半透明
     * @param sameToNavigationBar 是否同步状态栏颜色到底部导航栏
     */
    public static void setStatusBarTint(Activity context, boolean translucentStatusBar, boolean sameToNavigationBar) {
        Window window = context.getWindow();
        int colorId = getColorPrimary(context);
        if (translucentStatusBar) { //半透明状态栏
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                if (sameToNavigationBar) {
                    window.setNavigationBarColor(Color.TRANSPARENT);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorId);
                if (sameToNavigationBar) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    window.setNavigationBarColor(colorId);
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                SystemBarUtil tintManager = new SystemBarUtil(context);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(colorId);
                if (sameToNavigationBar) {
                    tintManager.setNavigationBarTintEnabled(true);
                    tintManager.setNavigationBarTintColor(colorId);
                }
            }
        }
    }

    public static int getColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    public static int getDarkColorPrimary(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark, typedValue, true);
        return typedValue.data;
    }
}
