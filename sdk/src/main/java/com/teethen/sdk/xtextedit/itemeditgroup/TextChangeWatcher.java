package com.teethen.sdk.xtextedit.itemeditgroup;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by xingq on 2017/12/3.
 * EditText输入内容改变的观察器
 */

public abstract class TextChangeWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
