package com.teethen.sdk.xutil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by xingq on 2017/12/6.
 */

public class FileIntentUtil {

    private static Context mContext;
    private static String authority;
    public static Intent getFileIntent(Context context, String filePath){
        if (mContext == null) {
            mContext = context;
        }
        if (TextUtils.isEmpty(authority)) {
            authority = mContext.getApplicationContext().getPackageName()+".fileProvider";
        }

        File file = new File(filePath);
        if(!file.exists()) return null;

        /* 取得扩展名 */
        String ext = FileUtil.getExtension(file.getName());

        /*Intent intent = null;
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName()+".fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }*/

        /* 依扩展名的类型决定MimeType */
        if("mp3,wav,m4a,mid,ogg,xmf".indexOf(ext)>-1){
            return getAudioFileIntent(file);
        }else if("mp4,3gp,wmv,rmvb,mkv".indexOf(ext)>-1){
            return getVideoFileIntent(file);
        }else if("jpg,jpeg,png,bmp,gif,tif,tiff".indexOf(ext)>-1){
            return getImageFileIntent(file);
        }else if("doc,docx".indexOf(ext)>-1){
            return getWordFileIntent(file);
        }else if("xls,xlsx".indexOf(ext)>-1){
            return getExcelFileIntent(file);
        }else if("ppt,pptx".indexOf(ext)>-1){
            return getPptFileIntent(file);
        }else if("txt,log,ini,json".indexOf(ext)>-1){
            return getTextFileIntent(file,false);
        }else if(ext.equals("apk")){
            return getApkFileIntent(file);
        }else if(ext.equals("pdf")){
            return getPdfFileIntent(file);
        }else if(ext.equals("chm")){
            return getChmFileIntent(filePath);
        }else if("html,htm".indexOf(ext)>-1){
            return getHtmlFileIntent(filePath);
        }else{
            return getAllIntent(file);
        }
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri,"*/*");
        return intent;
    }
    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(File file){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //Android获取一个用于打开Html文件的intent
    public static Intent getHtmlFileIntent(String param){
        Intent intent = new Intent("android.intent.action.VIEW");
        Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(File file){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(File file){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(File file){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent( String param ){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param ));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(File file, boolean paramBoolean){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getIntentUri(intent, file);
        /*if (paramBoolean){
            Uri uri1 = Uri.parse(param);
            intent.setDataAndType(uri1, "text/plain");
        }else{
            Uri uri2 = Uri.fromFile(new File(param ));
            intent.setDataAndType(uri2, "text/plain");
        }*/
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(File file){
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = getIntentUri(intent, file);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    public static Uri getIntentUri(Intent intent, File file) {
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//判断版本大于等于7.0
            uri = FileProvider.getUriForFile(mContext, authority, file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

}
