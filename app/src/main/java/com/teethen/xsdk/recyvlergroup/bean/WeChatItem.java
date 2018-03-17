package com.teethen.xsdk.recyvlergroup.bean;

import java.io.Serializable;

/**
 * Created by xingq on 2018/3/15.
 */

public class WeChatItem implements Serializable {

    private int key;
    private int imgId;
    private int titleId;
    private int subtitleId;
    private int viewType;
    private Class<?> intentClass;
    private boolean isChecked;

    public WeChatItem(int key, int titleId, int subtitleId, int viewType, boolean isChecked) {
        this(key, 0, titleId, subtitleId, viewType, null);
        this.isChecked = isChecked;
    }

    public WeChatItem(int key, int imgId, int titleId, int viewType, Class<?> intentClass) {
        this(key, imgId, titleId, 0, viewType, intentClass);
    }

    public WeChatItem(int key, int imgId, int titleId, int subtitleId, int viewType, Class<?> intentClass) {
        this.key = key;
        this.imgId = imgId;
        this.titleId = titleId;
        this.subtitleId = subtitleId;
        this.viewType = viewType;
        this.intentClass = intentClass;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getTitleId() {
        return titleId;
    }

    public void setTitleId(int titleId) {
        this.titleId = titleId;
    }

    public int getSubtitleId() {
        return subtitleId;
    }

    public void setSubtitleId(int subtitleId) {
        this.subtitleId = subtitleId;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public Class<?> getIntentClass() {
        return intentClass;
    }

    public void setIntentClass(Class<?> intentClass) {
        this.intentClass = intentClass;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
