package com.teethen.sdk.xutil;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.teethen.sdk.R;
import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xdialog.dialogui.DialogUIUtils;
import com.teethen.sdk.xdialog.dialogui.listener.DialogUIDateTimeSaveListener;
import com.teethen.sdk.xdialog.dialogui.widget.DateSelectorWheelView;
import com.teethen.sdk.xtextedit.supertextview.SuperTextView;
import com.teethen.sdk.xwidget.datetime.DateTimeChooser;

import java.util.LinkedList;

/**
 * Created by xingq on 2017/11/28.
 */

public class ControlUtil {

    //设置按钮状态
    public static void setButtonStatus(Button btn, boolean enabled) {
        btn.setEnabled(enabled);
        if (enabled)
            btn.setBackgroundResource(R.drawable.bg_button_primary);
        else
            btn.setBackgroundColor(Color.LTGRAY);
    }

    public static void setEditTextReadOnly(View view, boolean readonly) {
        if (view instanceof EditText) {
            EditText edit = (EditText) view;
            edit.setTextColor(readonly ? Color.LTGRAY : Color.WHITE);//设置只读时的文字颜色
            edit.setCursorVisible(!readonly);                        //设置输入框中的光标不可见
            edit.setFocusable(!readonly);                            //无焦点
            edit.setFocusableInTouchMode(!readonly);                 //触摸时也得不到焦点
        }
    }

