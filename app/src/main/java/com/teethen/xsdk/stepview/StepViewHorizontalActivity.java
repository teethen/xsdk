package com.teethen.xsdk.stepview;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.teethen.sdk.xwidget.stepview.HorizontalStepView;
import com.teethen.sdk.xwidget.stepview.bean.StepBean;
import com.teethen.xsdk.R;
import com.teethen.xsdk.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class StepViewHorizontalActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stepview_horizontal);

        initToolBar(R.id.toolbar, "Horizontal StepView");
        initView();
    }

    private void initView() {

        findViewById(R.id.btn_stepview_horizontal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HorizontalStepView stepView = (HorizontalStepView) findViewById(R.id.stepview_horizontal);
                //-----------------------------this data is example and you can also get data from server-----------------------------
                List<StepBean> stepsBeanList = new ArrayList<>();
                StepBean stepBean0 = new StepBean("接单",1);
                StepBean stepBean1 = new StepBean("打包",1);
                StepBean stepBean2 = new StepBean("出发",0);
                StepBean stepBean3 = new StepBean("送单",-1);
                StepBean stepBean4 = new StepBean("完成",-1);
                StepBean stepBean5 = new StepBean("支付",-1);
                stepsBeanList.add(stepBean0);
                stepsBeanList.add(stepBean1);
                stepsBeanList.add(stepBean2);
                stepsBeanList.add(stepBean3);
                stepsBeanList.add(stepBean4);
                stepsBeanList.add(stepBean5);
                //-----------------------------this data is example and you can also get data from server-----------------------------

                stepView.setStepViewTexts(stepsBeanList)
                        .setTextSize(16)//set textSize
                        .setStepsViewIndicatorCompletedLineColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsViewIndicator完成线的颜色
                        .setStepsViewIndicatorUnCompletedLineColor(ContextCompat.getColor(getBaseContext(), R.color.stepview_uncompleted_text))//设置StepsViewIndicator未完成线的颜色
                        .setStepViewComplectedTextColor(ContextCompat.getColor(getBaseContext(), android.R.color.white))//设置StepsView text完成线的颜色
                        .setStepViewUnComplectedTextColor(ContextCompat.getColor(getBaseContext(), R.color.stepview_uncompleted_text))//设置StepsView text未完成线的颜色
                        .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.stepview_complted))//设置StepsViewIndicator CompleteIcon
                        .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.stepview_default))//设置StepsViewIndicator DefaultIcon
                        .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(getBaseContext(), R.drawable.stepview_attention));//设置StepsViewIndicator AttentionIcon
            }
        });

    }

}
