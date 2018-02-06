package com.teethen.sdk.xhttp.nohttp.rest;

import com.teethen.sdk.xhttp.nohttp.Headers;
import com.teethen.sdk.xhttp.nohttp.RequestMethod;

import org.json.JSONArray;

/**
 * <p>JsonArray is returned by the server data, using the handle object.</p>
 *
 * @author xingq.
 */
public class JsonArrayRequest extends Request<JSONArray> {

    public JsonArrayRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public JsonArrayRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        setAccept(Headers.HEAD_VALUE_CONTENT_TYPE_JSON);
    }

    @Override
    public JSONArray parseResponse(Headers responseHeaders, byte[] responseBody) throws Exception {
        String jsonStr = StringRequest.parseResponseString(responseHeaders, responseBody);
        return new JSONArray(jsonStr);
    }

}
