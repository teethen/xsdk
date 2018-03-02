package com.teethen.xsdk.ninegrid.complex.news.bean;

import java.io.Serializable;

public class NewsChannel implements Serializable {
    private String channelId;
    private String name;

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "NewsChannel{" +
                "channelId='" + channelId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
