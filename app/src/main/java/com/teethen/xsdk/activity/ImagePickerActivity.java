package com.teethen.xsdk.activity;

import android.os.Bundle;

import com.teethen.xsdk.R;

public class ImagePickerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);

        initToolBar(R.id.toolbar, "ImagePicker Test");

    }

}
