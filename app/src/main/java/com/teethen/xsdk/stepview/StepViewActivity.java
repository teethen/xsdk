package com.teethen.xsdk.stepview;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;
import com.teethen.xsdk.stepview.fragment.DrawCanvasFragment;
import com.teethen.xsdk.stepview.fragment.HorizontalStepViewFragment;
import com.teethen.xsdk.stepview.fragment.VerticalStepViewFrowardFragment;
import com.teethen.xsdk.stepview.fragment.VerticalStepViewReverseFragment;

public class StepViewActivity extends BaseActivity {

    private VerticalStepViewReverseFragment mVerticalStepViewFragment;
    private DrawCanvasFragment mDrawCanvasFragment;
    private HorizontalStepViewFragment mHorizontalStepviewFragment;
    private VerticalStepViewFrowardFragment mVerticalStepViewReverseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepview);

        initToolBar(R.id.toolbar, "StepViewTest");

        initView();
    }

    private void initView() {
        mVerticalStepViewFragment = new VerticalStepViewReverseFragment();
        getFragmentManager().beginTransaction().replace(R.id.stepview_container, mVerticalStepViewFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.stepview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.action_stepview_horizontal:
                if (mHorizontalStepviewFragment == null) {
                    mHorizontalStepviewFragment = new HorizontalStepViewFragment();
                }
                fragmentTransaction.replace(R.id.stepview_container, mHorizontalStepviewFragment).commit();
                break;

            case R.id.action_stepview_drawcanvas:
                if (mDrawCanvasFragment == null) {
                    mDrawCanvasFragment = new DrawCanvasFragment();
                }
                fragmentTransaction.replace(R.id.stepview_container, mDrawCanvasFragment).commit();
                break;
            case R.id.action_stepview_vertical_reverse:
                if (mVerticalStepViewFragment == null) {
                    mVerticalStepViewFragment = new VerticalStepViewReverseFragment();
                }
                fragmentTransaction.replace(R.id.stepview_container, mVerticalStepViewFragment).commit();
                break;

            case R.id.action_stepview_vertical_forward:
                if (mVerticalStepViewReverseFragment == null) {
                    mVerticalStepViewReverseFragment = new VerticalStepViewFrowardFragment();
                }
                fragmentTransaction.replace(R.id.stepview_container, mVerticalStepViewReverseFragment).commit();
                break;

            case R.id.action_test_horizontal_stepview:
                startActivity(new Intent(this, StepViewHorizontalActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
