package com.teethen.sdk.xutil;

import android.app.Activity;
import android.app.Dialog;
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
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        if (hideNavigationBar) {
            iView |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        }

        //控制底部虚拟键盘
        window.getDecorView().setSystemUiVisibility(iView);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if (colorResId != null && colorResId.length == 1) {
            setAllBarsColor(activity, colorResId[0]);
        }
    }

    /**
     * 设置状态栏和导航栏的颜色
     * @param activity
     * @param colorResId
     */
    public static void setAllBarsColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(activity.getResources().getColor(colorResId));
                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                SystemBarUtil tintManager = new SystemBarUtil(activity);
                tintManager.setStatusBarTintEnabled(true);
                tintManager.setStatusBarTintColor(colorResId);
                tintManager.setNavigationBarTintEnabled(true);
                tintManager.setNavigationBarTintColor(colorResId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setWindowStatusBarColor(Dialog dialog, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = dialog.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(dialog.getContext().getResources().getColor(colorResId));

                //底部导航栏
                window.setNavigationBarColor(dialog.getContext().getResources().getColor(colorResId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setNavigationBarColor(Activity activity, int colorResId) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                //底部导航栏
                window.setNavigationBarColor(activity.getResources().getColor(colorResId));
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                SystemBarUtil tintManager = new SystemBarUtil(activity);
                tintManager.setNavigationBarTintEnabled(true);
                tintManager.setNavigationBarTintColor(colorResId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置沉浸式状态栏
     * @param context
     * @param sameToNavigationBar 是否同步状态栏颜色到底部导航栏
     * @param translucentStatusBar 状态栏是否半透明
     */
    public static void setStatusBarTint(Activity context, boolean sameToNavigationBar, boolean translucentStatusBar) {
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
            return;
        }
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
