package com.teethen.xsdk.marquee.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.teethen.sdk.xutil.ControlUtil;
import com.teethen.sdk.xutil.ToastUtil;
import com.teethen.sdk.xwidget.marquee.MarqueeView;
import com.teethen.xsdk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by xingq on 2017/8/8.
 */
public class MarqueeFragment extends Fragment {

    private MarqueeView marqueeView;
    private MarqueeView marqueeView1;
    private MarqueeView marqueeView2;
    private MarqueeView marqueeView3;
    private MarqueeView marqueeView4;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_marquee, container, false);

        //通过代码设置TextView的Marquee效果
        ControlUtil.setTextMarquee(view.findViewById(R.id.tv_test_marquee));

        marqueeView = view.findViewById(R.id.marqueeView);
        marqueeView1 = view.findViewById(R.id.marqueeView1);
        marqueeView2 = view.findViewById(R.id.marqueeView2);
        marqueeView3 = view.findViewById(R.id.marqueeView3);
        marqueeView4 = view.findViewById(R.id.marqueeView4);

        List<CharSequence> list = new ArrayList<>();
        SpannableString ss1 = new SpannableString("1.MarqueeView 1 邢庆祥");
        ss1.setSpan(new ForegroundColorSpan(Color.RED), 2, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss1);
        SpannableString ss2 = new SpannableString("2.邢庆祥在测试跑马灯 MarqueeView 2");
        ss2.setSpan(new ForegroundColorSpan(Color.GREEN), 9, 20, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss2);
        SpannableString ss3 = new SpannableString("3.MarqueeView 3，跑马灯测试人员 邢庆祥");
        ss3.setSpan(new URLSpan("http://www.teethen.com/"), 7, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        list.add(ss3);
        list.add("4.邢庆祥");

        marqueeView.startWithList(list);
        marqueeView.setOnItemClickListener((position, textView) -> ToastUtil.showToast(getContext(), textView.getText().toString()));

        marqueeView1.startWithText(getString(R.string.marquee_texts), R.anim.marquee_anim_top_in, R.anim.marquee_anim_bottom_out);
        marqueeView1.setOnItemClickListener((position, textView) -> ToastUtil.showToast(getContext(), String.valueOf(marqueeView1.getPosition()) + ". " + textView.getText()));

        marqueeView2.startWithText(getString(R.string.marquee_text));

        marqueeView3.startWithText(getString(R.string.marquee_texts));

        marqueeView4.startWithText(getString(R.string.marquee_texts));

        return view;
    }

}
