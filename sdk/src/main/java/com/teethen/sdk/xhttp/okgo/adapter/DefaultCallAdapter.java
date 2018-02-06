package com.teethen.sdk.xhttp.okgo.adapter;

import com.teethen.sdk.xhttp.okgo.adapter.AdapterParam;
import com.teethen.sdk.xhttp.okgo.adapter.Call;

/**
 * 描述：默认的工厂处理,不对返回值做任何操作
 */
public class DefaultCallAdapter<T> implements CallAdapter<T, Call<T>> {

    @Override
    public Call<T> adapt(Call<T> call, AdapterParam param) {
        return call;
    }
}
