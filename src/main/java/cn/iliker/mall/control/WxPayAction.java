package cn.iliker.mall.control;

import static cn.iliker.mall.alipay.util.AlipayCore.logResult;
import static cn.iliker.mall.utils.wxpay.ConstantUtil.genAppSign;
import static cn.iliker.mall.utils.wxpay.ConstantUtil.getRemortIP;
import static cn.iliker.mall.utils.wxpay.ConstantUtil.parseXML;
import static cn.iliker.mall.utils.wxpay.XMLUtil.toXML;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.config.WXPlatformConf;
import cn.iliker.mall.entity.stateattr.PayType;
import cn.iliker.mall.service.IAliYunAccountSvc;
import cn.iliker.mall.service.ITOrderSvc;
import cn.iliker.mall.service.IUserAccountSvc;
import cn.iliker.mall.utils.wxpay.ConstantUtil;
import cn.iliker.mall.utils.wxpay.ResponseHandler;
import cn.iliker.mall.utils.wxpay.client.TenpayHttpClient;

//微信支付相关
public class WxPayAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    private int requestCode;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String payProperty;
    private IUserAccountSvc userAccountSvc;
    private IAliYunAccountSvc aliYunAccountSvc;
    private JSONObject msg = new JSONObject();
    private ITOrderSvc itOrderSvc;

    public void setItOrderSvc(ITOrderSvc itOrderSvc) {
        this.itOrderSvc = itOrderSvc;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public void setAliYunAccountSvc(IAliYunAccountSvc aliYunAccountSvc) {
        this.aliYunAccountSvc = aliYunAccountSvc;
    }

    public void setPayProperty(String payProperty) {
        this.payProperty = payProperty;
    }

    public void setUserAccountSvc(IUserAccountSvc userAccountSvc) {
        this.userAccountSvc = userAccountSvc;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    //移动app统一下单
    public String wxPrepay() throws IOException {
        msg.clear();
        SecureRandom random = new SecureRandom();
        Map<String, Object> params = parseXML(payProperty);
        JSONObject checkOrder = null;
        if (params != null) {
            try {
                checkOrder = userAccountSvc.queryToalprice((String) params.get("out_trade_no"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (checkOrder == null || checkOrder.getBoolean("sys_flag")) {
            msg.put("requestCode", requestCode);
            msg.put("result_code", "FAIL");
            msg.put("data", "订单不存在或订单已支付");
            return ERROR;
        }
        if ("JSAPI".equals(params.get("trade_type"))) {
            params.put("mch_id", WXPlatformConf.PARTNER);//商户号
        } else {
            params.put("mch_id", ConstantUtil.PARTNER);//商户号
        }
        params.put("nonce_str", new BigInteger(32, random).toString(8));//生成随机字符串
        params.put("spbill_create_ip", getRemortIP(request));//终端IP
        params.put("notify_url", ConstantUtil.NOTIFY_URL);//异步通知地址
        String sign = genAppSign(params);
        params.put("sign", sign);
        TenpayHttpClient httpClient = new TenpayHttpClient();
        if (httpClient.callHttpPost(ConstantUtil.GATEURL, toXML(params))) {
            params.clear();
            params = parseXML(httpClient.getResContent());
            if (params != null && "SUCCESS".equals(params.get("return_code")) && "SUCCESS".equals(params.get("result_code"))) {
                String appid = (String) params.get("appid");
                String mch_id = (String) params.get("mch_id");
                String prepay_id = (String) params.get("prepay_id");
                String trade_type = (String) params.get("trade_type");
                String mweb_url = params.containsKey("mweb_url") ? (String) params.get("mweb_url") : "";
                params.clear();
                if ("APP".equals(trade_type)) {
                    params.put("appid", appid);
                    params.put("partnerid", mch_id);
                    params.put("prepayid", prepay_id);
                    params.put("package", "Sign=WXPay");
                    params.put("noncestr", new BigInteger(32, random).toString(8));
                    params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                    params.put("sign", genAppSign(params));
                    msg.put("requestCode", requestCode);
                    msg.put("result_code", "SUCCESS");
                    msg.put("data", JSONObject.parseObject(JSON.toJSONString(params)));
                } else if ("JSAPI".equals(trade_type)) {
                    params.put("appId", appid);
                    params.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
                    params.put("nonceStr", new BigInteger(32, random).toString(8));
                    params.put("package", "prepay_id=" + prepay_id);
                    params.put("signType", "MD5");
                    params.put("paySign", genAppSign(params));
                    msg.put("requestCode", requestCode);
                    msg.put("result_code", "SUCCESS");
                    msg.put("data", JSONObject.parseObject(JSON.toJSONString(params)));
                } else {
                    params.put("mweb_url", mweb_url);
                    msg.put("requestCode", requestCode);
                    msg.put("result_code", "SUCCESS");
                    msg.put("data", JSONObject.parseObject(JSON.toJSONString(params)));
                }
                return SUCCESS;
            }
            if (params != null)
                logResult("返回状态码:" + params.get("return_code") + ";返回信息:" + params.get("return_msg"));
        }

        msg.put("requestCode", requestCode);
        msg.put("result_code", "FAIL");
        msg.put("data", JSONObject.parseObject(JSON.toJSONString(params)));
        return ERROR;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }

    public String wxPayNotify() throws Exception {
        PrintWriter writer = response.getWriter();
        //创建支付应答对象
        ResponseHandler resHandler = new ResponseHandler(request, response);
        if ("SUCCESS".equals(resHandler.getParameter("return_code"))) {
            String nmch_id;
            if ("JSAPI".equals(resHandler.getParameter("trade_type"))) {
                nmch_id = WXPlatformConf.PARTNER;
                resHandler.setKey(WXPlatformConf.PARTNER_KEY);
            } else {
                nmch_id = ConstantUtil.PARTNER;
                resHandler.setKey(ConstantUtil.PARTNER_KEY);
            }
            //判断签名
            if (resHandler.isTenpaySign()) {
                //取结果参数做业务处理
                String result_code = resHandler.getParameter("result_code");//返回状态码
                new Thread(() -> {
                    if ("SUCCESS".equals(result_code)) {
                        // String appid = resHandler.getParameter("appid");//应用号
                        String mch_id = resHandler.getParameter("mch_id");//商户号
                        String out_trade_no = resHandler.getParameter("out_trade_no");//商户订单号
                        String transaction_id = resHandler.getParameter("transaction_id");//微信订单号
                        int total_fee = Integer.parseInt(resHandler.getParameter("total_fee"));//总金额(分)
                        if (nmch_id.equals(mch_id)) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = userAccountSvc.queryToalprice(out_trade_no);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (jsonObject != null && !jsonObject.getBoolean("sys_flag")) {
                                int toalprice = (int) (jsonObject.getDouble("toalprice") * 100);
                                String phone = jsonObject.getString("phone");
                                if (toalprice == total_fee) {
                                    try {
                                        userAccountSvc.updateOrderStatus(transaction_id, out_trade_no, jsonObject.getDouble("toalprice"), phone, PayType.WXPAY.getName());
                                        List<String> list = new ArrayList<>();
                                        list.add(phone);
                                        aliYunAccountSvc.pushMessage(list, "您订单编号:" + out_trade_no + "付款成功,请留意后续物流信息!");
                                    } catch (Exception e) {
                                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                        e.printStackTrace(new PrintStream(baos));
                                        jsonObject.clear();
                                        jsonObject.put("errorMsg", "更新订单系统出错");
                                        jsonObject.put("errorMsg2", baos.toString());
                                        jsonObject.put("out_trade_no", out_trade_no);
                                        jsonObject.put("trade_no", transaction_id);
                                        jsonObject.put("total_fee", total_fee);
                                        jsonObject.put("phone", phone);
                                        logResult(jsonObject.toJSONString());
                                    }
                                }
                            }
                        }
                    }
                }).start();
                writer.println("<xml><return_code><![CDATA[SUCCESS]]></return_code></xml>");
                writer.flush();
                writer.close();
                return NONE;

            }
            writer.println("<xml><return_code><![CDATA[FAIL]]></return_code></xml>");
            writer.flush();
            writer.close();
        }
        return NONE;
    }

    /*public String tastPay() {
        String out_trade_no = "c20171211175641";
        String transaction_id = "wx2017121112532e3";
        int total_fee = 63800;
        JSONObject jsonObject = null;
        try {
            jsonObject = userAccountSvc.queryToalprice(out_trade_no);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jsonObject != null && !jsonObject.getBoolean("sys_flag")) {
            int toalprice = (int) (jsonObject.getDouble("toalprice") * 100);
            String phone = jsonObject.getString("phone");
            if (toalprice == total_fee) {
                try {
                    userAccountSvc.updateOrderStatus(transaction_id, out_trade_no, jsonObject.getDouble("toalprice"), phone,PayType.WXPAY.getName());
                    List<String> list = new ArrayList<>();
                    list.add(phone);
                    aliYunAccountSvc.pushMessage(list, "您订单编号为"+out_trade_no+"付款成功,请留意后续物流信息!");
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonObject.clear();
                    jsonObject.put("errorMsg", "更新订单系统出错");
                    jsonObject.put("out_trade_no", out_trade_no);
                    jsonObject.put("trade_no", transaction_id);
                    jsonObject.put("total_fee", total_fee);
                    jsonObject.put("phone", phone);
                    logResult(jsonObject.toJSONString());
                }
            }
        }
        return NONE;
    }*/
}
