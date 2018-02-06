package com.teethen.xsdk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.teethen.sdk.xwidget.photoview.PhotoViewUtil;
import com.teethen.sdk.xwidget.zbar.ScanZbarActivity;
import com.teethen.sdk.xwidget.zxing.ScanZxingActivity;
import com.teethen.xsdk.test.PhotoViewTest;

import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int QR_REQ_COED_ZXING = 262;
    private static final int QR_REQ_COED_ZBAR = 263;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar(R.id.toolbar, getString(R.string.app_name), false);

        initViews();
    }

    private void initViews() {

        findViewById(R.id.btn_font_size).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsFontSizeActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_qr_zxing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zbar = new Intent(MainActivity.this, ScanZxingActivity.class);
                startActivityForResult(zbar, QR_REQ_COED_ZXING);
            }
        });

        findViewById(R.id.btn_qr_zbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zbar = new Intent(MainActivity.this, ScanZbarActivity.class);
                startActivityForResult(zbar, QR_REQ_COED_ZBAR);
            }
        });

        findViewById(R.id.btn_pv_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = PhotoViewTest.getBaiduSingleImg();
                PhotoViewUtil.startViewWithGlide(MainActivity.this, url);
            }
        });

        findViewById(R.id.btn_pv_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> uriList = PhotoViewTest.getBaiduImgs();
                PhotoViewUtil.startViewPagerWithUrl(MainActivity.this, uriList);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case QR_REQ_COED_ZXING:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (null != bundle) {
                        showToast(bundle.getString("result"), Gravity.BOTTOM);
                    }
                }
                break;
            case QR_REQ_COED_ZBAR:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    if (null != bundle) {
                        showToast(bundle.getString("result"), Gravity.BOTTOM);
                    }
                }
                break;
        }
    }
}
