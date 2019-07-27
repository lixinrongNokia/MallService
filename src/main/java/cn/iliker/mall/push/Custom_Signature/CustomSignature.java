package cn.iliker.mall.push.Custom_Signature;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.aliyuncs.utils.ParameterHelper;

import cn.iliker.mall.push.PushConfig;
import sun.misc.BASE64Encoder;

public class CustomSignature extends PushConfig {
    /**
     * 如果用户有自己的特殊应用场景,不适合使用SDK可以参考下面的demo自己组装HTTP请求 以ListTags接口为例,其它接口按要求构造相应的访问参数即可
     */
    public static String CustomSignature(String accounts, String title, String content, String ext, String type, String openType, String openUri) throws Exception {
        //构造访问参数
        String backMes = "";
        SecureRandom random = new SecureRandom();
        Map<String, Object> params = new HashMap<>();
        params.put("Format", "JSON");
        params.put("RegionId", REGIONID);
        params.put("Version", "2015-08-27");
        params.put("SignatureMethod", "HMAC-SHA1");
        params.put("Timestamp", ParameterHelper.getISO8601Time(new Date()));//生成ISO格式的时间戳
        params.put("SignatureVersion", "1.0");
        params.put("SignatureNonce", new BigInteger(32, random).toString(8));//生成随机字符串
        params.put("Action", "Push");
        params.put("AccessKeyId", ACCESSKEYID);
        params.put("AppKey", APPKEY);
        params.put("Target", "account");
        params.put("TargetValue", accounts);
        params.put("Type", type);//0:消息;1:通知
        params.put("DeviceType", "1");//0:iOS设备;1:Andriod设备;3：全部类型设备
        params.put("Title", title);
        params.put("Body", content);
        params.put("AndroidExtParameters", ext);
        params.put("AndroidOpenType", openType);
        params.put("AndroidActivity", openUri);
        params.put("StoreOffline", true);//保存离线消息
        params.put("ExpireTime", ParameterHelper.getISO8601Time(new Date(System.currentTimeMillis() + 12 * 3600 * 1000)));//离线消息保存时间

        //定义host并生成url
        String request = request(HOST, params);
        //发起请求并打印返回结果
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(request);
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler(3, false));
        try {
            int statusCode = client.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                backMes = method.getStatusLine().toString();
            }
            byte[] responseBody = method.getResponseBody();
            backMes = new String(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return backMes;
    }

    //构造移动推送HTTP请求URL
    private static String request(String host, Map<String, Object> params) throws Exception {
        String params_string = "";
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        for (Object key : sortedKeys) {
            params_string += URLEncoder.encode(String.valueOf(key), "UTF-8") + "=" + URLEncoder.encode(String.valueOf(params.get(key)), "UTF-8") + "&";
        }
        return host + "/?" + params_string + "Signature=" + sign("GET", params, ACCESSKEYSECRET + "&");
    }

    //构造签名
    private static String sign(String method, Map<String, Object> params, String secret) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        String query_string = "";
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        for (Object key : sortedKeys) {
            query_string += '&' + popEncode(String.valueOf(key)) + '=' + popEncode(String.valueOf(params.get(key)));
        }
        String sign_to_string = method + "&%2F&" + popEncode(query_string.substring(1, query_string.length()));
        Mac e = Mac.getInstance("HmacSHA1");
        e.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA1"));
        byte[] signData = e.doFinal(sign_to_string.getBytes("UTF-8"));
        return (new BASE64Encoder()).encodeBuffer(signData);
    }

    //pop网管urlEncode
    private static String popEncode(String value) throws UnsupportedEncodingException {
        return value != null ? URLEncoder.encode(value, "UTF-8").replace("+", "%20").replace("*", "%2A").replace("%7E", "~") : null;
    }
}
