package cn.iliker.mall.service.impl;

import static cn.iliker.mall.alipay.util.UtilDate.getOrderNum;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import cn.iliker.mall.dao.ITOrderDao;
import cn.iliker.mall.dao.IUserAccountDAO;
import cn.iliker.mall.entity.GiveAccount;
import cn.iliker.mall.entity.IntegralAccount;
import cn.iliker.mall.entity.PrepaidCardOrder;
import cn.iliker.mall.entity.Rechargeableorder;
import cn.iliker.mall.entity.Spending;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.Wallet;
import cn.iliker.mall.entity.stateattr.OrderState;
import cn.iliker.mall.service.IUserAccountSvc;
import cn.iliker.mall.utils.MatchOrderType;

public class UserAccountSvcImpl implements IUserAccountSvc {
    private static final Logger log = LoggerFactory
            .getLogger(UserAccountSvcImpl.class);
    private IUserAccountDAO userAccountDAO;

    private ITOrderDao orderDao;

    public void setOrderDao(ITOrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setUserAccountDAO(IUserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public void saveRechargeableorder(Rechargeableorder rechargeableorder) throws RuntimeException {
        userAccountDAO.saveRechargeableorder(rechargeableorder);
    }

    @Override
    public GiveAccount getGiveAccount(String phone) {
        return userAccountDAO.getGiveAccount(phone);
    }


    @Override
    public IntegralAccount getIntegral(String phone) {
        return userAccountDAO.getIntegral(phone);
    }


    @Override
    public Wallet getWallet(String phone) {
        return userAccountDAO.getWallet(phone);
    }


    @Override
    public JSONObject queryToalprice(String orderId) throws Exception {
        char c = cn.iliker.mall.utils.MatchOrderType.matchOrderType(orderId);
        JSONObject jsonObject = new JSONObject();
        switch (c) {
            case 'c'://购买实物商品
                List list = orderDao.findByOrderid(orderId);
                TOrder order = (TOrder) list.get(0);
                jsonObject.put("phone", order.getPhone());
                jsonObject.put("toalprice", order.getToalprice());
                jsonObject.put("sys_flag", order.getSys_flag());
                log.debug("购买商品");
                break;
            case 'd'://充值
                Rechargeableorder rechargeableorder = userAccountDAO.getRechargeableorder(orderId);
                jsonObject.put("phone", rechargeableorder.getTaggetPhone());
                jsonObject.put("toalprice", rechargeableorder.getAmount());
                jsonObject.put("sys_flag", rechargeableorder.getSys_flag());
                break;
            case 'x'://购买优惠券
                PrepaidCardOrder prepaidCardOrder = userAccountDAO.getPrepaidCardOrder(orderId);
                jsonObject.put("phone", prepaidCardOrder.getPhone());
                jsonObject.put("toalprice", prepaidCardOrder.getDenomination());
                jsonObject.put("sys_flag", prepaidCardOrder.getSys_flag());
                break;
        }
        return jsonObject;
    }

    @Override
    public void updateOrderStatus(String trade_no, String orderId, double total_fee, String phone,String paymentTool) throws Exception {
        char type = MatchOrderType.matchOrderType(orderId);
        switch (type) {
            case 'c':
                /*购物更新订单*/
                TOrder tOrder = (TOrder) orderDao.findByOrderid(orderId).get(0);
                tOrder.setOrderstate(OrderState.WAITCONFIRM.getName());//订单流转状态
                tOrder.setPaymentstate(true);//更新支付状态为已付款
                tOrder.setTradeNo(trade_no);//设置交易号
                tOrder.setPaymentTool(paymentTool);
                tOrder.setSys_flag(true);//已处理标志
                orderDao.save(tOrder);//执行更新
                break;
            case 'd':
                /*更新自充值*/
                Rechargeableorder rechargeableorder = userAccountDAO.getRechargeableorder(orderId);
                rechargeableorder.setTrade_no(trade_no);//设置交易号
                rechargeableorder.setRechargeablestate(true);//是否付款
                rechargeableorder.setSys_flag(true);//处理标志
                userAccountDAO.saveRechargeableorder(rechargeableorder);//执行更新
                Wallet wallet = userAccountDAO.getWallet(phone);
                BigDecimal oldprice = new BigDecimal(wallet.getRemainingSum());
                BigDecimal addprice = new BigDecimal(total_fee);
                wallet.setRemainingSum(oldprice.add(addprice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());//更新余额
                userAccountDAO.saveWallet(wallet);
                break;
            case 'x':
                /*购买卡券*/
                PrepaidCardOrder prepaidCardOrder = userAccountDAO.getPrepaidCardOrder(orderId);
                prepaidCardOrder.setSys_flag(true);//处理标志
                prepaidCardOrder.setEffective(true);//是否付款
                userAccountDAO.savePrepaidCardOrder(prepaidCardOrder);
                GiveAccount giveAccount = userAccountDAO.getGiveAccount(phone);
                if (giveAccount == null) {
                    giveAccount = new GiveAccount();
                    giveAccount.setPhone(phone);
                    giveAccount.setBalance(prepaidCardOrder.getVoucher_value());//添加可用金额
                } else {
                    BigDecimal bigDecimal1 = new BigDecimal(giveAccount.getBalance());
                    BigDecimal bigDecimal2 = new BigDecimal(prepaidCardOrder.getVoucher_value());//添加可用金额
                    giveAccount.setBalance(bigDecimal1.add(bigDecimal2).setScale(2, BigDecimal.ROUND_UP).doubleValue());
                }
                userAccountDAO.saveGiveAccount(giveAccount);
                break;
        }
    }

    @Override
    public void executeInAppPay(String phone, String orderId, double amount) throws Exception {
        Wallet wallet = userAccountDAO.getWallet(phone);
        BigDecimal oldAmount = new BigDecimal(wallet.getRemainingSum());
        BigDecimal newAmount = new BigDecimal(amount);
        wallet.setRemainingSum(oldAmount.subtract(newAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        userAccountDAO.saveWallet(wallet);
        TOrder tOrder = (TOrder) orderDao.findByOrderid(orderId).get(0);
        tOrder.setOrderstate(OrderState.WAITCONFIRM.getName());
        tOrder.setSys_flag(true);
        tOrder.setTradeNo("i" + getOrderNum());
        orderDao.save(tOrder);
        Spending spending = new Spending("余额支付", amount, phone);//记录消费
        userAccountDAO.saveSpending(spending);
    }
}
