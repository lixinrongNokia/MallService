package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IUserAccountDAO;
import cn.iliker.mall.entity.GiveAccount;
import cn.iliker.mall.entity.Income;
import cn.iliker.mall.entity.IntegralAccount;
import cn.iliker.mall.entity.Integralincome;
import cn.iliker.mall.entity.Integralspending;
import cn.iliker.mall.entity.PrepaidCardOrder;
import cn.iliker.mall.entity.Rechargeableorder;
import cn.iliker.mall.entity.Spending;
import cn.iliker.mall.entity.Wallet;

public class UserAccountDAO extends HibernateDaoSupport implements IUserAccountDAO {

    private final String PROPERTYNAME;//查询参数

    public UserAccountDAO() {
        PROPERTYNAME = "phone";
    }

    /*保存或更新充值订单*/
    @Override
    public void saveRechargeableorder(Rechargeableorder rechargeableorder) {
        getHibernateTemplate().saveOrUpdate(rechargeableorder);
    }

    /*根据订单号查找充值订单*/
    @Override
    public Rechargeableorder getRechargeableorder(String orderId) {
        return (Rechargeableorder) findByProperty("Rechargeableorder", "rechargeableid", orderId);
    }

    /*保存或更新卡券订单*/
    @Override
    public void savePrepaidCardOrder(PrepaidCardOrder prepaidCardOrder) {
        getHibernateTemplate().saveOrUpdate(prepaidCardOrder);
    }

    /*赠送卡券*/
    @Override
    public void givingPrepaidCard(PrepaidCardOrder prepaidCardOrder) throws RuntimeException {
        getHibernateTemplate().save(prepaidCardOrder);
    }

    /*根据订单号查找卡券订单*/
    @Override
    public PrepaidCardOrder getPrepaidCardOrder(String id) {
        return (PrepaidCardOrder) findByProperty("PrepaidCardOrder", "id", id);
    }

    /*根据手机号查找卡券金额*/
    @Override
    public GiveAccount getGiveAccount(String phone) {
        return (GiveAccount) findByProperty("GiveAccount", PROPERTYNAME, phone);
    }

    /*保存或更新卡券金额*/
    @Override
    public void saveGiveAccount(GiveAccount giveAccount) {
        getHibernateTemplate().saveOrUpdate(giveAccount);
    }

    /*根据手机号查找积分*/
    @Override
    public IntegralAccount getIntegral(String phone) {
        return (IntegralAccount) findByProperty("IntegralAccount", PROPERTYNAME, phone);
    }

    /*保存或更新积分*/
    @Override
    public void saveIntegral(IntegralAccount integralAccount) {
        getHibernateTemplate().saveOrUpdate(integralAccount);
    }

    /*根据手机号查找余额*/
    @Override
    public Wallet getWallet(String phone) {
        return (Wallet) findByProperty("Wallet", PROPERTYNAME, phone);
    }

    /*根据保存或更新余额*/
    @Override
    public void saveWallet(Wallet wallet) {
        getHibernateTemplate().saveOrUpdate(wallet);
    }

    public Object findByProperty(String EntityName, String propertyName, Object value) {
        String queryString = "from " + EntityName + " as model where model."
                + propertyName + "= ?";
        List list = getHibernateTemplate().find(queryString, value);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /*插入消费记录*/
    @Override
    public void saveSpending(Spending spending) {
        getHibernateTemplate().save(spending);
    }

    /*插入收入记录*/
    @Override
    public void saveIncome(Income income) {
        getHibernateTemplate().save(income);
    }

    /*记录积分支出*/
    @Override
    public void saveIntegralspending(Integralspending integralspending) {
        getHibernateTemplate().save(integralspending);
    }

    /*记录积分收入*/
    @Override
    public void saveIntegralincome(Integralincome integralincome) {
        getHibernateTemplate().save(integralincome);
    }
}
