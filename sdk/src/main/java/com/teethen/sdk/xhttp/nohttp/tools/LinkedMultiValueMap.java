package com.teethen.sdk.xhttp.nohttp.tools;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author xingq.
 */
public class LinkedMultiValueMap<K, V> extends BasicMultiValueMap<K, V> {

    public LinkedMultiValueMap() {
        super(new LinkedHashMap<K, List<V>>());
    }
}
