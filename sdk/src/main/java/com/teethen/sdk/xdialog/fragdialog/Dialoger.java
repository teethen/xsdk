package com.teethen.sdk.xdialog.fragdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.teethen.sdk.R;

/**
 * Created by xingq on 2018/3/3.
 *
 * 通过 DialogFragment 弹出浮层
 */

public class Dialoger extends DialogFragment implements View.OnClickListener {

    public interface OnPositiveListener {
        void onPositive(Dialog dialog);
    }

    public interface OnNegativeListener {
        void onNegative(Dialog dialog);
    }

    private Activity mActivity;
    private View mContentView;

    private String mContentTitle;
    private int mContentTitleColor;

    private String mContentText;
    private int mContentTextColor;

    private String mNegativeText;
    private int mNegativeTextColor;
    private OnNegativeListener mNegativeListener;

    private String mPositiveText;
    private int mPositiveTextColor;
    private OnPositiveListener mPositiveListener;

    private DialogInterface.OnCancelListener mCancelListener;
    private String mTransactionTag;

    public static Dialoger build(FragmentActivity activity, String transactionTag, Bundle args) {
        Dialoger dialoger = new Dialoger();
        dialoger.setActivity(activity);//设置传入的activity对象
        dialoger.setTransactionTag(transactionTag);//设置的tag标志（和Fragment一样，便于利用tag获取当前的Activity对象，来传递数据)
        dialoger.setArguments(args);//设置的bundle参数
        dialoger.setCancelable(false);//初始化设置为不可点击
        dialoger.setTheme(0);//无主题
        dialoger.setContentView(R.layout.layout_fragmentdialog);//设置默认的dialog的View
        return dialoger;
    }

    public static Dialoger build(FragmentActivity activity) {
        return build(activity, Dialoger.class.getSimpleName(), null);
    }

    private void setActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    private void setTransactionTag(String transactionTag) {
        this.mTransactionTag = transactionTag;
    }

    public Dialoger setContentView(int layoutResID) {
        this.mContentView = LayoutInflater.from(mActivity).inflate(layoutResID, null);
        return this;
    }

    public Dialoger setContentView(View view) {
        this.mContentView = view;
        return this;
    }

    public Dialoger setTheme(int theme) {
        setStyle(DialogFragment.STYLE_NO_TITLE, theme);
        return this;
    }

    public Dialoger setContentTitle(String title) {
        mContentTitle = title;
        return this;
    }

    public Dialoger setContentText(String text) {
        mContentText = text;
        return this;
    }

    public Dialoger setContentTitle(String title, int titleColor) {
        mContentTitle = title;
        mContentTitleColor = titleColor;
        return this;
    }

    public Dialoger setContentText(String text, int textColor) {
        mContentText = text;
        mContentTextColor = textColor;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//设置透明背景
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (mCancelListener != null) {//只有当设置了取消监听的时候，才可以设置取消
            setCancelable(true);
        }
        //设置标题
        TextView mTitleView = (TextView) view.findViewById(R.id.dialog_title_tv);
        if (!TextUtils.isEmpty(mContentTitle)) {
            mTitleView.setText(mContentTitle);
            if (mContentTitleColor != 0) {
                mTitleView.setTextColor(mContentTitleColor);
            }
        } else {
            mTitleView.setVisibility(View.GONE);
        }
        //设置内容
        TextView mTextView = (TextView) view.findViewById(R.id.dialog_text_tv);
        if (!TextUtils.isEmpty(mContentText)) {
            mTextView.setText(mContentText);
            if (mContentTextColor != 0) {
                mTextView.setTextColor(mContentTextColor);
            }
        } else {
            mTextView.setVisibility(View.GONE);
        }

        //设置 确定 和 取消 按钮
        View buttonPanel = view.findViewById(R.id.buttons_layout);
        buttonPanel.setVisibility(View.GONE);

        boolean negativeEnable = false;
        boolean positiveEnable = false;
        //如果有确定按钮 设置 ----->可点击 内容 监听
        Button negativeButton = (Button) view.findViewById(R.id.btn_negative);
        if (TextUtils.isEmpty(mNegativeText)) {
            negativeButton.setVisibility(View.GONE);
        } else {
            negativeEnable = true;
            buttonPanel.setVisibility(View.VISIBLE);
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setText(mNegativeText);
            negativeButton.setOnClickListener(this);
            if (mNegativeTextColor != 0) {
                negativeButton.setTextColor(mNegativeTextColor);
            }
        }
        //如果有取消按钮 设置 ----->可点击 内容 监听
        Button positiveButton = (Button) view.findViewById(R.id.btn_positive);
        if (TextUtils.isEmpty(mPositiveText)) {
            positiveButton.setVisibility(View.GONE);
        } else {
            positiveEnable = true;
            buttonPanel.setVisibility(View.VISIBLE);
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setText(mPositiveText);
            positiveButton.setOnClickListener(this);

            if (mPositiveTextColor != 0) {
                positiveButton.setTextColor(mPositiveTextColor);
            }
        }

        View contentDivider = view.findViewById(R.id.horizontal_divider);
        View buttonDivider = view.findViewById(R.id.vertical_divider);

        //设置布局的分割线 显示和隐藏
        if (positiveEnable) {
            if (negativeEnable) {
                contentDivider.setVisibility(View.VISIBLE);
                buttonDivider.setVisibility(View.VISIBLE);
            } else {
                contentDivider.setVisibility(View.GONE);
                buttonDivider.setVisibility(View.GONE);
            }
            buttonPanel.setVisibility(View.VISIBLE);
        } else {
            if (negativeEnable) {
                contentDivider.setVisibility(View.VISIBLE);
                buttonPanel.setVisibility(View.VISIBLE);
                buttonDivider.setVisibility(View.GONE);
            } else {
                contentDivider.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 确认
     *
     * @param text
     * @param listener
     * @return
     */
    public Dialoger setPositiveBtn(String text, OnPositiveListener listener) {
        if (!TextUtils.isEmpty(text)) {
            mPositiveText = text;
        } else {
            mPositiveText = mActivity.getString(R.string.btn_ok);
        }
        mPositiveListener = listener;
        return this;
    }

    public Dialoger setPositiveColor(int textColor) {
        mPositiveTextColor = textColor;
        return this;
    }

    /**
     * 取消按钮(不是点击dialog其他地方的取消）
     *
     * @param text
     * @param listener
     * @return
     */
    public Dialoger setNegativeBtn(String text, OnNegativeListener listener) {
        if (!TextUtils.isEmpty(text)) {
            mNegativeText = text;
        } else {
            mNegativeText = mActivity.getString(R.string.btn_cancel);
        }
        mNegativeListener = listener;
        return this;
    }

    public Dialoger setNegativeColor(int textColor) {
        mNegativeTextColor = textColor;
        return this;
    }

    //取消监听
    public void setOnCancelListener(@Nullable DialogInterface.OnCancelListener listener) {
        if (listener != null) {
            mCancelListener = listener;
        }
    }

    //调用dialog的取消
    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (mCancelListener != null) {
            mCancelListener.onCancel(dialog);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_negative) {
            if (mNegativeListener != null) mNegativeListener.onNegative(getDialog());
        } else if (v.getId() == R.id.btn_positive) {
            if (mPositiveListener != null) mPositiveListener.onPositive(getDialog());
        }
    }
}
