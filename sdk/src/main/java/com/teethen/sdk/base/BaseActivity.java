package com.teethen.sdk.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.teethen.sdk.R;
import com.teethen.sdk.xutil.ToastUtil;

/**
 * Created by xingq on 2018/2/6.
 */

public class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void initToolBar(int toolbarId, String title, boolean... homeAsUpEnabled) {
        Toolbar toolbar = (Toolbar) findViewById(toolbarId);
        initToolBar(toolbar, title, homeAsUpEnabled);
    }

    public void initToolBar(Toolbar toolbar, String title, boolean... homeAsUpEnabled) {
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(title);
            boolean homeAsUp = true;
            if (homeAsUpEnabled != null && homeAsUpEnabled.length > 0) {
                homeAsUp = homeAsUpEnabled[0];
            }
            getSupportActionBar().setDisplayHomeAsUpEnabled(homeAsUp);
        }
    }

    /**
     * 弹出 指定位置的 Toast
     *
     * @param text  文本
     * @param position  位置((默认)下:Gravity.BOTTOM，中:Gravity.CENTER，上:Gravity.TOP)
     * @return
     */
    public void showToast(String text, int... position) {
        int gravity = Gravity.BOTTOM;
        if (position != null && position.length > 0) { gravity = position[0]; }
        if (gravity == Gravity.BOTTOM) {
            ToastUtil.showToast(this, text);
        } else if(gravity == Gravity.CENTER) {
            ToastUtil.showToast(this, text, Gravity.CENTER);
        } else if (gravity == Gravity.TOP) {
            ToastUtil.showToast(this, text, Gravity.TOP);
        }
    }

    public void showLongToast(String msg) {
        ToastUtil.showLongToast(this, msg);
    }

    public void showAlertDialog(String title, @NonNull String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(!TextUtils.isEmpty(title)) builder.setTitle(title);
        builder.setMessage(msg);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public void showLoading(String msg, int theme, String... title) {
        if (dialog != null && dialog.isShowing()) { return; }
        if (theme == XConstant.PROGRESS_DLG_THEME_DARK) {
            dialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
        } else {
            dialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if(title != null && title.length > 0) { dialog.setTitle(title[0]); }
        if(TextUtils.isEmpty(msg)) { msg = getResources().getString(R.string.load_loading); }
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
