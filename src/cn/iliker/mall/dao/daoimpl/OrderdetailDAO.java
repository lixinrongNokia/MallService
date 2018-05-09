package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.entity.Orderdetail;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class OrderdetailDAO extends HibernateDaoSupport {
    private static final Logger log = LoggerFactory
            .getLogger(OrderdetailDAO.class);
    // property constants
    private static final String ORDERAMOUNT = "orderamount";
    private static final String SALETOTALPRICE = "saletotalprice";

    protected void initDao() {
        // do nothing
    }

    public void save(Orderdetail transientInstance) {
        log.debug("saving Orderdetail instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Orderdetail persistentInstance) {
        log.debug("deleting Orderdetail instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Orderdetail findById(java.lang.Integer id) {
        log.debug("getting Orderdetail instance with id: " + id);
        try {
            return (Orderdetail) getHibernateTemplate().get(
                    "cn.iliker.mall.entity.Orderdetail", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Orderdetail instance) {
        log.debug("finding Orderdetail instance by example");
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
        log.debug("finding Orderdetail instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Orderdetail as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all Orderdetail instances");
        try {
            String queryString = "from Orderdetail";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Orderdetail merge(Orderdetail detachedInstance) {
        log.debug("merging Orderdetail instance");
        try {
            Orderdetail result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Orderdetail instance) {
        log.debug("attaching dirty Orderdetail instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Orderdetail instance) {
        log.debug("attaching clean Orderdetail instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}