package com.teethen.xsdk.mediapicker;

import java.io.Serializable;

/**
 * Created by xingq on 2017/12/5.
 */

public class FileItem implements Serializable {

    private String type;
    private String fileName;
    private String originalPath;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getOriginalPath() {
        return originalPath;
    }

    public void setOriginalPath(String originalPath) {
        this.originalPath = originalPath;
    }
}
