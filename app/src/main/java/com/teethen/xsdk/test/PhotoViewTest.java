package com.teethen.xsdk.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xingq on 2018/2/6.
 */

public class PhotoViewTest {

    public static String getBaiduSingleImg() {
        return "http://xiangce.baidu.com/img/shouji.png";
    }

    public static List<String> getBaiduImgs() {
        List<String> uriList = new ArrayList<>();

        String[] urlArr = new String[]{
                "http://img6.bdstatic.com/img/image/pcindex/shitu_b01.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_b02.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_b04.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_b05.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w01.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w03.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w04.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w05.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w06.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w07.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w08.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w09.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w10.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w11.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/shitu_w12.jpg",
                "http://img6.bdstatic.com/img/image/pcindex/sshitu_w02.jpg",
                "http://img6.bdstatic.com/img/image/public/shitu01.jpg"
        };

        for (String url:urlArr) {
            uriList.add(url);
        }

        return uriList;
    }

}
