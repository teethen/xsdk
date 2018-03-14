package com.teethen.sdk.xutil;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.teethen.sdk.R;

import java.util.ArrayList;

/**
 * Created by xingq on 2017/12/14.
 * 分享工具类
 */

public class ShareUtil {

    //Package Name
    public static final String PKG_WeChat = "com.tencent.mm";
    public static final String PKG_WeChatFriends = "com.tencent.mm";
    public static final String PKG_TencentQQ = "com.tencent.mobileqq";
    public static final String PKG_TencentQQZone = "com.qzone";
    public static final String PKG_TencentWeibo = "com.tencent.WBlog";
    public static final String PKG_SinaWeibo = "com.sina.weibo";
    //Activity Name
    public static final String ACT_WeChat = "com.tencent.mm.ui.tools.ShareImgUI";
    public static final String ACT_WeChatFriends = "com.tencent.mm.ui.tools.ShareToTimeLineUI";
    public static final String ACT_TencentQQ = "com.tencent.mobileqq.activity.JumpActivity";
    public static final String ACT_TencentQQZone = "com.qzone.ui.operation.QZonePublishMoodActivity";
    public static final String ACT_TencentWeibo = "com.tencent.WBlog.intentproxy.TencentWeiboIntent";
    public static final String ACT_SinaWeibo = "com.sina.weibo.EditActivity";

    /*分享至的app, 分享界面ActivityName, 包名
    "微信", "com.tencent.mm.ui.tools.ShareImgUI", "com.tencent.mm"
    "朋友圈",  "com.tencent.mm.ui.tools.ShareToTimeLineUI", "com.tencent.mm"
    "qq", "com.tencent.mobileqq.activity.JumpActivity","com.tencent.mobileqq"
    "qq空间",  "com.qzone.ui.operation.QZonePublishMoodActivity","com.qzone"
    "新浪微博", "com.sina.weibo.EditActivity", "com.sina.weibo" "com.sina.weibog3"
    "腾讯微博",  "com.tencent.WBlog.intentproxy.TencentWeiboIntent","com.tencent.WBlog"
    "FB" "com.facebook.katana"
    "MS" "com.instagram.android"
    "Ins" "com.facebook.orca"*/

    /**
     * 调用系统分享文本到
     * @param activity
     * @param subject 分享主题
     * @param title 文本标题
     * @param text 文本内容
     * @param componentName new ComponentName(PackageName, ActivityName)
     */
    public static void shareText(Activity activity, String subject, String title, String text, @Nullable ComponentName componentName) {
        try {
            Intent intent = new Intent();
            intent.setType("text/plain");
            intent.setAction(Intent.ACTION_SEND);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TITLE, title);
            intent.putExtra(Intent.EXTRA_TEXT, text);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (componentName != null) {
                intent.setComponent(componentName);
            }
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.action_share)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, activity.getString(R.string.error_unknown), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统分享单张图片
     * @param activity
     * @param subject 分享主题
     * @param title 图片标题
     * @param imgUri 图片Uri
     * @param componentName new ComponentName(PackageName, ActivityName)
     */
    public static void shareSingleImage(Activity activity, String subject, String title, Uri imgUri, @Nullable ComponentName componentName) {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_SEND);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TITLE, title);
            intent.putExtra(Intent.EXTRA_STREAM, imgUri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (componentName != null) {
                intent.setComponent(componentName);
            }
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.action_share)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, activity.getString(R.string.error_unknown), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统分享多张图片
     * @param activity
     * @param subject 分享主题
     * @param title 图片标题
     * @param imgUriList 图片Uri列表
     * @param componentName new ComponentName(PackageName, ActivityName)
     */
    public static void shareMultipleImage(Activity activity, String subject, String title, ArrayList<Uri> imgUriList, @Nullable ComponentName componentName) {
        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_SEND_MULTIPLE);
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TITLE, title);
            intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imgUriList);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (componentName != null) {
                intent.setComponent(componentName);
            }
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.action_share)));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, activity.getString(R.string.error_unknown), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 国内图片的分享
     * <p/>由于国内几大社交app(QQ,空间,微信,朋友圈,微博等),通过意图过滤得到的要传递图片的Activity不只一个(例如QQ会出现发送,发送到电脑;微信会出现发送和收藏),故这里还需要制定具体分享
     * <p/>界面的全类名,才能实现快速分享
     * @param context
     * @param componentName 要分享的社交app的包名，分享界面Activity的全类名
     * @param subject 分享的主题
     * @param imageTitle 图片标题
     * @param fileUri 分享图片的Uri
     */
    public static void shareImageChina(Context context, ComponentName componentName, String subject, String imageTitle,  Uri fileUri){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TITLE, imageTitle);
        intent.setComponent(componentName);
        context.startActivity(intent);
    }

    /**
     *  国内视频的分享
     *  <p/>目前国内只支持QQ,微信的直接发送视频,其他的(空间,朋友圈,微博)都需要借助于服务器,需要上传到服务器上,再将Url分享出去
     * @param context
     * @param componentName 要分享的社交app的包名，分享界面Activity的全类名
     * @param subject 分享的主题
     * @param videoTitle 视频标题
     * @param fileUri
     */
    public static void shareVideoChina(Context context, ComponentName componentName, String subject, String videoTitle, Uri fileUri){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setType("video/*");
        intent.putExtra(Intent.EXTRA_STREAM, fileUri);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TITLE, videoTitle);
        intent.setComponent(componentName);
        context.startActivity(intent);
    }

    /**
     * 保存视频文件到本地相册,并生成缩略图,避免分享时没有缩略图而显示为黑色
     * @param path
     */
    public static void saveSdScan(Context context, String path){
        //final Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path, MediaStore.Images.Thumbnails.MICRO_KIND); //生成缩略图
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("_data", path);
        localContentValues.put("description", "save video ---");
        localContentValues.put("mime_type", "video/mp4");
        ContentResolver localContentResolver = context.getContentResolver();
        Uri localUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        localContentResolver.insert(localUri, localContentValues);
        //MediaStore.Images.Media.insertImage(localContentResolver, bitmap, "thumbnail", "thumbnail for video"); //这个是给图片添加缩略图
    }
}
