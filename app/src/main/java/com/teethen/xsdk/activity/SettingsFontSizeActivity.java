package com.teethen.xsdk.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.SeekBar;
import android.widget.TextView;

import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xutil.ControlUtil;
import com.teethen.sdk.xwidget.seekbar.RangeSeekBar;
import com.teethen.xsdk.R;
import com.teethen.xsdk.common.SpKeys;

public class SettingsFontSizeActivity extends BaseActivity {
    private final String TAG = "SettingsFontSize";

    private SeekBar fontSeekBar;
    private RangeSeekBar fontRangeSeekBar;
    private TextView tvFont1, tvFont2;
    private float defaultFontSize = 16;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_font_size);
        try {
            initToolbar();
            initView();
            setListener();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            showToast(e.toString(), Gravity.BOTTOM);
        }
    }

    private void initToolbar() {
        initToolBar(R.id.toolbar, getString(R.string.act_settings_font_size));
    }

    private void initView() {
        float displayValue = 100;
        try {
            tvFont1 = (TextView) findViewById(R.id.chat_font_tv1);
            tvFont2 = (TextView) findViewById(R.id.chat_font_tv2);
            //fontSeekBar = (SeekBar) findViewById(R.id.fontSeekBar);
            //customSeekBar = (CustomSeekBar) findViewById(R.id.customSeekBar);
            fontRangeSeekBar = (RangeSeekBar) findViewById(R.id.fontRangeSeekBar);

            float scale = sp.getFloat(SpKeys.KEY_FONTSIZE_SCALE, 1);
            displayValue = scale * 100;
            fontRangeSeekBar.setValue(displayValue);
        } catch (Exception e) {
            displayValue = 100;
            fontRangeSeekBar.setValue(displayValue);
            Log.e(TAG, e.toString());
        }
        finally {
            fontRangeSeekBar.setProgressDescription((int)displayValue + "%");
        }
    }

    private void setListener() {
        fontRangeSeekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                fontRangeSeekBar.setProgressDescription((int)min + "%");

                float scale = 1 + ((min - 100) * 0.01F);
                setSpKeysData(scale);
                ControlUtil.setGlobalFontSize(SettingsFontSizeActivity.this);
                setTextSize(scale);
            }
            @Override
            public void onStartTrackingTouch(RangeSeekBar view,  boolean isLeft) {
            }
            @Override
            public void onStopTrackingTouch(RangeSeekBar view,  boolean isLeft) {
            }
        });

        /*fontSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int txtSize = progress + 75;
                showToast(String.valueOf(txtSize), 0);
                float scale = 1 + ((txtSize - 100) * 0.01f);
                Constant.FONT_SIZE_SCALE = scale;
                sp.putFloat(SpKeys.KEY_FONTSIZE_SCALE, scale);
                ControlUtil.setGlobalFontSize(SettingsFontSizeActivity.this);
                setTextSize();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        //CustomSeekBar
        ArrayList<String> volume_sections = new ArrayList<String>();
        volume_sections.add("75");
        volume_sections.add("标准");
        volume_sections.add("125");
        volume_sections.add("150");
        customSeekBar.initData(volume_sections);
        customSeekBar.setProgress(25);
        customSeekBar.setResponseOnTouch(new CustomSeekBar.ResponseOnTouch() {
            @Override
            public void onTouchResponse(int progress) {
                showToast(String.valueOf(progress));
            }
        });*/
    }

    /**
     * 设置字体放大倍数和字体主题
     * @param scale 字体放大的倍数(系数)
     */
    private void setSpKeysData(float scale) {
        XConstant.FONT_SIZE_SCALE = scale;

        float configScale = scale;
        if(scale < 1){
            configScale = 0.75F;
        }else if(scale >=1 && scale < 1.25){
            configScale = 1;
        } else if(scale >=1.25 && scale < 1.5) {
            configScale = 1.25F;
        } else if (scale >= 1.5) {
            configScale = 1.5F;
        }

        sp.putFloat(SpKeys.KEY_FONTSIZE_MODE, configScale);
        sp.putFloat(SpKeys.KEY_FONTSIZE_SCALE, configScale);
        //sp.putFloat(SpKeys.KEY_FONTSIZE_SCALE, scale);
    }

    private void setTextSize(float scale) {
        float size = scale * defaultFontSize;
        tvFont1.setTextSize(size);
        tvFont2.setTextSize(size);
    }
}