    /**
     * 获取时间
     * @param activity
     * @param superTextView 显示位置 rightString
     * @param formatType 弹出日期时间选择框类型，DATE_FORMAT:日期，TIME_FORMAT:时间，DATETIME_FORMAT:日期时间
     */
    public static void setDateTime(final Activity activity, final SuperTextView superTextView, final String formatType) {
        superTextView.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {
                String initDateTime = DateTimeUtil.getDateTimeStr(null, XConstant.DATETIME_FORMAT);
                if (formatType == XConstant.DATE_FORMAT) {
                    new DateTimeChooser(activity, initDateTime, formatType).showDate(superTextView);
                } else if (formatType == XConstant.TIME_FORMAT || formatType == XConstant.TIME_FORMAT_HM) {
                    new DateTimeChooser(activity, initDateTime, formatType).showTime(superTextView);
                } else if (formatType == XConstant.DATETIME_FORMAT) {
                    new DateTimeChooser(activity, initDateTime, formatType).showDateTime(superTextView);
                }
            }
        });
    }

    /**
    * 监听DateTimePicker点击事件
    * @param activity
    * @param editText
    * @param formatType 弹出日期时间选择框类型，DATE_FORMAT:日期，TIME_FORMAT:时间，DATETIME_FORMAT:日期时间
    */
    public static void listenDateTimeChooser(final Activity activity, final EditText editText, final String formatType, @Nullable final String initialTime) {
        editText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String initDateTime = DateTimeUtil.getDateTimeStr(initialTime, XConstant.DATETIME_FORMAT);
                if (formatType == XConstant.DATE_FORMAT) {
                    new DateTimeChooser(activity, initDateTime, formatType).showDate(editText);
                } else if (formatType == XConstant.TIME_FORMAT) {
                    new DateTimeChooser(activity, initDateTime, formatType).showTime(editText);
                } else if (formatType == XConstant.DATETIME_FORMAT) {
                    new DateTimeChooser(activity, initDateTime, formatType).showDateTime(editText);
                }
            }
        });
    }
    /**
     * 监听DateTimePicker点击事件
     * @param context
     * @param editText
     * @param dateType 弹出日期时间选择框类型，DateSelectorWheelView.TYPE_***
     */
    public static void listenDateTimePicker(final Context context, final EditText editText, final int dateType) {
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "选择日期";
                if (dateType == DateSelectorWheelView.TYPE_YYYYMMDDHHMM || dateType == DateSelectorWheelView.TYPE_YYYYMMDDHHMMSS) {
                    title = "选择日期和时间";
                }
                DialogUIUtils.showDatePick(context, Gravity.CENTER, title,System.currentTimeMillis() + 60000, dateType, 0,
                        new DialogUIDateTimeSaveListener() {
                            @Override
                            public void onSaveSelectedDate(int tag, String selectedDate) {
                                editText.setText(selectedDate);
                            }
                        }).show();
            }
        });
    }

    public static void setSwipeRefreshLayoutColors(SwipeRefreshLayout swipeRefreshLayout) {
        int[] colors = new int[]{
                android.R.color.holo_blue_dark, android.R.color.holo_green_light, android.R.color.holo_orange_light,
                android.R.color.holo_red_light, android.R.color.holo_blue_bright
        };
        swipeRefreshLayout.setColorSchemeResources(colors);
    }

    public static void setProgressBarStyle(ProgressBar progressBar, boolean horizontalStyle, @Nullable Drawable progressDrawable) {
        progressBar.setVisibility(View.VISIBLE);
        //progressBar.setScrollBarStyle(1);
        progressBar.setProgressDrawable(progressDrawable);
        progressBar.setHorizontalScrollBarEnabled(horizontalStyle);
    }

    //判断EditText是否为空
    public static boolean isEditTextEmpty(EditText editText) {
        return TextUtils.isEmpty(editText.getText().toString().trim());
    }

    //判断Spinner是否为空
    public static boolean isSpinnerEmpty(Spinner spinner) {
        Object obj = spinner.getSelectedItem();
        return TextUtils.isEmpty(obj == null ? null : obj.toString().trim());
    }

    //onlyInputTypeResId:  input_only_number, input_only_char, input_only_number_char
    public static void setEditTextInput(final Context context, EditText edit, final int onlyInputTypeResId) {
        edit.setKeyListener(new DigitsKeyListener() {
            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_NUMBER;
            }

            @Override
            protected char[] getAcceptedChars() {
                return context.getResources().getString(onlyInputTypeResId).toCharArray();
            }
        });
    }

    public static EditText getRangedEditText(final Context context, final int min, final int max, String hint) {
        final EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setHint(hint);
        editText.setFocusable(true);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    String range = String.format("数值限制范围: %d - %d", min, max);
                    int value = DataUtil.getInt(s);
                    if (value < min) {
                        editText.setText(String.valueOf(min));
                        Toast.makeText(context, range, Toast.LENGTH_SHORT).show();
                    } else if (value > max) {
                        editText.setText(String.valueOf(max));
                        Toast.makeText(context, range, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return editText;
    }

    public static CharSequence getLinkText(String text) {
        return Html.fromHtml(text);
    }

    public static void setEnabled(View view, boolean enabled) {
        if (null == view) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            LinkedList<ViewGroup> queue = new LinkedList<ViewGroup>();
            queue.add(viewGroup);
            while (!queue.isEmpty()) { //遍历viewGroup
                ViewGroup current = queue.removeFirst();
                current.setEnabled(enabled);
                for (int i = 0; i < current.getChildCount(); i++) {
                    if (current.getChildAt(i) instanceof ViewGroup) {
                        queue.addLast((ViewGroup) current.getChildAt(i));
                    } else {
                        current.getChildAt(i).setEnabled(enabled);
                    }
                }
            }
        } else {
            view.setEnabled(enabled);
        }
    }

    public static void setEditTextEnabled(View view, boolean enabled) {
        if (null == view) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            LinkedList<ViewGroup> queue = new LinkedList<ViewGroup>();
            queue.add(viewGroup);
            while (!queue.isEmpty()) { //遍历viewGroup
                ViewGroup current = queue.removeFirst();
                for (int i = 0; i < current.getChildCount(); i++) {
                    View v = current.getChildAt(i);
                    if (v instanceof ViewGroup) {
                        queue.addLast((ViewGroup) current.getChildAt(i));
                    } else if(v instanceof EditText) {
                        //setEditTextReadOnly(v, readonly);
                        v.setEnabled(enabled);
                    }
                }
            }
        } else if(view instanceof EditText) {
            //setEditTextReadOnly(view, readonly);
            view.setEnabled(enabled);
        }
    }

    /**
     * 设置全局字体大小(字体大小单位必须为sp方可生效)
     * @param activity
     */
    public static void setGlobalFontSize(Activity activity){
        Configuration configuration = activity.getResources().getConfiguration();
        configuration.fontScale = XConstant.FONT_SIZE_SCALE; //1为标准字体, multiple为放大的倍数
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        displayMetrics.scaledDensity = configuration.fontScale * displayMetrics.density;
        activity.getBaseContext().getResources().updateConfiguration(configuration, displayMetrics);
    }

    /**
     * 创建弹出菜单
     * @param context
     * @param v
     * @param menuArray
     * @return
     */
    public static PopupMenu createMenu(Context context, View v, String[] menuArray) {
        PopupMenu popupMenu = new PopupMenu(context, v);
        Menu menu = popupMenu.getMenu();
        for (String menuText : menuArray) {
            menu.add(menuText);
        }
        return popupMenu;
    }

}
