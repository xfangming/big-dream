package org.dream.toolkit.url;


import org.dream.toolkit.utils.MapUtils;

import java.util.Map;

/**
 * @author tobiasy
 */
public class HttpUtil {
    public static String doGet(String url, Map<String, Object> params) {
        if (params.size() > 0) {
            return HttpClient.doGet(url + "?" + getParams(params));
        } else {
            return HttpClient.doGet(url);
        }
    }

    public static String doPost(String url, Map<String, Object> params) {
        return HttpClient.doPost(url, getParams(params));
    }

    private static String getParams(Map<String, Object> params) {
        Object[] keys = MapUtils.getKeys(params);
        StringBuffer sf = new StringBuffer();
        for (int i = 0; i < keys.length; i++) {
            Object key = keys[i];
            if (i != 0) {
                sf.append("&");
            }
            sf.append(key + "=" + params.get(key));
        }
        return sf.toString();
    }


}