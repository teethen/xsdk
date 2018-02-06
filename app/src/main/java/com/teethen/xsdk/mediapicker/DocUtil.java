package com.teethen.xsdk.mediapicker;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teethen.sdk.xmediapicker.MediaItem;
import com.teethen.sdk.xmediapicker.MediaOptions;
import com.teethen.sdk.xmediapicker.activities.MediaPickerActivity;
import com.teethen.sdk.xutil.FileUtil;
import com.teethen.sdk.xutil.ToastUtil;
import com.teethen.xsdk.R;

import java.io.File;
import java.util.List;

/**
 * Created by xingq on 2017/12/5.
 */

public class DocUtil {
    private static final String TAG = "DocUtil";
    private static final String UriSchemaFile = "file";
    private static final String UriSchemaContent = "content";

    public static final int REQUEST_CODE_MEDIA = 20171;
    public static final int REQUEST_CODE_FILES = 20172;
    private static final String[] extension = new String[] {".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf", ".txt", ".log", ".json", ".wps", ".rar", ".zip"};


    //===================== 添加 照片或视频 BEGIN =====================//
    //MediaPicker add images
    public static void addImages (final Activity context, final LinearLayout mediaLinearLayout, final MediaItem mediaItem, final List<MediaItem> mediaSelectedList) {
        if (mediaItem != null) {
            final LinearLayout root = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_item_media, null);

            ImageView deleteIv = (ImageView) root.findViewById(R.id.media_image_delete);
            ImageView imageView = (ImageView) root.findViewById(R.id.media_image);
            TextView textView = (TextView) root.findViewById(R.id.media_name);

            //String info = String.format("Original Uri [%s]\nOriginal Path [%s] \n\nCropped Uri [%s] \nCropped Path[%s]", mediaItem.getUriOrigin(), mediaItem.getUriCropped(), mediaItem.getPathOrigin(this), mediaItem.getPathCropped(this));
            //String info = String.format("路径[%s]", mediaItem.getPathOrigin(context));
            final String originalPath = mediaItem.getPathOrigin(context);
            String info = FileUtil.getFileName(originalPath);
            textView.setText(info);

            if (mediaItem.getUriCropped() == null) {
                Glide.with(context).load(mediaItem.getUriOrigin()).into(imageView);
            } else {
                Glide.with(context).load(mediaItem.getUriCropped()).into(imageView);
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 5;
            mediaLinearLayout.addView(root, params);

            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent = FileIntentUtil.getFileIntent(originalPath);
                   context.startActivity(intent);
                }
            });*/

            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mediaSelectedList != null && mediaSelectedList.contains(mediaItem)) {
                        mediaSelectedList.remove(mediaItem);
                    }
                    mediaLinearLayout.removeView(root);
                }
            });
        }
    }
    //MediaPicker handle images/videos
    public static void handleOptionSelected(Activity context, LinearLayout mediaLinearLayout, List<MediaItem> mediaSelectedList, int option) {
        MediaOptions options = null;
        MediaOptions.Builder builder = new MediaOptions.Builder();
        switch (option) {
            case 0:
                options = builder
                        .canSelectMultiPhoto(true)
                        .canSelectMultiVideo(true)
                        .canSelectBothPhotoVideo()
                        .setIsCropped(true)
                        .setFixAspectRatio(false)
                        .setMinVideoDuration(5 * 1000)
                        .setMaxVideoDuration(60 * 1000)
                        .setShowWarningBeforeRecordVideo(true)
                        .setMediaListSelected(mediaSelectedList)
                        .build();
                break;
        }
        if (options != null) {
            clearImages(mediaLinearLayout);
            try {
                MediaPickerActivity.open(context, REQUEST_CODE_MEDIA, options);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        }
    }
    //MediaPicker clear images
    private static void clearImages(LinearLayout mediaLinearLayout) {
        mediaLinearLayout.removeAllViews();
    }
    //===================== 添加 照片或视频 END =====================//


    //===================== 添加 常用文档文件 BEGIN =====================//
    public static void handleFileOptionSelected(Activity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("*/*");
        intent.setType("application/*;text/*;audio/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            //context.startActivityForResult(intent, REQUEST_CODE_FILES);
            context.startActivityForResult(Intent.createChooser(intent, "选择文件"), REQUEST_CODE_FILES);
        } catch (Exception e) {
            ToastUtil.showToast(context, "未找到文件管理器 -_- ");
        }
    }

    public static FileItem getFileItem (Activity context, Intent data) {

        FileItem item = null;
        String filePath = null;

        Uri uri = data.getData();

        if (UriSchemaContent.equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE };

            //构造筛选语句
            /*String selection = "";
            for(int i = 0; i < extension.length; i++)
            {
                if(i != 0)
                {
                    selection += " OR ";
                }
                selection += MediaStore.Files.FileColumns.DATA + " LIKE '%" + extension[i] + "'";
            }
            String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, projection, selection, null, sortOrder);*/

            String sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED;
            ContentResolver resolver = context.getContentResolver();
            Cursor cursor = resolver.query(uri, projection, null, null, sortOrder);
            if (cursor != null) {
                int columnIndexPath = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
                //int columnIndexName = cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME);
                if(cursor.moveToLast()) {
                    do{
                        filePath = cursor.getString(columnIndexPath);
                        //fileName = cursor.getString(columnIndexName);
                    } while(cursor.moveToPrevious());
                }
                cursor.close();
            }
        } else if (UriSchemaFile.equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }

        if (!TextUtils.isEmpty(filePath)) {
            //Log.i(TAG, filePath);
            File file = new File(filePath);
            if (file != null) {
                item = new FileItem();
                item.setFileName(file.getName());
                item.setType(FileUtil.getExtension(file.getName()));
                item.setOriginalPath(file.getPath());
            }
        }

        return item;
    }

    //Add Common type doc files
    public static void addFiles (final Activity context, final LinearLayout fileLinearLayout, final FileItem fileItem, final List<FileItem> fileSelectedList) {
        if (fileItem != null) {

            fileSelectedList.add(fileItem);

            final LinearLayout item = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_item_media, null);

            ImageView deleteIv = (ImageView) item.findViewById(R.id.media_image_delete);
            ImageView imageView = (ImageView) item.findViewById(R.id.media_image);
            TextView textView = (TextView) item.findViewById(R.id.media_name);

            //textView.setText(fileItem.getOriginalPath()); //完整路径
            textView.setText(fileItem.getFileName()); //文件名
            switch (fileItem.getType()) {
                case "doc":
                case "docx":
                    Glide.with(context).load("W").into(imageView);
                    break;
                case "xls":
                case "xlsx":
                    Glide.with(context).load("E").into(imageView);
                    break;
                case "ppt":
                case "pptx":
                    Glide.with(context).load("P").into(imageView);
                    break;
                case "pdf":
                    Glide.with(context).load("PDF").into(imageView);
                    break;
                case "log":
                case "txt":
                case "json":
                    Glide.with(context).load("TXT").into(imageView);
                    break;
                case "jpg":
                case "jpeg":
                case "png":
                case "bmp":
                case "tif":
                case "tiff":
                case "gif":
                    Glide.with(context).load(fileItem.getOriginalPath()).into(imageView);
                    break;
                default:
                    Glide.with(context).load("?").into(imageView);
                    break;
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin = 5;
            fileLinearLayout.addView(item, params);

            /*imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ("doc,docx,xls,xlsx,ppt,pptx".indexOf(fileItem.getType()) > -1) {
                        Intent intent = new Intent(context, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.WEB_VIEW_URL, fileItem.getOriginalPath());
                        context.startActivity(intent);
                    } else {
                        Intent intent = FileIntentUtil.getFileIntent(fileItem.getOriginalPath());
                        context.startActivity(intent);
                    }
                }
            });*/

            deleteIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fileSelectedList != null && fileSelectedList.contains(fileItem)) {
                        fileSelectedList.remove(fileItem);
                    }
                    fileLinearLayout.removeView(item);
                }
            });
        }
    }
    //===================== 添加 常用文档文件 END =====================//


}
