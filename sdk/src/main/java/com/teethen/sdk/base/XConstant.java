package com.teethen.sdk.base;

import org.ksoap2.SoapEnvelope;

/**
 * Created by xingq on 2018/2/6.
 */

public class XConstant {

    public static String SERVER_IP = "192.168.3.86:9080";
    public static String SERVER_URL_PATTERN = "http://%s/combine/services/";
    public static String SERVER_URL = String.format(SERVER_URL_PATTERN, SERVER_IP);

    public static final String SHARED_PREF_CFG = "teethen_sdk_cfg";//系统配置文件

    //时间配置
    public static final String TIME_ZONE = "GMT+08";//时区
    public static final String CHARSET_UTF8 = "UTF-8";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SHORT = "yyyyMMdd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String TIME_FORMAT_HM = "HH:mm";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT_HM = "yyyy-MM-dd HH:mm";
    public static final String DATETIME_FORMAT_MDHM = "MM-dd HH:mm";

    //超时配置
    public static final String MSG_SOCKET_TIMEOUT = "connect timed out"; //连接超时
    public static final int EXPIRED_DAYS = 7; //默认过期日期天数(1星期)
    public static final int MAX_IMG_COUNT = 9;
    public static final int HTTP_TIMEOUT = 10000;

    //字体配置
    public static final float FONT_THEME_DEFAULT = 1;  //字体大小主题模式(默认FontDefaultTheme)
    public static float FONT_SIZE_SCALE = 1; //正常字体大小的倍数
    public static int FONTSIZE_SMALLER = 75; //较小
    public static int FONTSIZE_NORMAL = 100; //标准
    public static int FONTSIZE_MEDIUM = 125; //中等大
    public static int FONTSIZE_LARGE = 150;  //大
    public static int FONTSIZE_LARGEST = 200;//较大

    //分隔符(半角)[".", "|", "^"等字符做分隔符时,要写成s.split("\\^")的格式]
    public static final String SPLIT_COMMA = ","; //逗号
    public static final String SPLIT_DOT = "\\."; //句点
    public static final String SPLIT_UPPER_BRACKET = "\\^"; //上尖括号
    public static final String SPLIT_VERTICAL_LINE = "\\|"; //竖线

    //Image Resource Type
    public static final int IMG_RES_TYPE_ID = 0; //resource id
    public static final int IMG_RES_TYPE_URL = 1;//resource url/filepath
    public static final int IMG_RES_TYPE_URI = 2;//resource uri

    //Progress Dialog Theme
    public static final int PROGRESS_DLG_THEME_LIGHT = 0; //ProgressDialog Holo_Light_Theme
    public static final int PROGRESS_DLG_THEME_DARK = 1; //ProgressDialog Holo_Dark_Theme

    //文件配置
    public static final String MIME_HTML = "text/html";
    public static final String MIME_PDF = "application/pdf";
    public static final String MIME_ANDROID = "application/vnd.android.package-archive";
    public static final String SCHEME_FILE = "file";
    public static final String SCHEME_CONTENT = "content";
    public static String TEMP_DIR = "/teethen/sdk/";
    public static String TEMP_DIR_IMAGE = TEMP_DIR + "image/";
    public static String TEMP_DIR_VIDEO = TEMP_DIR + "video/";
    public static String TEMP_DIR_FILES = TEMP_DIR + "files/";
    public static String TEMP_DIR_LOG = TEMP_DIR + "log/";

    //数据类型
    public static final int FORMAT_STR = 1;
    public static final int FORMAT_OBJ = 2;

    //WebService
    public static final int SOAP_VERSION10 = SoapEnvelope.VER10;
    public static final int SOAP_VERSION11 = SoapEnvelope.VER11;
    public static final int SOAP_VERSION12 = SoapEnvelope.VER12;
    public static final boolean WithSoapAction = true;
    public static final boolean WithoutSoapAction = false;
    public static final boolean NeedToDecrypt = true;
    public static final boolean NotNeedToDecrypt = false;
    //接口地址
    public static final String WEBSERVICE_URL = SERVER_URL + "mobileService?wsdl";
    public static final String WEBSERVICE_NAMESPACE = "http://webservice.frame.xingq.com/";

}
