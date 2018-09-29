package melon.im.network;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApiParamUtil {

    private static String checkUrlSuffix(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        int index = url.lastIndexOf("?");
        if (index == -1) {
            //整个链接没有?
            if (url.endsWith("?")) {
                return url;
            } else {
                return url + "?";
            }
        } else {

            if (url.endsWith("?")) {
                return url;
            } else if (url.endsWith("&")) {
                return url;
            } else {
                return url + "&";
            }
        }

    }

    public static String wrappeUrlParam(String url, Map<String, String> map) {

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if (map == null || map.isEmpty()) {
            return url;
        }

        map = ApiParamUtil.wrappeBaseParam(map);

        url = checkUrlSuffix(url);

        StringBuilder sb = new StringBuilder(url);
        sb.append(encodeParameters(map, "UTF-8"));
        String retStr = sb.toString();
        retStr = retStr.substring(0, retStr.length() - 1);
//        Log.d(Log.TAG_REQUEST, "ApiParamUtil#wrappeUrlParam retStr : " + retStr);
        return retStr;
    }
//
//
    public static Map<String, String> wrappeBaseParam(Map<String, String> map) {

//        Map<String, String> retMap = getBaseParam();
        Map<String, String> retMap = new HashMap<>();
        if (map == null || map.isEmpty()) {
            return retMap;
        }


        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String value = map.get(key);
            if (!TextUtils.isEmpty(key)) {
                if (TextUtils.isEmpty(value)) {
                    value = "";
                }
                retMap.put(key, value);
            }
        }


        return retMap;
    }

    public static String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                if (entry.getValue() != null) {
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                } else {
                    encodedParams.append("");
                }
                encodedParams.append('&');

            }
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

}
