package com.teethen.xsdk.mediapicker;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.teethen.sdk.xdialog.dialogui.DialogUIUtils;
import com.teethen.sdk.xdialog.dialogui.bean.TieBean;
import com.teethen.sdk.xdialog.dialogui.listener.DialogUIItemListener;
import com.teethen.sdk.xmediapicker.MediaItem;
import com.teethen.sdk.xmediapicker.activities.MediaPickerActivity;
import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class MediaPickerTestActivity extends BaseActivity {

    private LinearLayout mediaLinearLayout;
    private List<MediaItem> mMediaSelectedList;
    private LinearLayout filesLinearLayout;
    private List<FileItem> mFilesSelectedList;

    private ImageButton btnAttach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_picker);

        try {
            initToolBar(R.id.toolbar, "MediaPickerTest");
            initView();
            setListener();
        } catch (Exception e) {
        }
    }

    private void initView() {
        btnAttach = findViewById(R.id.btnAttach);
        mediaLinearLayout = (LinearLayout) findViewById(R.id.list_media);
        filesLinearLayout = (LinearLayout) findViewById(R.id.list_files);
    }

    private void setListener() {
        //添加文档/附件
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<TieBean> list = new ArrayList<TieBean>();
                list.add(new TieBean("照片/视频"));
                list.add(new TieBean("文档/文件"));

                DialogUIUtils.showSheet(MediaPickerTestActivity.this, list, "取消", Gravity.BOTTOM, true, true, new DialogUIItemListener() {
                    @Override
                    public void onItemClick(CharSequence text, int position) {
                        if (position == 0) {
                            DocUtil.handleOptionSelected(MediaPickerTestActivity.this, mediaLinearLayout, mMediaSelectedList, 0);
                        } else if (position == 1) {
                            if (mFilesSelectedList == null) {
                                mFilesSelectedList = new ArrayList<>();
                            }
                            DocUtil.handleFileOptionSelected(MediaPickerTestActivity.this);
                        }
                    }
                    @Override
                    public void onBottomBtnClick() {
                    }
                }).show();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DocUtil.REQUEST_CODE_MEDIA) {
            if (resultCode == RESULT_OK) {
                mMediaSelectedList = MediaPickerActivity.getMediaItemSelected(data);
                if (mMediaSelectedList != null) {
                    for (MediaItem mediaItem : mMediaSelectedList) {
                        DocUtil.addImages(this, mediaLinearLayout, mediaItem, mMediaSelectedList);
                    }
                } else {
                    showToast("Error to get media, NULL", Gravity.BOTTOM);
                }
            }
        } else if (requestCode == DocUtil.REQUEST_CODE_FILES) {
            if (resultCode == RESULT_OK) {
                FileItem fileItem = DocUtil.getFileItem(this, data);
                DocUtil.addFiles(this, filesLinearLayout, fileItem, mFilesSelectedList);
            }
        }
    }

}
