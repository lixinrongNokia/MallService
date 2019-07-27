package cn.iliker.mall.service.impl;

import static cn.iliker.mall.utils.wxpay.ConstantUtil.genAppSign;
import static cn.iliker.mall.utils.wxpay.ConstantUtil.parseXML;
import static cn.iliker.mall.utils.wxpay.XMLUtil.toXML;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.taobao.api.ApiException;

import cn.iliker.mall.alipay.config.AlipayConfig;
import cn.iliker.mall.dao.ITOrderDao;
import cn.iliker.mall.dao.ITransferDAO;
import cn.iliker.mall.dao.IUserAccountDAO;
import cn.iliker.mall.entity.Income;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.Transfer;
import cn.iliker.mall.entity.Wallet;
import cn.iliker.mall.entity.stateattr.OrderState;
import cn.iliker.mall.entity.stateattr.PayType;
import cn.iliker.mall.service.IAliYunAccountSvc;
import cn.iliker.mall.service.ITransferSvc;
import cn.iliker.mall.utils.wxpay.ConstantUtil;
import cn.iliker.mall.utils.wxpay.client.TenpayHttpClient;

public class TransferSvcImpl implements ITransferSvc {
    private ITransferDAO transferDao;
    private IUserAccountDAO userAccountDAO;
    private ITOrderDao orderDao;
    private IAliYunAccountSvc aliYunAccountSvc;

    public void setAliYunAccountSvc(IAliYunAccountSvc aliYunAccountSvc) {
        this.aliYunAccountSvc = aliYunAccountSvc;
    }

