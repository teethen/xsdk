package com.teethen.sdk.xwidget.zbar.camera;

/**
 * <p>Scan results callback.</p>
 * Created by xingq on 2017/5/5.
 */
public interface CameraScanCallback {

    /**
     * Content is not empty when the callback.
     *
     * @param content qr code content, is not null.
     */
    void onScanResult(String content);

}
