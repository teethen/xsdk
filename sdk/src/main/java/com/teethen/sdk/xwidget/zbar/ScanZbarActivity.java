package com.teethen.sdk.xwidget.zbar;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.teethen.sdk.R;
import com.teethen.sdk.base.BaseActivity;
import com.teethen.sdk.xutil.PermissionUtil;
import com.teethen.sdk.xwidget.zbar.camera.CameraPreview;
import com.teethen.sdk.xwidget.zbar.camera.CameraScanCallback;

/**
 * Created by xingq 2018/1/31.
 * ZBar 二维码/条码扫描
 */

public class ScanZbarActivity extends BaseActivity {
    private final String TAG = ScanZbarActivity.class.getSimpleName();;

    private String TEXT_LIGHT_ON = "打开闪光灯";
    private String TEXT_LIGHT_OFF = "关闭闪光灯";
    private boolean isFlashLightOn = false;

    private AlertDialog dialog;
    private boolean isDialogShowing = false;

    private RelativeLayout mScanCropView;
    private ImageView mScanLine;
    private ValueAnimator mScanAnimator;
    private CameraPreview mPreviewView;
    private BeepManager beepManager;
    private Button mBtnRestart;
    private boolean isOnScanning;

    PermissionUtil mPermissionUtil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_scan_zbar);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            mPermissionUtil = new PermissionUtil(this);

            initToolBar(R.id.toolbar, getResources().getString(R.string.act_qr_scanner));

            TEXT_LIGHT_ON = getResources().getString(R.string.action_flashlight_on);
            TEXT_LIGHT_OFF = getResources().getString(R.string.action_flashlight_off);

            mPreviewView = findViewById(R.id.zbar_capture_preview);
            mScanCropView = findViewById(R.id.zbar_capture_crop_view);
            mScanLine = findViewById(R.id.zbar_capture_scan_line);
            mPreviewView.setScanCallback(resultCallback);
            beepManager = new BeepManager(this);

            mBtnRestart = findViewById(R.id.zbar_capture_restart_scan);
            mBtnRestart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isFlashLightOn = false;
                    startScanUnKnowPermission();
                }
            });
        } catch (Exception e) {
            showToast(e.toString());
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.flashlight, menu);//右上角菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_flashlight) {
            if (isFlashLightOn) {
                turnFlashlight(false);
            } else {
                turnFlashlight(true);
            }
            item.setTitle(isFlashLightOn ? TEXT_LIGHT_OFF : TEXT_LIGHT_ON);
        }
        return true;
    }

    //开/关灯
    private void turnFlashlight(boolean open) {
        try {
            mPreviewView.setFlashLight(open);
            isFlashLightOn = open;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Accept scan result.
     */
    private CameraScanCallback resultCallback = new CameraScanCallback() {
        @Override
        public void onScanResult(String result) {
            beepManager.playBeepSoundAndVibrate();
            stopScan();
            //showLongToast(result);
            Intent intent = new Intent();
            intent.putExtra("result", result);
            setResult(RESULT_OK, intent);
            finish();

            /*DialogUtil.showAlertOne(ScanZbarActivity.this, "", result, new Callback() {
                @Override
                public void callback() {
                    mBtnRestart.callOnClick();//点击确定后自动重启扫描
                }
            });*/
            //DialogUtil.showAlertOne(ScanZbarActivity.this, "", result, null); //点击确定后仅关闭提示框
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mScanAnimator != null) {
            startScanUnKnowPermission();
        }
    }

    @Override
    public void onPause() {
        // Must be called here, otherwise the camera should not be released properly.
        beepManager.close();
        stopScan();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void stopScan() {
        mScanAnimator.cancel();
        mPreviewView.stop();

        setButtonStatus(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mScanAnimator == null) {
            try {
                int height = mScanCropView.getMeasuredHeight() - 25;
                mScanAnimator = ObjectAnimator.ofFloat(mScanLine, "translationY", 0F, height).setDuration(3000);
                mScanAnimator.setInterpolator(new LinearInterpolator());
                mScanAnimator.setRepeatCount(ValueAnimator.INFINITE);
                mScanAnimator.setRepeatMode(ValueAnimator.REVERSE);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            startScanUnKnowPermission();
        }
    }

    /**
     * Do not have permission to request for permission and start scanning.
     */
    private void startScanUnKnowPermission() {
        mPermissionUtil.requestPermissions("请授予本APP使用系统相机的权限",
                new PermissionUtil.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {
                        isDialogShowing = true;
                        startScanWithPermission();
                    }
                    @Override
                    public void doAfterDenied(String... permission) {
                        showPermissionDialog();
                    }
                }, Manifest.permission.CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (!isDialogShowing) {
            mPermissionUtil.handleRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * There is a camera when the direct scan.
     */
    private void startScanWithPermission() {
        if (mPreviewView != null) {
            if (mPreviewView.start()) {
                mScanAnimator.start();

                setButtonStatus(true);
            } else {
                //showPermissionDialog();
                showToast(getResources().getString(R.string.camera_hint));
            }
        }
    }

    /**
     * 显示权限被拒绝的提示框
     */
    private void showPermissionDialog() {
        if (!isDialogShowing) {
            if (dialog == null) {
                dialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.camera_failure)
                    .setMessage(R.string.camera_hint)
                    .setCancelable(false)
                    .setPositiveButton(R.string.btn_ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            finish();
                        }
                    }).create();
                isDialogShowing = true;
            }
            if (!dialog.isShowing()) {
                isDialogShowing = true;
                dialog.show();
            }
        }
    }

    private void setButtonStatus(boolean isPreviewing) {
        isOnScanning = isPreviewing;
        mBtnRestart.setEnabled(!isOnScanning);
        //mBtnRestart.setVisibility(isOnScanning ? View.GONE : View.VISIBLE);
    }
}
