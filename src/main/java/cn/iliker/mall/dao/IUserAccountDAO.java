package cn.iliker.mall.dao;

import cn.iliker.mall.entity.GiveAccount;
import cn.iliker.mall.entity.Income;
import cn.iliker.mall.entity.IntegralAccount;
import cn.iliker.mall.entity.Integralincome;
import cn.iliker.mall.entity.Integralspending;
import cn.iliker.mall.entity.PrepaidCardOrder;
import cn.iliker.mall.entity.Rechargeableorder;
import cn.iliker.mall.entity.Spending;
import cn.iliker.mall.entity.Wallet;

public interface IUserAccountDAO {
    /*保存或更新充值订单*/
    void saveRechargeableorder(Rechargeableorder rechargeableorder) throws RuntimeException;

    /*根据订单号查找充值订单*/
    Rechargeableorder getRechargeableorder(String orderId);

    /*保存或更新卡券订单*/
    void savePrepaidCardOrder(PrepaidCardOrder prepaidCardOrder) throws RuntimeException;

    /*赠送卡券*/
    void givingPrepaidCard(PrepaidCardOrder prepaidCardOrder) throws RuntimeException;

    /*根据订单号查找卡券订单*/
    PrepaidCardOrder getPrepaidCardOrder(String id);

    GiveAccount getGiveAccount(String phone);

    /*保存或更新卡券金额*/
    void saveGiveAccount(GiveAccount giveAccount) throws RuntimeException;

    IntegralAccount getIntegral(String phone);

    void saveIntegral(IntegralAccount integralAccount) throws RuntimeException;

    Wallet getWallet(String phone);

    void saveWallet(Wallet wallet) throws RuntimeException;

    void saveSpending(Spending spending) throws RuntimeException;

    void saveIncome(Income income) throws RuntimeException;
    void saveIntegralspending(Integralspending integralspending);
    void saveIntegralincome(Integralincome integralincome);
}
