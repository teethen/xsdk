package com.teethen.sdk.xwidget.glide;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.teethen.sdk.R;
import com.teethen.sdk.xutil.ToastUtil;

/**
 * Created by xingq on 2018/2/2.
 */

public class GlideUtil {

    private Context mContext;
    private ProgressDialog dialog;

    public void clearDiskCache(Context context) {

        this.mContext = context;
        new ClearCacheTask().execute();

        /*new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                // This method must be called on a background thread.
                Glide.get(App.getAppContext()).clearDiskCache();
                return null;
            }
        };*/
    }

    class ClearCacheTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading("正在清理，请稍等....");
        }

        @Override
        protected Void doInBackground(Void... params) {
            // This method must be called on a background thread.
            Glide.get(mContext).clearDiskCache();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dismissLoading();
            ToastUtil.showToast(mContext, "清理成功");
        }
    }

    public void showLoading(String msg) {
        if (dialog != null && dialog.isShowing()) {
            return;
        }
        dialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_DARK);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(TextUtils.isEmpty(msg)) {
            msg = mContext.getResources().getString(R.string.load_loading);
        }
        dialog.setMessage(msg);
        dialog.show();

        //下面是show后设置背景透明度,位置等信息
        Point size = new Point();
        dialog.getWindow().getWindowManager().getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.alpha = 0.8f;
        params.height = height / 8;
        params.gravity = Gravity.CENTER;
        params.width = 4 * width / 5;
        params.dimAmount = 0f; //半透明背景灰度(范围0~1)，默认:0.5，1:背景全黑，0:背景不变暗(和原来灰度一样)
        dialog.getWindow().setAttributes(params);
    }

    public void dismissLoading() {
        if (dialog != null && dialog.isShowing()) { dialog.dismiss(); }
    }

}

