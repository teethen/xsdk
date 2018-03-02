package com.teethen.xsdk.ninegrid.complex.news;

import com.teethen.sdk.xhttp.okgo.callback.AbsCallback;
import com.teethen.sdk.xhttp.okgo.model.HttpHeaders;
import com.teethen.sdk.xhttp.okgo.request.base.Request;

import java.io.IOException;

import okhttp3.Response;

public abstract class NewsCallBack extends AbsCallback<String> {

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        super.onStart(request);
        HttpHeaders headers = new HttpHeaders();
        headers.put("apikey", Urls.APIKEY);
        request.headers(headers);
    }

    @Override
    public String convertResponse(Response response) throws Throwable {
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
