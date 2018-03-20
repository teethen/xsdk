package com.teethen.xsdk.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.teethen.sdk.xbottom.navigation.BottomNavigationBar;
import com.teethen.sdk.xbottom.navigation.BottomNavigationItem;
import com.teethen.sdk.xbottom.navigation.ShapeBadgeItem;
import com.teethen.sdk.xbottom.navigation.TextBadgeItem;
import com.teethen.xsdk.R;
import com.teethen.xsdk.fragment.FragmentViewPagerAdapter;
import com.teethen.xsdk.fragment.FragmentText;
import com.teethen.xsdk.marquee.adapter.FragmentPagerItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部导航栏 (ViewPager 滑动)
 */

public class BottomNavigationViewPagerActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    BottomNavigationBar bottomNavigationBar;
    int lastSelectedPosition = 0;

    FragmentText fragment1;
    FragmentText fragment2;
    FragmentText fragment3;
    FragmentText fragment4;
    FragmentText fragment5;

    @Nullable
    TextBadgeItem numberBadgeItem;

    @Nullable
    ShapeBadgeItem shapeBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_viewpager);

        initToolBar(R.id.toolbar, "Bottom Navigation Bar", true);

        initBottomNavigationBar();
        initViewPager();//implements ViewPager.OnPageChangeListener
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        fragment1 = new FragmentText();
        Bundle bundle1 = new Bundle();
        bundle1.putString(FragmentText.TAG_MSG, getString(R.string.para1));
        fragment1.setArguments(bundle1);

        fragment2 = new FragmentText();
        Bundle bundle2 = new Bundle();
        bundle2.putString(FragmentText.TAG_MSG, getString(R.string.para2));
        fragment2.setArguments(bundle2);

        fragment3 = new FragmentText();
        Bundle bundle3 = new Bundle();
        bundle3.putString(FragmentText.TAG_MSG, getString(R.string.para3));
        fragment3.setArguments(bundle3);

        fragment4 = new FragmentText();
        Bundle bundle4 = new Bundle();
        bundle4.putString(FragmentText.TAG_MSG, getString(R.string.para4));
        fragment4.setArguments(bundle4);

        fragment5 = new FragmentText();
        Bundle bundle5 = new Bundle();
        bundle5.putString(FragmentText.TAG_MSG, getString(R.string.para5));
        fragment5.setArguments(bundle5);

        bottomNavigationBar.setTabSelectedListener(this);

        refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_navigation_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_github:
                String url = "https://github.com/Ashok-Varma/BottomNavigation";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refresh() {

        bottomNavigationBar.clearAll();

        //setScrollableText(lastSelectedPosition);

        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.red)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(true);

        shapeBadgeItem = new ShapeBadgeItem()
                .setShape(ShapeBadgeItem.SHAPE_OVAL)
                .setShapeColorResource(R.color.teal)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(true);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey))
                .setFirstSelectedPosition(lastSelectedPosition)
                .initialise();
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        if (numberBadgeItem != null) {
            boolean isHidden = numberBadgeItem.isHidden();
            if (position >= 2) {
                if (isHidden) {
                    numberBadgeItem.show(true);
                }
            } else {
                if (!isHidden) {
                    numberBadgeItem.hide(true);
                }
            }
            /*if (numberBadgeItem.isHidden()) {
                numberBadgeItem.toggle();//在show和hide之间切换
            }*/

            numberBadgeItem.setText(Integer.toString(position));
        }
        //setScrollableText(position);

        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
        //position + " Tab Reselected"
    }

    private void setScrollableText(int position) {
        switch (position) {
            case 0:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment1).commitAllowingStateLoss();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment2).commitAllowingStateLoss();
                break;
            case 2:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment3).commitAllowingStateLoss();
                break;
            case 3:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment4).commitAllowingStateLoss();
                break;
            case 4:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment5).commitAllowingStateLoss();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment1).commitAllowingStateLoss();
                break;
        }
    }

    //ViewPager 滑动
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private void initViewPager() {

        viewPager = (ViewPager) findViewById(R.id.bottom_nav_view_pager);

        if (fragments == null) {
            fragments = new ArrayList<Fragment>();
        }
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);

        viewPager.requestDisallowInterceptTouchEvent(false);
        viewPager.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
