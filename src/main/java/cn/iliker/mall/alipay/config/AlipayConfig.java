package cn.iliker.mall.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AlipayConfig {
    public static final String APPID = "";
    //服务器异步通知页面路径
    public static final String NOTIFY_URL = "";
    //支付异步通知路径
    public static final String ALIPAY_NOTIFY_URL = "";

    //手机网页支付成功后跳转页面路径
    public static final String RETURN_URL = "";
    //付款账号 必填
    public static final String EMAIL = "";

    //付款账户名，必填，个人支付宝账号是真实姓名公司支付宝账号是公司名称
    public static final String ACCOUNT_NAME = "";

    // ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    // 合作身份者ID，以2088开头由16位纯数字组成的字符串
    public final static String PARTNER = "";

    // 安全校验码
    public final static String KEY = "";
    // 商户私钥RSA2
    public final static String ALI_PRIVATE_KEY2 = "";
    // 支付宝公钥
    public final static String ALI_PUBLIC_KEY = "";
    // 调试用，创建TXT日志文件夹路径
    public final static String log_path = "C:\\alipayLog\\";

    // 字符编码格式 目前支持 gbk 或 utf-8
    public final static String INPUT_CHARSET = "utf-8";


    //实例化客户端
    public static AlipayClient client = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APPID, ALI_PRIVATE_KEY2, "json", INPUT_CHARSET, ALI_PUBLIC_KEY, "RSA2");
}
