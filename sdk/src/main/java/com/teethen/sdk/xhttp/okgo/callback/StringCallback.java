package com.teethen.sdk.xhttp.okgo.callback;

import com.teethen.sdk.xhttp.okgo.callback.*;
import com.teethen.sdk.xhttp.okgo.convert.StringConvert;

import okhttp3.Response;

/**
 * 描述：返回字符串类型的数据
 */
public abstract class StringCallback extends AbsCallback<String> {

    private StringConvert convert;

    public StringCallback() {
        convert = new StringConvert();
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        String s = convert.convertResponse(response);
        response.close();
        return s;
    }
}
