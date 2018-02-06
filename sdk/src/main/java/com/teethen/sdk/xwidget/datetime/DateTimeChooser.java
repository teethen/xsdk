package com.teethen.sdk.xwidget.datetime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

import com.teethen.sdk.R;
import com.teethen.sdk.xtextedit.supertextview.SuperTextView;
import com.teethen.sdk.xutil.SharedPreferencesUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间选择控件使用方法:
 * private EditText inputDate;//需要设置的日期时间文本编辑框
 * private String initDateTime="2012-9-3 14:44";//初始日期时间值
 * private String dateFormat = "yyyy-MM-dd HH:mm";
 * 在点击事件中使用:
 * inputDate.setOnClickListener(new OnClickListener() {
 *      @Override
 *      public void onClick(View v) { DateTimePickerUtil
 *           dateTimePicKDialog=new DateTimeChooser(Activity.this,initDateTime,dateFormat);
 *           dateTimePicKDialog.dateTimePicKDialog(inputDate);
 *      }
 *  });
 */

public class DateTimeChooser implements OnDateChangedListener, OnTimeChangedListener {
    private LinearLayout dateTimeLayout;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private AlertDialog alertDialog;
    private String dateTime;
    private String initDateTime;
    private String dateFormat;
    private Activity activity;

    private static final String CHOOSE_DATE = "选择日期";
    private static final String CHOOSE_TIME = "选择时间";
    private static final String CHOOSE_DATETIME = "选择日期时间";

    /**
     * 日期时间弹出选择框构造函数
     *
     * @param activity 调用的父activity
     * @param initDateTime 初始日期时间值，作为弹出窗口的标题和日期时间初始值
     * @param dateFormat 日期格式
     */
    public DateTimeChooser(Activity activity, String initDateTime, String dateFormat) {
        this.activity = activity;
        this.initDateTime = initDateTime;
        this.dateFormat = dateFormat;

        dateTimeLayout = (LinearLayout) activity.getLayoutInflater().inflate(R.layout.fragment_datetime, null);
        datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
        timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
    }

    public void init(@Nullable DatePicker datePicker, @Nullable TimePicker timePicker) {
        Calendar calendar = Calendar.getInstance();

        String date = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        if (!TextUtils.isEmpty(initDateTime)) {
            calendar = getCalendarByInitData(initDateTime);
        }

        if (datePicker != null) {
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
            initDateTime = date;
        }
        if (timePicker != null) {
            timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            timePicker.setIs24HourView(true);
            timePicker.setOnTimeChangedListener(this);
            initDateTime = time;
        }

        if (datePicker != null && timePicker != null) {
            initDateTime = date + " " + time;
        }
    }

    /**
     * 弹出日期时间选择框
     * @param inputDate 为需要设置的日期时间文本编辑框
     */
    public AlertDialog showDateTime(final EditText inputDate) {
        init(datePicker, timePicker);
        alertDialog = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(CHOOSE_DATETIME)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
        return alertDialog;
    }

    /**
     * 弹出日期选择框
     * @param inputDate 为需要设置的日期文本编辑框
     */
    public AlertDialog showDate(final EditText inputDate) {
        timePicker.setVisibility(View.GONE);
        datePicker.setVisibility(View.VISIBLE);
        init(datePicker, null);
        alertDialog = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(CHOOSE_DATE)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputDate.setText(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
        return alertDialog;
    }

    /**
     * 弹出时间选择框
     * @param inputTime 为需要设置的时间文本编辑框
     */
    public AlertDialog showTime(final EditText inputTime) {
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.VISIBLE);
        init(null, timePicker);
        alertDialog = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(CHOOSE_TIME)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        inputTime.setText(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
        return alertDialog;
    }


    /**
     * 弹出日期时间选择框
     * @param superTextView 显示位置:rightString
     */
    public void showDateTime(final SuperTextView superTextView) {
        init(datePicker, timePicker);
        alertDialog = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(CHOOSE_DATETIME)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        superTextView.setRightString(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
    }

    /**
     * 弹出日期选择框
     * @param superTextView 显示位置:rightString
     */
    public void showDate(final SuperTextView superTextView) {
        timePicker.setVisibility(View.GONE);
        datePicker.setVisibility(View.VISIBLE);
        init(datePicker, null);
        alertDialog = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(CHOOSE_DATE)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        superTextView.setRightString(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
    }

    /**
     * 弹出时间选择框
     * @param superTextView 显示位置:rightString
     */
    public void showTime(final SuperTextView superTextView) {
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.VISIBLE);
        init(null, timePicker);
        alertDialog = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(CHOOSE_TIME)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        superTextView.setRightString(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
    }

    /**
     * 显示勿扰模式时间选择框
     * @param superTextView 显示位置:rightString
     */
    public void showDisturbTime(final SuperTextView superTextView, final SharedPreferencesUtil sp, final String spKey) {
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.VISIBLE);
        init(null, timePicker);
        alertDialog = new AlertDialog.Builder(activity)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle(CHOOSE_TIME)
                .setView(dateTimeLayout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        sp.putString(spKey, dateTime);
                        superTextView.setRightString(dateTime);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                }).show();
        onDateChanged(null, 0, 0, 0);
    }


    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        onDateChanged(null, 0, 0, 0);
    }

    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        dateTime = formatter.format(calendar.getTime());
        //alertDialog.setTitle(dateTime);
    }

    private Calendar getCalendarByInitData(String initDateTime) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        try {
            Date date = format.parse(initDateTime);
            calendar.setTime(date);
        } catch (Exception e) {
            Log.e("DateTimeChooser", e.getMessage());
        }
        return calendar;
    }
}

