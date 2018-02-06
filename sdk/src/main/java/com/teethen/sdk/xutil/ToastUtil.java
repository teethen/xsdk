package com.teethen.sdk.xutil;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.teethen.sdk.R;

/**
 * Created by xingq on 2018/1/29.
 */

public class ToastUtil {

    private static Toast mToast;
    private static View mView;
    private static int mXOffset = 0;
    private static int mYOffset = 64;

    public static void showToast(Context context, String text, int... position) {

        if (mToast == null) {
            ToastUtil.mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        }

        if (position != null && position.length > 0) {
            int gravity = position[0];
            switch (gravity) {
                case Gravity.BOTTOM:
                    mToast.setGravity(gravity, mXOffset, mYOffset);
                    break;
                case Gravity.CENTER:
                    mToast.setGravity(gravity, mXOffset, 0);
                    break;
                case Gravity.TOP:
                    mToast.setGravity(gravity, mXOffset, mYOffset);
                    break;
            }
        }

        if (mView == null) {
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ToastUtil.mView = inflate.inflate(R.layout.layout_toast, null);
        }

        mToast.setView(mView);
        mToast.setText(text);
        mToast.show();
    }

    public static void showLongToast(Context context, String text, int... position) {
        if (mToast == null) {
            ToastUtil.mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        }

        if (position != null && position.length > 0) {
            int gravity = position[0];
            switch (gravity) {
                case Gravity.BOTTOM:
                    mToast.setGravity(gravity, mXOffset, mYOffset);
                    break;
                case Gravity.CENTER:
                    mToast.setGravity(gravity, mXOffset, 0);
                    break;
                case Gravity.TOP:
                    mToast.setGravity(gravity, mXOffset, mYOffset);
                    break;
            }
        }

        if (mView == null) {
            LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ToastUtil.mView = inflate.inflate(R.layout.layout_toast, null);
        }

        mToast.setView(mView);
        mToast.setText(text);
        mToast.show();
    }

}
