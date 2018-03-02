package com.teethen.xsdk.ninegrid.complex.news;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.teethen.sdk.xhttp.okgo.OkHttp;
import com.teethen.sdk.xhttp.okgo.cache.CacheMode;
import com.teethen.sdk.xwidget.tab.PagerSlidingTabStrip;
import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;
import com.teethen.xsdk.ninegrid.complex.news.bean.NewsChannel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsActivity extends BaseActivity {

    @BindView(R.id.tab) PagerSlidingTabStrip tab;
    @BindView(R.id.viewPager) ViewPager viewPager;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initToolBar(R.id.toolbar, "新闻频道");

        /*emptyView = View.inflate(this, R.layout.load_loading, null);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(emptyView, params);*/

        List<NewsChannel> channelItems = new ArrayList<>();
        NewsChannel channel1 = new NewsChannel();
        channel1.setChannelId("新浪");
        channel1.setName("新浪");
        channelItems.add(channel1);
        NewsChannel channel2 = new NewsChannel();
        channel2.setChannelId("搜狐");
        channel2.setName("搜狐");
        channelItems.add(channel2);
        viewPager.setAdapter(new NewsChannelAdapter(getSupportFragmentManager(), channelItems));
        tab.setViewPager(viewPager);

        /*OkHttp.<String>get(Urls.CHANNEL)
                .tag(this)
                .cacheKey("CHANNEL")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new NewsCallBack() {
                    @Override
                    public void onSuccess(com.teethen.sdk.xhttp.okgo.model.Response<String> response) {
                        try {
                            emptyView.setVisibility(View.GONE);
                            String s = response.body();
                            JSONArray object = new JSONObject(s).getJSONObject("showapi_res_body").getJSONArray("channelList");
                            Type channelItemType = new TypeToken<List<NewsChannel>>() {}.getType();
                            List<NewsChannel> channelItems = new Gson().fromJson(object.toString(), channelItemType);
                            viewPager.setAdapter(new NewsChannelAdapter(getSupportFragmentManager(), channelItems));
                            tab.setViewPager(viewPager);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });*/
    }

}
