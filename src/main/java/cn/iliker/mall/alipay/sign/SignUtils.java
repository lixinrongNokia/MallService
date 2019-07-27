package cn.iliker.mall.alipay.sign;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.iliker.mall.alipay.config.AlipayConfig;

public class SignUtils {

    /**
     * 构造支付订单参数列表
     */
    public static Map<String, String> buildOrderParamMap(String app_id, String biz_content) {
        Map<String, String> keyValues = new LinkedHashMap<>();
        keyValues.put("app_id", app_id);
        keyValues.put("biz_content", biz_content);
        keyValues.put("charset", "utf-8");
        keyValues.put("method", "alipay.trade.app.pay");
        keyValues.put("notify_url", AlipayConfig.ALIPAY_NOTIFY_URL);
        keyValues.put("sign_type", "RSA2");
        keyValues.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        keyValues.put("version", "1.0");
        return keyValues;
    }

    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     */
    public static String buildOrderParam(Map<String, String> map, boolean encode) {
        List<String> keys = new ArrayList<>(map.keySet());
        // key排序
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key:keys) {
            String value = map.get(key);
            sb.append(key).append("=");
            try {
                sb.append(encode ? URLEncoder.encode(value, "UTF-8") : value).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public static String decodeOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        // key排序
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (String key:keys) {
            String value = map.get(key);
            sb.append(key).append("=");
            try {
                if (!key.equals("sign_type")&&!key.equals("sign"))
                sb.append(URLDecoder.decode(value, "UTF-8")).append("&");
                else sb.append(value).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * 对支付参数信息进行排序
     */
    /*public static String getSignStr(Map<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (String key : keys) {
            String value = map.get(key);
            authInfo.append(key).append("=");
            try {
                authInfo.append(URLDecoder.decode(value, "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return authInfo.deleteCharAt(authInfo.length() - 1).toString();
    }*/
    /*public static String sign(String content, String privateKey, boolean rsa2) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(getAlgorithms(rsa2));

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }*/


}
