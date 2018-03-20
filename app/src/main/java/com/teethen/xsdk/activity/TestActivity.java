package com.teethen.xsdk.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.teethen.sdk.callback.Callback;
import com.teethen.sdk.xdialog.colordialog.PromptDialog;
import com.teethen.sdk.xdialog.fragdialog.Dialoger;
import com.teethen.sdk.xupdater.XUpdater;
import com.teethen.sdk.xutil.ApkUtil;
import com.teethen.sdk.xutil.DialogUtil;
import com.teethen.sdk.xlocation.LocationUtil;
import com.teethen.sdk.xwidget.photoview.PhotoViewUtil;
import com.teethen.sdk.xwidget.zbar.ScanZbarActivity;
import com.teethen.sdk.xwidget.zxing.ScanZxingActivity;
import com.teethen.xsdk.R;
import com.teethen.xsdk.marquee.MarqueeActivity;
import com.teethen.xsdk.mediapicker.MediaPickerTestActivity;
import com.teethen.xsdk.ninegrid.complex.news.NewsActivity;
import com.teethen.xsdk.ninegrid.simple.SimpleNineGridActivity;
import com.teethen.xsdk.recyvlergroup.WeChatMeActivity;
import com.teethen.xsdk.recyvlergroup.WeChatNewMsgActivity;
import com.teethen.xsdk.stepview.StepViewActivity;
import com.teethen.xsdk.test.PhotoViewTest;

import java.util.List;

public class TestActivity extends BaseActivity {

    private static final String TAG = TestActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        initToolBar(R.id.toolbar, "测试", true);
        initViews();
    }

    private void initViews() {

        //Banner
        findViewById(R.id.btn_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(BannerActivity.class);
            }
        });
        //StepView
        findViewById(R.id.btn_stepview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(StepViewActivity.class);
            }
        });

        //Marquee 跑马灯
        findViewById(R.id.btn_marquee).setOnClickListener((view) -> {
            jump(MarqueeActivity.class);
        });

        //ImagePicker
        findViewById(R.id.btn_imagepicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(ImagePickerActivity.class);
            }
        });
        //MediaPicker
        findViewById(R.id.btn_media_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(MediaPickerTestActivity.class);
            }
        });

        //当前经纬度
        findViewById(R.id.btn_lonlat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jwd = "";
                Location location = LocationUtil.getLocation(TestActivity.this);
                if (location != null) {
                    jwd = LocationUtil.getLongitudeLatitude(location);
                } else {
                    jwd = "location is null";
                }

                DialogUtil.showPromptDialog(TestActivity.this, false, "提示", "当前位置：\n"+jwd,
                        new Dialoger.OnPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                            }
                        });
            }
        });
        //检查新版本
        findViewById(R.id.btn_updater).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String API_TOKEN = "3c57fb226edf7facf821501e4eba08d2";
                String APP_ID = "5a89af10548b7a760110c918";
                String url = "http://photonxing.com:8084/api/app";
                new XUpdater(TestActivity.this, API_TOKEN, APP_ID).checkVersion(url);
            }
        });

        //设备号
        findViewById(R.id.btn_dviceid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deviceId = ApkUtil.getCombineDeviceId();
                DialogUtil.showPromptDialog(TestActivity.this, false, "提示", "设备号：\n"+deviceId,
                        new Dialoger.OnPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                            }
                        });
            }
        });
        //APK安装唯一码
        findViewById(R.id.btn_apkid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apkInstallId = ApkUtil.getInstallationId(TestActivity.this);
                DialogUtil.showPromptDialog(TestActivity.this, false, "提示", "安装号：\n"+apkInstallId,
                        new Dialoger.OnPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                            }
                        });
            }
        });

        //对话框 浮层1
        findViewById(R.id.btn_dlg_frag1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showPromptDialog(TestActivity.this, false, "提示", "欢迎使用DialogFragment浮层对话框，好使",
                        new Dialoger.OnPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                                showToast(TestActivity.class.getSimpleName());
                            }
                        });
            }
        });
        //对话框 浮层2
        findViewById(R.id.btn_dlg_frag2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showPromptDialog(TestActivity.this, false, "提示", "欢迎使用DialogFragment浮层对话框 简单实用",
                        0,0,Color.RED,Color.BLUE,"拒绝","同意",
                        new Dialoger.OnPositiveListener() {
                            @Override
                            public void onPositive(Dialog dialog) {
                                dialog.dismiss();
                                showToast("点击确定");
                            }
                });
            }
        });
        //对话框 LOGO弹出框
        findViewById(R.id.btn_dlg_prompt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showPromptDialog(TestActivity.this,"提示","操作失败", PromptDialog.DIALOG_TYPE_WRONG, null, null);
            }
        });
        //对话框 彩色框
        findViewById(R.id.btn_dlg_prompt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showColorDialog(TestActivity.this, "提示", "这是一个 ColorDialog", null, null,
                        false, new Callback() {
                            @Override
                            public void callback() {
                                showToast("点击了确定");
                            }
                        });
            }
        });


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
                //intent.putExtra(PdfViewActivity.PDF_SRC_TYPE, PdfViewActivity.PDF_TYPE_ONLINE);
                //intent.putExtra(PdfViewActivity.PDF_SRC_FILEPATH, "http://photonxing.com:8084/download/pdf/AndroidStudio30EssentialsPreview.pdf");
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

        //RecyclerViewGroup WechatMe
        findViewById(R.id.btn_recycler_group_wechat_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(WeChatMeActivity.class);
            }
        });
        //RecyclerViewGroup WechatNewMsg
        findViewById(R.id.btn_recycler_group_wechat_newmsg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jump(WeChatNewMsgActivity.class);
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
