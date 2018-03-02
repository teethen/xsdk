package com.teethen.xsdk.ninegrid.complex.news;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.teethen.xsdk.ninegrid.complex.news.bean.NewsChannel;

import java.util.List;

/**
 * Created by xingq on 2018/2/24.
 */

public class NewsChannelAdapter extends FragmentPagerAdapter {
    private List<NewsChannel> channelItems;

    public NewsChannelAdapter(FragmentManager fm, List<NewsChannel> channelItems) {
        super(fm);
        this.channelItems = channelItems;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return channelItems.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {

        NewsFragment fragment = new NewsFragment();
        NewsChannel channel = channelItems.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("channelId", channel.getChannelId());
        bundle.putInt("page", 1);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return channelItems.size();
    }
}
