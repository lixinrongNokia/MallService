package cn.iliker.mall.service;

import com.alibaba.fastjson.JSONObject;

import cn.iliker.mall.entity.GiveAccount;
import cn.iliker.mall.entity.IntegralAccount;
import cn.iliker.mall.entity.Rechargeableorder;
import cn.iliker.mall.entity.Wallet;

public interface IUserAccountSvc {

    void saveRechargeableorder(Rechargeableorder rechargeableorder) throws RuntimeException;

    GiveAccount getGiveAccount(String phone);


    IntegralAccount getIntegral(String phone);


    Wallet getWallet(String phone);


    JSONObject queryToalprice(String orderId) throws Exception;

    /**
     *
     * @param trade_no 支付交易号
     * @param orderId 商户订单号
     * @param total_fee 总金额
     * @param phone 用户手机号
     * @param paymentTool 支付工具
     * @throws Exception 异常
     */
    void updateOrderStatus(String trade_no, String orderId, double total_fee, String phone,String paymentTool) throws Exception;

    void executeInAppPay(String phone, String orderId, double amount) throws Exception;

}
