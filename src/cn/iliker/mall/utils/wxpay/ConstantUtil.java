package cn.iliker.mall.utils.wxpay;

import cn.iliker.mall.config.WXPlatformConf;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class ConstantUtil {
    /**
     * 商家可以考虑读取配置文件
     */
    public static final String APP_ID = "";//微信开发平台应用id
    //public static final String APP_SECRET = "";//应用对应的凭证
    public static final String APP_SECRET = "";//应用对应的凭证
    public static final String PARTNER = "";//财付通商户号
    public static final String PARTNER_KEY = "";//商户号对应的密钥
    public static final String GATEURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//微信支付统一下单请求路径
    public static final String REFUNDURL = "https://api.mch.weixin.qq.com/secapi/pay/refund";//微信支付申请退款请求路径
    public static final String NOTIFY_URL = "";//异步通知路径

    /**
     * 签名
     *
     * @param params 待签名参数
     * @return 签名字符串
     */
    public static String genAppSign(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        List<String> sortedKeys = new ArrayList<>(params.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        if ("wx82a6291f4ce0547e".equals(params.get("appid"))) {
            sb.append("key=").append(ConstantUtil.PARTNER_KEY);
        } else {
            sb.append("key=").append(WXPlatformConf.PARTNER_KEY);
        }
        return MD5.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
    }

    /**
     * xml转map集合
     *
     * @param resContent xml字符串
     * @return map
     */
    public static Map<String, Object> parseXML(String resContent) {
        try {
            Document document = DocumentHelper.parseText(resContent);
            Element root = document.getRootElement();
            Map<String, Object> params = new HashMap<>();
            for (Iterator it = root.elementIterator(); it.hasNext(); ) {
                Element element = (Element) it.next();
                params.put(element.getQualifiedName(), element.getText());
            }
            return params;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取客户端IP
    public static String getRemortIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip.contains(",")) {
            return ip.split(",")[0];
        }
        return ip;
    }

}