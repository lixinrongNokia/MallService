package cn.iliker.mall.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public final class AliPayUtil {

    public static Map<String, String> parsAliPayPostInfo(Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<>();
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String[]> entry : requestParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            for (String s : values) {
                builder.append(s).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, builder.toString());
            builder.setLength(0);
        }
        return params;
    }

    public static Map<String, String> parsAliPayPostInfo2(Map<String, String[]> requestParams) {
        Map<String, String> params = new HashMap<>();
        for (String key : requestParams.keySet()) {
            String[] values = requestParams.get(key);
            for (String value : values) {
                params.put(key, value);
            }
        }
        return params;
    }

    public static StringBuilder readBuffer(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        String line;
        StringBuilder inputString = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        reader.close();
        return inputString;
    }
}
