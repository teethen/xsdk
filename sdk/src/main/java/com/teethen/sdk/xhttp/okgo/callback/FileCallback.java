package com.teethen.sdk.xhttp.okgo.callback;

import com.teethen.sdk.xhttp.okgo.convert.FileConvert;
import java.io.File;
import okhttp3.Response;

/**
 * 描述：文件的回调下载进度监听
 */
public abstract class FileCallback extends AbsCallback<File> {

    private FileConvert convert;    //文件转换类

    public FileCallback() {
        this(null);
    }

    public FileCallback(String destFileName) {
        this(null, destFileName);
    }

    public FileCallback(String destFileDir, String destFileName) {
        convert = new FileConvert(destFileDir, destFileName);
        convert.setCallback(this);
    }

    @Override
    public File convertResponse(Response response) throws Throwable {
        File file = convert.convertResponse(response);
        response.close();
        return file;
    }
}
