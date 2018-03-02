package com.teethen.xsdk.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.teethen.sdk.xbottom.barlayout.BottomBarItem;
import com.teethen.sdk.xbottom.barlayout.BottomBarLayout;
import com.teethen.xsdk.ActivityCollector;
import com.teethen.xsdk.R;
import com.teethen.xsdk.fragment.FragmentViewPagerAdapter;
import com.teethen.xsdk.fragment.FragmentTab;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    private ViewPager mVpContent;
    private BottomBarLayout mBottomBarLayout;

    private List<Fragment> mFragmentList = new ArrayList<>();
    private RotateAnimation mRotateAnimation;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //侧边导航
        initDrawerToolbar();
        initNavHeaderLayout();

        //底部导航
        initView();
        initData();
        initListener();
    }

    //侧边导航
    private void initDrawerToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_header_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initNavHeaderLayout() {
        View view = navigationView.getHeaderView(0); //navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView tvUserName = (TextView) view.findViewById(R.id.txtUserName);
        TextView tvUserMobile = (TextView) view.findViewById(R.id.txtUserMobile);

        tvUserName.setText("邢庆祥");
        tvUserMobile.setText("15981958077");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = null;
            if (id == R.id.nav_manage) {
                //intent = new Intent(MainActivity.this, SettingsActivity.class);
            } else if (id == R.id.nav_logout) {
                //sp.putString(SpKeys.KEY_LOGIN_TOKEN, "");
                ActivityCollector.finishAll();
                //intent = new Intent(MainActivity.this, LoginActivity.class);
            }
            if (intent != null) {
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
            } else {
                showToast("功能正在完善中，敬请期待...");
            }
        }
        return true;
    }

    //右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        } else if(id != R.id.action_more) {
            Intent intent = null;

            if (id == R.id.menu_1) {
                intent = new Intent(this, TestActivity.class);
            } else if (id == R.id.menu_2) {
                intent = new Intent(this, BottomNavigationActivity.class);
            } else if (id == R.id.menu_3) {
                intent = new Intent(this, BottomNavigationViewPagerActivity.class);
            } else if (id == R.id.menu_4) {

            }

            if (intent != null) {
                startActivity(intent);
            } else {
                showToast("您点击了 " + item.getTitle().toString(), Gravity.CENTER);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //底部导航
    private void initView() {
        mVpContent = (ViewPager) findViewById(R.id.vp_content);
        mBottomBarLayout = (BottomBarLayout) findViewById(R.id.bbl);
    }

    private void initData() {

        FragmentTab homeFragment = new FragmentTab();
        Bundle bundle1 = new Bundle();
        bundle1.putString(FragmentTab.CONTENT, "首页");
        homeFragment.setArguments(bundle1);
        mFragmentList.add(homeFragment);

        FragmentTab videoFragment = new FragmentTab();
        Bundle bundle2 = new Bundle();
        bundle2.putString(FragmentTab.CONTENT, "视频");
        videoFragment.setArguments(bundle2);
        mFragmentList.add(videoFragment);

        FragmentTab microFragment = new FragmentTab();
        Bundle bundle3 = new Bundle();
        bundle3.putString(FragmentTab.CONTENT, "消息");
        microFragment.setArguments(bundle3);
        mFragmentList.add(microFragment);

        FragmentTab meFragment = new FragmentTab();
        Bundle bundle4 = new Bundle();
        bundle4.putString(FragmentTab.CONTENT, "我的");
        meFragment.setArguments(bundle4);
        mFragmentList.add(meFragment);
    }

    private void initListener() {
        mVpContent.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mBottomBarLayout.setViewPager(mVpContent);
        mBottomBarLayout.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int position) {
                Log.i("MainActivity","position: " + position);
                if (position == 0){
                    //如果是第一个，即首页
                    if (mBottomBarLayout.getCurrentItem() == position){
                        //如果是在原来位置上点击,更换首页图标并播放旋转动画
                        bottomBarItem.setIconSelectedResourceId(R.drawable.tab_loading);//更换成加载图标
                        bottomBarItem.setStatus(true);

                        //播放旋转动画
                        if (mRotateAnimation == null) {
                            mRotateAnimation = new RotateAnimation(0, 360,
                                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                                    0.5f);
                            mRotateAnimation.setDuration(800);
                            mRotateAnimation.setRepeatCount(-1);
                        }
                        ImageView bottomImageView = bottomBarItem.getImageView();
                        bottomImageView.setAnimation(mRotateAnimation);
                        bottomImageView.startAnimation(mRotateAnimation);//播放旋转动画

                        //模拟数据刷新完毕
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bottomBarItem.setIconSelectedResourceId(R.drawable.tab_home_selected);//更换成首页原来图标
                                bottomBarItem.setStatus(true);//刷新图标
                                cancelTabLoading(bottomBarItem);
                            }
                        },3000);
                        return;
                    }
                }

                //如果点击了其他条目
                BottomBarItem bottomItem = mBottomBarLayout.getBottomItem(0);
                bottomItem.setIconSelectedResourceId(R.drawable.tab_home_selected);//更换为原来的图标

                cancelTabLoading(bottomItem);//停止旋转动画
            }
        });

        mBottomBarLayout.setUnread(0,20);//设置第一个页签的未读数为20
        mBottomBarLayout.setUnread(1,1001);//设置第二个页签的未读数
        mBottomBarLayout.showNotify(2);//设置第三个页签显示提示的小红点
        mBottomBarLayout.setMsg(3,"NEW");//设置第四个页签显示NEW提示文字
    }

    /**停止首页页签的旋转动画*/
    private void cancelTabLoading(BottomBarItem bottomItem) {
        Animation animation = bottomItem.getImageView().getAnimation();
        if (animation != null){
            animation.cancel();
        }
    }
}
