package com.teethen.sdk.xhttp.okgo.request;

import com.teethen.sdk.xhttp.okgo.model.HttpMethod;
import com.teethen.sdk.xhttp.okgo.request.base.BodyRequest;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 描述：Options请求
 */
public class OptionsRequest<T> extends BodyRequest<T, OptionsRequest<T>> {

    public OptionsRequest(String url) {
        super(url);
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.OPTIONS;
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = generateRequestBuilder(requestBody);
        return requestBuilder.method("OPTIONS", requestBody).url(url).tag(tag).build();
    }
}
