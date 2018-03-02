package com.teethen.xsdk.ninegrid.simple;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xingq on 2018/3/2.
 */

public class GridImage implements Serializable {

    private int spanType;
    private String content;
    private List<String> urlList;

    public GridImage(String content, List<String> list) {
        this.content = content;
        this.urlList = list;
    }

    public GridImage(int spanType, String content, List<String> list) {
        this.spanType = spanType;
        this.content = content;
        this.urlList = list;
    }

    public int getSpanType() {
        return spanType;
    }

    public void setSpanType(int spanType) {
        this.spanType = spanType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }
}
