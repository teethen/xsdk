package com.teethen.xsdk.recyvlergroup;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;
import com.teethen.xsdk.recyvlergroup.adapter.WeChatNewMsgGroupAdapter;

public class WeChatNewMsgActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_group);

        initView();
    }

    private void initView() {
        initToolBar(R.id.toolbar, getString(R.string.act_wechat_new_msg));

        RecyclerView recyclerView = findViewById(R.id.recyclerViewGroup);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WeChatNewMsgGroupAdapter weChatAdapter = new WeChatNewMsgGroupAdapter(recyclerView, DataSource.WeChatNewMsgItems);
        recyclerView.setAdapter(weChatAdapter);

        weChatAdapter.setOnItemClickListener((adapter, data, groupPosition, childPosition) -> {
            if (null == data || 0 == data.getKey()) {
                return;
            }

            if (R.attr.key_new_message_voice == data.getKey()) {
                showToast("你是我的小呀小苹果^_^");
            }
        });

    }
}
