package com.teethen.xsdk.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teethen.xsdk.R;

/**
 * Created by xingq on 2018/2/23.
 */

public class FragmentText extends Fragment {

    public static final String TAG_MSG = "content";
    private View view;
    private TextView mTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle bundle) {
        view = inflater.inflate(R.layout.fragment_text, container, false);
        mTextView = view.findViewById(R.id.tf_textview);
        mTextView.setGravity(Gravity.CENTER);
        String content = getArguments().getString(TAG_MSG, "");
        mTextView.setText(content);

        return view;
    }
}
