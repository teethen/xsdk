package com.teethen.xsdk.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.teethen.sdk.xbottom.navigation.BottomNavigationBar;
import com.teethen.sdk.xbottom.navigation.BottomNavigationItem;
import com.teethen.sdk.xbottom.navigation.ShapeBadgeItem;
import com.teethen.sdk.xbottom.navigation.TextBadgeItem;
import com.teethen.xsdk.R;
import com.teethen.xsdk.fragment.FragmentText;

import java.util.Arrays;

public class BottomNavigationActivity extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, BottomNavigationBar.OnTabSelectedListener {

    BottomNavigationBar bottomNavigationBar;

    FloatingActionButton fabHome;

    Spinner modeSpinner;
    Spinner shapeSpinner;
    Spinner itemSpinner;
    Spinner bgSpinner;
    CheckBox autoHide;

    Button toggleHide;
    Button toggleBadge; //TextBadgeItem and ShapeBadgeItem (Shape.*) in code line 220 - 226

    TextView message;

    int lastSelectedPosition = 0;

    FragmentText fragment1;
    FragmentText fragment2;
    FragmentText fragment3;
    FragmentText fragment4;
    FragmentText fragment5;
    FragmentText fragment6;

    @Nullable
    TextBadgeItem numberBadgeItem;

    @Nullable
    ShapeBadgeItem shapeBadgeItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        initToolBar(R.id.toolbar, "Bottom Navigation Bar", true);
        initBottomNavigationBar();
        //initViewPager();//implements ViewPager.OnPageChangeListener
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        fabHome = (FloatingActionButton) findViewById(R.id.fab_home);

        modeSpinner = (Spinner) findViewById(R.id.mode_spinner);
        bgSpinner = (Spinner) findViewById(R.id.bg_spinner);
        shapeSpinner = (Spinner) findViewById(R.id.shape_spinner);
        itemSpinner = (Spinner) findViewById(R.id.item_spinner);
        autoHide = (CheckBox) findViewById(R.id.auto_hide);

        toggleHide = (Button) findViewById(R.id.toggle_hide);
        toggleBadge = (Button) findViewById(R.id.toggle_badge);

        message = (TextView) findViewById(R.id.message);

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

