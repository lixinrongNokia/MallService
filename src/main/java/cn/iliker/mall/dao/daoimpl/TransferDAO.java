package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.ITransferDAO;
import cn.iliker.mall.entity.Transfer;

public class TransferDAO extends HibernateDaoSupport implements ITransferDAO {
    private static final Logger log = LoggerFactory
            .getLogger(TransferDAO.class);
    // property constants
    private static final String PHONE = "phone";
    private static final String ACCOUNT = "account";
    private static final String REALNAME = "realname";
    private static final String AMOUNT = "amount";
    private static final String PUTINFOR_TIME = "putinforTime";
    private static final String MANAGE_TIME = "manageTime";
    private static final String DEAL_TAG = "dealTag";

    protected void initDao() {
        // do nothing
    }

    public void delete(Transfer persistentInstance) {
        log.debug("deleting Transfer instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public Transfer findById(java.lang.Integer id) {
        log.debug("getting Transfer instance with id: " + id);
        try {
            return getHibernateTemplate().get(Transfer.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Transfer instance) {
        log.debug("finding Transfer instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: "
                    + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    private List findByProperty(String propertyName, Object value) {
        log.debug("finding Transfer instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Transfer as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findByPhone(Object phone) {
        return findByProperty(PHONE, phone);
    }


    @Override
    public List findByDealTag(Object dealTag) {
        return findByProperty(DEAL_TAG, dealTag);
    }

    @Override
    public List findAll() {
        log.debug("finding all Transfer instances");
        try {
            String queryString = "from Transfer";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Transfer merge(Transfer detachedInstance) {
        log.debug("merging Transfer instance");
        try {
            Transfer result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Override
    public void attachDirty(Transfer instance){
        log.debug("attaching dirty Transfer instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Transfer instance) {
        log.debug("attaching clean Transfer instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}