    public void setOrderDao(ITOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setUserAccountDAO(IUserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    public void setTransferDao(ITransferDAO transferDao) {
        this.transferDao = transferDao;
    }

    @Override
    public Transfer findById(Integer id) {
        return transferDao.findById(id);
    }

    @Override
    public List findByPhone(Object phone) {
        return transferDao.findByPhone(phone);
    }

    @Override
    public List findAll() {
        return transferDao.findAll();
    }

    /**
     * app提现
     */
    @Override
    public void addTransfer(Transfer instance) throws RuntimeException, ApiException {
        /*if (tOrder != null) {
            TOrder tOrder1 = orderDao.findById(tOrder.getId());
            tOrder1.setOrderstate(OrderState.REFUNDING.getName());//更新订单状态为退款中
            orderDao.save(tOrder1);//执行更新
            String commission = tOrder1.getCommission();
            //三级分佣退款减记
            if (commission != null && commission.length() > 0) {
                JSONObject object = JSONObject.parseObject(commission);
                String level1 = object.getString("level1");
                String[] level1s = level1.split("\\$");
                Income income = new Income("退款减记", Double.parseDouble(level1s[1]), instance.getPhone());
                userAccountDAO.saveIncome(income);
                Wallet wallet = userAccountDAO.getWallet(instance.getPhone());
                BigDecimal oldprice = new BigDecimal(wallet.getRemainingSum());
                BigDecimal addprice = new BigDecimal(Double.parseDouble(level1s[1]));
                wallet.setRemainingSum(oldprice.subtract(addprice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                userAccountDAO.saveWallet(wallet);
                if (object.containsKey("level2")) {
                    String level2 = object.getString("level2");
                    String[] level2s = level2.split("\\$");
                    Income income1 = new Income("退款减记", Double.parseDouble(level2s[1]), level2s[0]);
                    userAccountDAO.saveIncome(income1);
                    String phone=level2s[0];
                    Wallet wallet1 = userAccountDAO.getWallet(level2s[0]);
                    BigDecimal oldprice1 = new BigDecimal(wallet1.getRemainingSum());
                    BigDecimal addprice1 = new BigDecimal(Double.parseDouble(level2s[1]));
                    wallet1.setRemainingSum(oldprice1.subtract(addprice1).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    userAccountDAO.saveWallet(wallet1);
                }
                if (object.containsKey("level3")) {
                    String level3 = object.getString("level3");
                    String[] level3s = level3.split("\\$");
                    Income income2 = new Income("退款减记", Double.parseDouble(level3s[1]), level3s[0]);
                    userAccountDAO.saveIncome(income2);
                    Wallet wallet2 = userAccountDAO.getWallet(level3s[0]);
                    BigDecimal oldprice2 = new BigDecimal(wallet2.getRemainingSum());
                    BigDecimal addprice2 = new BigDecimal(Double.parseDouble(level3s[1]));
                    wallet2.setRemainingSum(oldprice2.subtract(addprice2).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    userAccountDAO.saveWallet(wallet2);
                }
            }
            message = "亲爱的顾客,你的退款申请已经受理，欢迎你再次惠顾!受理订单号:" + tOrder1.getOrderid() + "，订单金额：￥" + amount;
        } else {*/
        double amount = instance.getAmount();
        BigDecimal o_minuend = new BigDecimal(amount);//被减数
        BigDecimal o_reduction = new BigDecimal(instance.getAmount() * 0.006);//减数
        String message = "亲爱的顾客,系统已经受理你的一笔提现,提现金额：" + instance.getAmount() + ",支付宝收取0.6%手续费";
        instance.setAmount(o_minuend.subtract(o_reduction).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        Wallet wallet = userAccountDAO.getWallet(instance.getPhone());
        BigDecimal minuend = new BigDecimal(wallet.getRemainingSum());//被减数
        BigDecimal reduction = new BigDecimal(amount);//减数
        if (minuend.compareTo(reduction) > 0) {
            wallet.setRemainingSum(minuend.subtract(reduction).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            userAccountDAO.saveWallet(wallet);
        }
        transferDao.attachDirty(instance);
        List<String> list = new ArrayList<>();
        list.add(instance.getPhone());
        aliYunAccountSvc.pushMessage(list, message);
    }

    /**
     * 退款至用户app账户
     */
    @Override
    public void transferAppAccount(TOrder tOrder) throws Exception{
        Wallet wallet = userAccountDAO.getWallet(tOrder.getPhone());
        BigDecimal oldprice = new BigDecimal(wallet.getRemainingSum());
        BigDecimal addprice = new BigDecimal(tOrder.getGoodsTotalPrice());
        wallet.setRemainingSum(oldprice.add(addprice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());//更新余额
        userAccountDAO.saveWallet(wallet);
        Income income1 = new Income("订单退款", tOrder.getGoodsTotalPrice(), tOrder.getPhone());
        userAccountDAO.saveIncome(income1);
        tOrder.setOrderstate(OrderState.SHUTDOWN.getName());
        orderDao.save(tOrder);
    }

    private void aliPayRefunds(String biz_content) throws Exception {
        AlipayClient alipayClient = AlipayConfig.client; //获得初始化的AlipayClient
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(biz_content);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        if (!response.isSuccess()) {
            throw new RuntimeException(response.getSubMsg());
        }
    }

    private void wxPayRefunds(Map<String, Object> params) throws Exception {
        TenpayHttpClient httpClient = new TenpayHttpClient();
        httpClient.setReqContent(ConstantUtil.REFUNDURL);
        try {
            httpClient.callHttpPostSSL(ConstantUtil.REFUNDURL, toXML(params));
            params.clear();
            params = parseXML(httpClient.getResContent());
            if (params != null && "SUCCESS".equals(params.get("return_code"))) {
                if ("FAIL".equals(params.get("result_code"))) {
                    throw new RuntimeException((String) params.get("err_code"));
                }
                return;//成功申请退款
            }
            if (params != null)
                throw new RuntimeException((String) params.get("return_msg"));
        } catch (Exception e) {
            throw new RuntimeException("退款系统错误");
        }
    }

    /**
     * 原路退款
     */
    @Override
    public void refunds(TOrder order) throws Exception {
        if (order.getPaymentstate() && (!OrderState.DELIVERED.getName().equals(order.getOrderstate()) || !OrderState.RECEIVED.getName().equals(order.getOrderstate()))) {
            order.setOrderstate(OrderState.SHUTDOWN.getName());
            orderDao.save(order);
            if (PayType.ALIPAY.getName().equals(order.getPaymentTool())) {
                aliPayRefunds("{\"out_trade_no\":\"" + order.getOrderid() + "\",\"trade_no\":\"" + order.getTradeNo() + "\",\"refund_amount\":" + order.getToalprice() + "}");
            } else if (PayType.WXPAY.getName().equals(order.getPaymentTool())) {
                int refund_amount = (int) (order.getToalprice() * 100);
                SecureRandom random = new SecureRandom();
                Map<String, Object> params = new HashMap<>();
                params.put("appid", ConstantUtil.APP_ID);
                params.put("mch_id", ConstantUtil.PARTNER);
                params.put("nonce_str", new BigInteger(32, random).toString(8));//生成随机字符串
                params.put("transaction_id", order.getTradeNo());
                params.put("out_refund_no", order.getOrderid());
                params.put("total_fee", refund_amount);
                params.put("refund_fee", refund_amount);
                String sign = genAppSign(params);
                params.put("sign", sign);
                wxPayRefunds(params);
            } else {
                throw new RuntimeException("只有微信付款或支付宝付款才能操作");
            }
            return;
        }
        throw new RuntimeException("订单未支付或暂时不可退款");
    }
}
