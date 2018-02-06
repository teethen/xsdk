package com.teethen.sdk.xhttp.okgo.request;

import com.teethen.sdk.xhttp.okgo.model.HttpMethod;
import com.teethen.sdk.xhttp.okgo.request.base.BodyRequest;

import okhttp3.Request;
import okhttp3.RequestBody;

public class DeleteRequest<T> extends BodyRequest<T, DeleteRequest<T>> {

    public DeleteRequest(String url) {
        super(url);
    }

    @Override
    public HttpMethod getMethod() {
        return HttpMethod.DELETE;
    }

    @Override
    public Request generateRequest(RequestBody requestBody) {
        Request.Builder requestBuilder = generateRequestBuilder(requestBody);
        return requestBuilder.delete(requestBody).url(url).tag(tag).build();
    }
}
