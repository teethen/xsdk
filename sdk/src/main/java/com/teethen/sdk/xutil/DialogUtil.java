package com.teethen.sdk.xutil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.teethen.sdk.R;
import com.teethen.sdk.callback.Callback;
import com.teethen.sdk.xdialog.colordialog.ColorDialog;
import com.teethen.sdk.xdialog.colordialog.PromptDialog;

/**
 * Created by xingq on 2017/9/22.
 */

public class DialogUtil {

    public static void showAlertOne(Context context, CharSequence title, CharSequence msg, final Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setCancelable(false).setMessage(msg)
                .setPositiveButton(context.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callback != null) callback.callback();
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public static void showAlertTwo(Context context, CharSequence title, CharSequence msg, boolean cancelable, final Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title).setMessage(msg)
                .setCancelable(cancelable)
                .setNegativeButton(context.getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(context.getString(R.string.btn_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(callback != null) callback.callback();
                        dialog.dismiss();
                    }
                });
                builder.show();
    }

    public static void showDialog(final Context context, String title, String contentText,
                                  String btnOkText, String btnCancelText,
                                  boolean cancelable, final Callback callback) {
        if(TextUtils.isEmpty(btnOkText)) btnOkText = context.getString(R.string.btn_ok);
        if(TextUtils.isEmpty(btnCancelText)) btnCancelText = context.getString(R.string.btn_cancel);

        ColorDialog dialog = new ColorDialog(context);
        dialog.setTitle(title);
        dialog.setContentText(contentText);
        dialog.setCancelable(cancelable);
        dialog.setPositiveListener(btnOkText, new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                if(callback != null) callback.callback();
                dialog.dismiss();
            }
        }).setNegativeListener(btnCancelText, new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.cancel();
            }
        }).show();
    }

    public static void showDialog(final Context context, String title, String contentText,
                                  String btnOkText, String btnCancelText, int resImgId,
                                  boolean cancelable, final Callback callback) {
        if(TextUtils.isEmpty(btnOkText)) btnOkText = context.getString(R.string.btn_ok);
        if(TextUtils.isEmpty(btnCancelText)) btnCancelText = context.getString(R.string.btn_cancel);

        ColorDialog dialog = new ColorDialog(context);
        dialog.setTitle(title);
        dialog.setContentText(contentText);
        dialog.setCancelable(cancelable);
        if (resImgId > 0) {
            dialog.setContentImage(context.getResources().getDrawable(resImgId));
        }
        dialog.setPositiveListener(btnOkText, new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                if(callback != null) callback.callback();
                dialog.dismiss();
            }
        }).setNegativeListener(btnCancelText, new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.cancel();
            }
        }).show();
    }

    public static void showDialog(final Context context, String title, String contentText,
                                  String btnOkText, String btnCancelText, int resImgId,
                                  boolean cancelable, final Callback callbackOk, final Callback callbackCancel) {
        if(TextUtils.isEmpty(btnOkText)) btnOkText = context.getString(R.string.btn_ok);
        if(TextUtils.isEmpty(btnCancelText)) btnCancelText = context.getString(R.string.btn_cancel);

        ColorDialog dialog = new ColorDialog(context);
        dialog.setTitle(title);
        dialog.setContentText(contentText);
        dialog.setCancelable(cancelable);
        if (resImgId > 0) {
            dialog.setContentImage(context.getResources().getDrawable(resImgId));
        }
        dialog.setPositiveListener(btnOkText, new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                if(callbackOk != null) callbackOk.callback();
                dialog.dismiss();
            }
        }).setNegativeListener(btnCancelText, new ColorDialog.OnNegativeListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                if(callbackCancel != null) callbackCancel.callback();
                dialog.cancel();
            }
        }).show();
    }

    public static void showDialog(final Context context, String title, String contentText, String btnText, int resImgId, boolean cancelable) {
        if(TextUtils.isEmpty(btnText)) btnText = context.getString(R.string.btn_ok);
        ColorDialog dialog = new ColorDialog(context);
        dialog.setTitle(title);
        dialog.setContentText(contentText);
        dialog.setCancelable(cancelable);
        if (resImgId > 0) {
            dialog.setContentImage(context.getResources().getDrawable(resImgId));
        }
        dialog.setPositiveListener(btnText, new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                dialog.dismiss();
            }
        }).show();
    }

    public static void showDialog(Context context, String title, String contentText, String btnText, @Nullable final Callback callback) {
        if(TextUtils.isEmpty(btnText)) btnText = context.getString(R.string.btn_ok);
        PromptDialog dialog = new PromptDialog(context)
                .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                .setAnimationEnable(true)
                .setTitleText(title)
                .setContentText(contentText)
                .setPositiveListener(btnText, new PromptDialog.OnPositiveListener() {
                    @Override
                    public void onClick(PromptDialog dialog) {
                        if (callback != null) {
                            callback.callback();
                        }
                        dialog.dismiss();
                    }
                });
        dialog.setCancelable(false);
        dialog.show();
    }
}
