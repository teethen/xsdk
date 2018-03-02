package com.teethen.xsdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.teethen.sdk.xwidget.photoview.PhotoViewUtil;
import com.teethen.sdk.xwidget.zbar.ScanZbarActivity;
import com.teethen.sdk.xwidget.zxing.ScanZxingActivity;
import com.teethen.xsdk.R;
import com.teethen.xsdk.ninegrid.complex.news.NewsActivity;
import com.teethen.xsdk.ninegrid.simple.SimpleNineGridActivity;
import com.teethen.xsdk.test.PhotoViewTest;

import java.util.List;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initToolBar(R.id.toolbar, "测试", true);
        initViews();
    }

    private void initViews() {

        //字体大小
        findViewById(R.id.btn_font_size).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, SettingsFontSizeActivity.class);
                startActivity(intent);
            }
        });

        //pdf预览
        findViewById(R.id.btn_pdf_viewer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestActivity.this, PdfViewActivity.class);
                startActivity(intent);
            }
        });

        //QR Scan By Zbar
        findViewById(R.id.btn_qr_zxing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zbar = new Intent(TestActivity.this, ScanZxingActivity.class);
                startActivityForResult(zbar, QR_REQ_COED_ZXING);
            }
        });

        //QR Scan By Zxing
        findViewById(R.id.btn_qr_zbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent zbar = new Intent(TestActivity.this, ScanZbarActivity.class);
                startActivityForResult(zbar, QR_REQ_COED_ZBAR);
            }
        });

        //单张图片预览
        findViewById(R.id.btn_pv_single).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = PhotoViewTest.getBaiduSingleImg();
                PhotoViewUtil.startViewWithGlide(TestActivity.this, url);
            }
        });

        //多张图片预览
        findViewById(R.id.btn_pv_multiple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> uriList = PhotoViewTest.getBaiduImgs();
                PhotoViewUtil.startViewPagerWithUrl(TestActivity.this, uriList);
            }
        });

        //九宫格 简单实现
        findViewById(R.id.btn_gv_liteview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(SimpleNineGridActivity.class);
            }
        });

        //九宫格 稍微复杂实现
        findViewById(R.id.btn_gv_complex).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(NewsActivity.class);
            }
        });
    }


    private static final int QR_REQ_COED_ZXING = 262;
    private static final int QR_REQ_COED_ZBAR = 263;
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