        fragment6 = new FragmentText();
        Bundle bundle6 = new Bundle();
        bundle6.putString(FragmentText.TAG_MSG, getString(R.string.para6));
        fragment6.setArguments(bundle6);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(new String[]{"MODE_DEFAULT", "MODE_FIXED", "MODE_SHIFTING", "MODE_FIXED_NO_TITLE", "MODE_SHIFTING_NO_TITLE"}));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        modeSpinner.setSelection(1);

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(new String[]{"3 items", "4 items", "5 items"}));
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itemSpinner.setAdapter(itemAdapter);
        itemSpinner.setSelection(2);

        ArrayAdapter<String> shapeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(new String[]{"SHAPE_OVAL", "SHAPE_RECTANGLE", "SHAPE_HEART", "SHAPE_STAR_3_VERTICES", "SHAPE_STAR_4_VERTICES", "SHAPE_STAR_5_VERTICES", "SHAPE_STAR_6_VERTICES"}));
        shapeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shapeSpinner.setAdapter(shapeAdapter);
        shapeSpinner.setSelection(5);

        ArrayAdapter<String> bgAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Arrays.asList(new String[]{"BACKGROUND_STYLE_DEFAULT", "BACKGROUND_STYLE_STATIC", "BACKGROUND_STYLE_RIPPLE"}));
        bgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bgSpinner.setAdapter(bgAdapter);
        bgSpinner.setSelection(1);

        modeSpinner.setOnItemSelectedListener(this);
        bgSpinner.setOnItemSelectedListener(this);
        shapeSpinner.setOnItemSelectedListener(this);
        itemSpinner.setOnItemSelectedListener(this);
        autoHide.setOnCheckedChangeListener(this);

        toggleHide.setOnClickListener(this);
        toggleBadge.setOnClickListener(this);
        fabHome.setOnClickListener(this);

        bottomNavigationBar.setTabSelectedListener(this);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.toggle_hide) {
            if (bottomNavigationBar != null) {
                bottomNavigationBar.toggle();
            }
        } else if (v.getId() == R.id.toggle_badge) {
            if (numberBadgeItem != null) {
                numberBadgeItem.toggle();
            }
            if (shapeBadgeItem != null) {
                shapeBadgeItem.toggle();
            }
            //numberBadgeItem.setMessage("new");==numberBadgeItem.setText("new");
        } else if (v.getId() == R.id.fab_home) {
            final Snackbar snackbar = Snackbar.make(message, "Fab Clicked", Snackbar.LENGTH_LONG);
            snackbar.setAction("dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();
                }
            });
            snackbar.show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        refresh();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        refresh();
    }

    private void refresh() {

        bottomNavigationBar.clearAll();
        //bottomNavigationBar.setFab(fabHome, BottomNavigationBar.FAB_BEHAVIOUR_TRANSLATE_AND_STICK);
        bottomNavigationBar.setFab(fabHome);

        setScrollableText(lastSelectedPosition);

        numberBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.blue)
                .setText("" + lastSelectedPosition)
                .setHideOnSelect(autoHide.isChecked());

        shapeBadgeItem = new ShapeBadgeItem()
                .setShape(shapeSpinner.getSelectedItemPosition())
                .setShapeColorResource(R.color.teal)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(autoHide.isChecked());

        bottomNavigationBar.setMode(modeSpinner.getSelectedItemPosition());
        bottomNavigationBar.setBackgroundStyle(bgSpinner.getSelectedItemPosition());


        if (itemSpinner.getSelectedItemPosition() == 0) {
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_location_on_white_24dp, "Nearby").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_find_replace_white_24dp, "Find").setActiveColorResource(R.color.teal))
                    .addItem(new BottomNavigationItem(R.drawable.ic_favorite_white_24dp, "Categories").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                    .setFirstSelectedPosition(lastSelectedPosition > 2 ? 2 : lastSelectedPosition)
                    .initialise();
        } else if (itemSpinner.getSelectedItemPosition() == 1) {
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                    .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                    .setFirstSelectedPosition(lastSelectedPosition > 3 ? 3 : lastSelectedPosition)
                    .initialise();
        } else if (itemSpinner.getSelectedItemPosition() == 2) {
            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.drawable.ic_home_white_24dp, "Home").setActiveColorResource(R.color.orange).setBadgeItem(numberBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_book_white_24dp, "Books").setActiveColorResource(R.color.teal))
                    .addItem(new BottomNavigationItem(R.drawable.ic_music_note_white_24dp, "Music").setActiveColorResource(R.color.blue).setBadgeItem(shapeBadgeItem))
                    .addItem(new BottomNavigationItem(R.drawable.ic_tv_white_24dp, "Movies & TV").setActiveColorResource(R.color.brown))
                    .addItem(new BottomNavigationItem(R.drawable.ic_videogame_asset_white_24dp, "Games").setActiveColorResource(R.color.grey))
                    .setFirstSelectedPosition(lastSelectedPosition)
                    .initialise();
        }
    }

    @Override
    public void onTabSelected(int position) {
        lastSelectedPosition = position;
        setMessageText(position + " Tab Selected");
        if (numberBadgeItem != null) {
            numberBadgeItem.setText(Integer.toString(position));
        }
        setScrollableText(position);
    }

    @Override
    public void onTabUnselected(int position) {
    }

    @Override
    public void onTabReselected(int position) {
        setMessageText(position + " Tab Reselected");
    }

    private void setMessageText(String messageText) {
        message.setText(messageText);
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
                getSupportFragmentManager().beginTransaction().replace(R.id.home_activity_frag_container, fragment6).commitAllowingStateLoss();
                break;
        }
    }

    //ViewPager 滑动
    /*private ViewPager viewPager;
    private List<Fragment> fragments;
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.bottom_nav_view_pager);

        fragments = new ArrayList<Fragment>();
        fragments.add(new HomeFragment());
        fragments.add(new CatagoryFragment());
        fragments.add(new DiscoverFragment());
        fragments.add(new UserCentralFragment());

        viewPager.setAdapter(new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationBar.selectTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }*/
}
