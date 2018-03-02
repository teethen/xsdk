package com.teethen.xsdk.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.teethen.xsdk.fragment.FragmentTab;

import java.util.List;

/**
 * Created by xingq on 2018/2/23.
 */

public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragments;

    public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
