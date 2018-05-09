package cn.iliker.mall.control;

import cn.iliker.mall.alipay.config.AlipayConfig;
import cn.iliker.mall.entity.stateattr.PayType;
import cn.iliker.mall.service.IAliYunAccountSvc;
import cn.iliker.mall.service.ITOrderSvc;
import cn.iliker.mall.service.IUserAccountSvc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.iliker.mall.alipay.sign.SignUtils.buildOrderParam;
import static cn.iliker.mall.alipay.sign.SignUtils.buildOrderParamMap;
import static cn.iliker.mall.alipay.util.AlipayCore.logResult;
import static cn.iliker.mall.utils.AliPayUtil.parsAliPayPostInfo2;

//支付宝相关
public class ASYNNotify extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String total_amount;//总金额
    private String requestCode;
    private String biz_content;
    private String out_trade_no;
    private IUserAccountSvc userAccountSvc;
    private IAliYunAccountSvc aliYunAccountSvc;
    private ITOrderSvc itOrderSvc;

    public void setItOrderSvc(ITOrderSvc itOrderSvc) {
        this.itOrderSvc = itOrderSvc;
    }

    public void setAliYunAccountSvc(IAliYunAccountSvc aliYunAccountSvc) {
        this.aliYunAccountSvc = aliYunAccountSvc;
    }

    public void setUserAccountSvc(IUserAccountSvc userAccountSvc) {
        this.userAccountSvc = userAccountSvc;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public void setBiz_content(String biz_content) {
        this.biz_content = biz_content;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }


    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.request = httpServletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.response = httpServletResponse;
    }

    public String mobilePayNotify() throws IOException {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> params = parsAliPayPostInfo2(request.getParameterMap());//获取支付宝POST过来反馈信息
        String out_trade_no = params.get("out_trade_no");//商户订单号
        String trade_no = params.get("trade_no");//支付宝交易号
        String seller_id = params.get("seller_id");//卖家用户号2088开头的纯16位数字
        String charset = params.get("charset");
        String sign_type = params.get("sign_type");//签名类型
        String total_fee = params.get("total_amount");//该笔订单的总金额
        String trade_status = params.get("trade_status");//交易状态
        //商品名称
//        String subject = params.get("subject");
        PrintWriter out = response.getWriter();
        boolean isCheck = false;
        try {
            isCheck = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALI_PUBLIC_KEY, charset, sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (isCheck) {//验证成功
            new Thread(() -> {
                if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {//支付成功TRADE_SUCCESS支付完成不能退款TRADE_FINISHED
                    if (AlipayConfig.PARTNER.equals(seller_id)) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = userAccountSvc.queryToalprice(out_trade_no);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (jsonObject != null && !jsonObject.getBoolean("sys_flag")) {
                            String phone = jsonObject.getString("phone");
                            BigDecimal a = new BigDecimal(jsonObject.getDouble("toalprice"));
                            BigDecimal b = new BigDecimal(Double.parseDouble(total_fee));
                            if (b.compareTo(a) == 0) {
                                try {
                                    userAccountSvc.updateOrderStatus(trade_no, out_trade_no, Double.parseDouble(total_fee), phone, PayType.ALIPAY.getName());
                                    List<String> list = new ArrayList<>();
                                    list.add(phone);
                                    aliYunAccountSvc.pushMessage(list, "您的订单付款成功,祝您购物愉快!");
                                } catch (Exception e) {
                                    jsonObject.clear();
                                    jsonObject.put("errorMsg", "更新订单系统出错");
                                    jsonObject.put("out_trade_no", out_trade_no);
                                    jsonObject.put("trade_no", trade_no);
                                    jsonObject.put("total_fee", total_fee);
                                    jsonObject.put("phone", phone);
                                    logResult(jsonObject.toJSONString());
                                }
                            }
                        }
                    }
                }
            }).start();
            out.println("success");    //请不要修改或删除
            out.flush();
            out.close();
            return NONE;
        } else {//验证失败
            out.println("fail");
            out.flush();
            out.close();
            return NONE;
        }
    }

    /*支付宝app支付请求参数构建*/
    public String createPaySign() throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        // 订单
        try {
            JSONObject bizContent = JSON.parseObject(biz_content);
            JSONObject checkOrder = null;
            try {
                checkOrder = userAccountSvc.queryToalprice(bizContent.getString("out_trade_no"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (checkOrder == null || checkOrder.getBoolean("sys_flag")) {
                String data = "{\"requestCode\":" + requestCode + ",\"result_code\":\"FAIL\",\"data\":\"订单不存在或订单已支付\"}";
                writer.print(data);
                writer.flush();
                writer.close();
                return NONE;
            }
            Map<String, String> params = buildOrderParamMap(AlipayConfig.APPID, biz_content);
            String orderParam = buildOrderParam(params, false);
            String sign = AlipaySignature.rsa256Sign(orderParam, AlipayConfig.ALI_PRIVATE_KEY2, AlipayConfig.INPUT_CHARSET);
            params.put("sign", sign);
            String data = "{\"requestCode\":" + requestCode + ",\"result_code\":\"SUCCESS\",\"data\":\"" + buildOrderParam(params, true) + "\"}";
            writer.print(data);
            writer.flush();
            writer.close();
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        String data = "{\"requestCode\":" + requestCode + ",\"result_code\":\"FAIL\",\"data\":\"\"}";
        writer.print(data);
        writer.flush();
        writer.close();
        return NONE;
    }

    public String aliPayForJS() throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        PrintWriter writer = response.getWriter();
        AlipayClient alipayClient = AlipayConfig.client; //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
        alipayRequest.setReturnUrl(AlipayConfig.RETURN_URL);
        alipayRequest.setNotifyUrl(AlipayConfig.ALIPAY_NOTIFY_URL);//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\", \"total_amount\":" + total_amount + ",\"subject\":\"爱内秀-网站支付\", \"seller_id\":\"" + AlipayConfig.PARTNER + "\", \"product_code\":\"QUICK_WAP_PAY\"}");//填充业务参数
        try {
            String form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
            writer.println(form);//直接将完整的表单html输出到页面
            writer.flush();
            writer.close();
            return NONE;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        writer.println("调用支付宝支付失败");
        writer.flush();
        writer.close();
        return NONE;
    }
}
