package com.teethen.xsdk.ninegrid.complex.news.bean;

import java.io.Serializable;
import java.util.List;

public class NewsContent implements Serializable {
    private String channelId;
    private String channelName;
    private String desc;
    private String link;
    private String nid;
    private String pubDate;
    private String source;
    private String title;
    private List<NewsImage> imageUrls;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<NewsImage> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<NewsImage> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "NewsContent{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", desc='" + desc + '\'' +
                ", link='" + link + '\'' +
                ", nid='" + nid + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", source='" + source + '\'' +
                ", title='" + title + '\'' +
                ", imageurls=" + imageUrls +
                '}';
    }
}


