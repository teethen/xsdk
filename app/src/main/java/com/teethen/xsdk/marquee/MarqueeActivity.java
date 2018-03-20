package com.teethen.xsdk.marquee;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;
import com.teethen.xsdk.marquee.adapter.FragmentPagerItemAdapter;
import com.teethen.xsdk.marquee.fragment.MarqueeFragment;
import com.teethen.xsdk.marquee.fragment.RecyclerViewFragment;

public class MarqueeActivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee);

        initToolBar(R.id.toolbar, "Marquee跑马灯");

        initView();
    }

    private void initView() {
        tabLayout = (TabLayout) findViewById(R.id.marquee_TabLayout);
        viewPager = (ViewPager) findViewById(R.id.marquee_ViewPager);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter.Builder(this, getSupportFragmentManager())
                .add("Common1", new MarqueeFragment())
                .add("Common2", new MarqueeFragment())
                .add("RecyclerView", new RecyclerViewFragment())
                .build();
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
    }

}
