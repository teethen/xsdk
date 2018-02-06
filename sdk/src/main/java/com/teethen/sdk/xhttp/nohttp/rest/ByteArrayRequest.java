package com.teethen.sdk.xhttp.nohttp.rest;

import com.teethen.sdk.xhttp.nohttp.Headers;
import com.teethen.sdk.xhttp.nohttp.RequestMethod;

/**
 * Created by xingq on 2017/8/20.
 */
public class ByteArrayRequest extends Request<byte[]> {

    public ByteArrayRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public ByteArrayRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
    }

    @Override
    public byte[] parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        return responseBody == null ? new byte[0] : responseBody;
    }
}