package com.teethen.xsdk.recyvlergroup;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;
import com.teethen.xsdk.recyvlergroup.adapter.WeChatMeGroupAdapter;

public class WeChatMeActivity extends BaseActivity {

    private int clickProfileCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_group);

        initView();
    }

    private void initView() {
        initToolBar(R.id.toolbar, getString(R.string.act_wechat_me));

        RecyclerView recyclerView = findViewById(R.id.recyclerViewGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WeChatMeGroupAdapter weChatAdapter = new WeChatMeGroupAdapter(recyclerView, DataSource.WeChatWeItems);
        recyclerView.setAdapter(weChatAdapter);

        weChatAdapter.setOnItemClickListener((adapter, data, groupPosition, childPosition) -> {
            if (null == data || 0 == data.getKey()) {
                return;
            }

            if (R.attr.key_profile == data.getKey()) {
                showToast("惊喜 + " + (++clickProfileCount));
            } else {
                showToast(getString(data.getTitleId()));
            }
        });
    }
}
