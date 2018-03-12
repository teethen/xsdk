package com.teethen.sdk.xutil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.teethen.sdk.R;
import com.teethen.sdk.callback.Callback;
import com.teethen.sdk.xdialog.colordialog.ColorDialog;
import com.teethen.sdk.xdialog.colordialog.PromptDialog;
import com.teethen.sdk.xdialog.fragdialog.Dialoger;

/**
 * Created by xingq on 2017/9/22.
 */

public class DialogUtil {

    /**
     * 弹出AlertDialog(只有一个确定按钮)
     * @param context
     * @param title
     * @param msg
     * @param callback
     */
    public static void showAlert(Context context, CharSequence title, CharSequence msg, final Callback callback) {
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

    /**
     * 弹出标准的AlertDialog(包含确定和取消按钮)
     * @param context
     * @param title
     * @param msg
     * @param cancelable
     * @param callback
     */
    public static void showAlertDialog(Context context, CharSequence title, CharSequence msg, boolean cancelable, final Callback callback) {
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

    public static void showColorDialog(final Context context, String title, String contentText,
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

    public static void showColorDialog(final Context context, String title, String contentText,
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

    public static void showColorDialog(final Context context, String title, String contentText,
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

    public static void showColorDialog(final Context context, String title, String contentText, String btnText, int resImgId, boolean cancelable) {
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

    /**
     * 弹出对话框(图标 + 背景色 + 一个按钮)
     * DialogUtil.showPromptDialog(this, "标题", "内容", PromptDialog.DIALOG_TYPE_SUCCESS, null, null);
     * @param context
     * @param title
     * @param contentText
     * @param dialogType (对话框类型: 信息, 帮助, 成功, 错误, 警告) DIALOG_TYPE_SUCCESS
     * @param btnText
     * @param callback
     */
    public static void showPromptDialog(Context context, String title, String contentText, int dialogType, @Nullable String btnText, @Nullable final Callback callback) {
        if(TextUtils.isEmpty(btnText)) btnText = context.getString(R.string.btn_ok);
        PromptDialog dialog = new PromptDialog(context)
                .setDialogType(dialogType)
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

    /**
     * 通过DialogFragment弹出浮层,使用默认定义
     * @param activity
     * @param cancelable
     * @param title
     * @param content
     * @param listener
     */
    public static void showPromptDialog(FragmentActivity activity,
                                        boolean cancelable,
                                        String title,
                                        String content,
                                        Dialoger.OnPositiveListener listener) {
        Dialoger dialoger = Dialoger.build(activity)
                .setContentTitle(title)
                .setContentText(content)
                .setNegativeBtn(null, new Dialoger.OnNegativeListener() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveBtn(null, listener);

        if (cancelable) {
            dialoger.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    dialog.cancel();
                }
            });
        }

        dialoger.show(activity.getSupportFragmentManager(), activity.getClass().getSimpleName());
    }


    /**
     * 通过DialogFragment弹出浮层 (可进行较多的自定义)
     * DialogUtil.showPromptDialog(this, false, "提示", "内容", Color.RED, 0, 0, Color.BLUE, null, null, new Dialoger.OnPositiveListener(){});
     * @param activity
     * @param cancelable
     * @param title
     * @param content
     * @param negativeBtnText 左侧按钮文字(默认 取消)
     * @param positiveBtnText 右侧按钮文字(默认 确定)
     * @param contentTextColor 内容颜色(默认 0)
     * @param negativeBtnColor 左侧按钮颜色(默认 0)
     * @param positiveBtnColor 右侧按钮颜色(默认 0)
     */
    public static void showPromptDialog(FragmentActivity activity,
                                        boolean cancelable,
                                        String title,
                                        String content,
                                        int titleColor,
                                        int contentTextColor,
                                        int negativeBtnColor,
                                        int positiveBtnColor,
                                        @Nullable String negativeBtnText,
                                        @Nullable String positiveBtnText,
                                        @Nullable Dialoger.OnPositiveListener listener) {
        Dialoger dialoger = Dialoger.build(activity)
                .setContentTitle(title, titleColor)
                .setContentText(content, contentTextColor)
                .setNegativeColor(negativeBtnColor)
                .setPositiveColor(positiveBtnColor)
                .setNegativeBtn(negativeBtnText, new Dialoger.OnNegativeListener() {
                    @Override
                    public void onNegative(Dialog dialog) {
                        dialog.dismiss();
                    }
                })
                .setPositiveBtn(positiveBtnText, listener);

        if (cancelable) {
            dialoger.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    //view.getViewActivity().finish();
                    dialog.cancel();
                }
            });
        }

        dialoger.show(activity.getSupportFragmentManager(), activity.getClass().getSimpleName());
    }

    /*
    DialogFragment dialog usage example:

    DialogUtil.showPromptDialog(this, false, "提示", "内容",
        new Dialoger.OnPositiveListener() {
            @Override
            public void onPositive(Dialog dialog) {
                dialog.dismiss();
                showToast("您刚才点击了确定按钮");
            }
    });

    DialogUtil.showPromptDialog(this, false, "提示", "内容", Color.RED, 0, 0, Color.BLUE, null, null,
        new Dialoger.OnPositiveListener() {
            @Override
            public void onPositive(Dialog dialog) {
                dialog.dismiss();
                showToast("您刚才点击了确定按钮");
            }
    });*/
}
