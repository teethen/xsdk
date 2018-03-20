package com.teethen.xsdk.ninegrid.simple;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teethen.sdk.xutil.LayoutUtil;
import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;
import com.teethen.xsdk.test.PhotoViewTest;

import java.util.List;

import butterknife.BindView;

public class SimpleNineGridActivity extends BaseActivity {

    @BindView(R.id.ninegrid_rv) RecyclerView imgRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_ninegrid);

        initToolBar(R.id.toolbar, "简单九宫格测试");

        setListener();
    }

    private void setListener() {
        List<String> list = PhotoViewTest.getBaiduImgs();
        SimpleNineGridAdapter adapter = new SimpleNineGridAdapter(this, list);
        LinearLayoutManager layoutManager = LayoutUtil.getLinearLayoutManager(this);
        LayoutUtil.setupRecyclerView(imgRV, layoutManager);
        imgRV.setAdapter(adapter);
    }

}
