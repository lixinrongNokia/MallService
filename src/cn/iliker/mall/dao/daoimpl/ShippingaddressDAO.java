package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IDeliverInfoDAO;
import cn.iliker.mall.entity.Shippingaddress;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class ShippingaddressDAO extends HibernateDaoSupport implements IDeliverInfoDAO {
    private static final Logger log = LoggerFactory
            .getLogger(ShippingaddressDAO.class);
    // property constants
    private static final String CONSIGNEE_NAME = "consigneeName";
    private static final String PHONE = "phone";
    private static final String ADDRESS = "address";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(Shippingaddress transientInstance) {
        log.debug("saving Shippingaddress instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(Shippingaddress persistentInstance) {
        log.debug("deleting Shippingaddress instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Shippingaddress findById(java.lang.Integer id) {
        log.debug("getting Shippingaddress instance with id: " + id);
        try {
            return getHibernateTemplate()
                    .get(Shippingaddress.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Shippingaddress instance) {
        log.debug("finding Shippingaddress instance by example");
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

    @Override
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Shippingaddress instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from Shippingaddress as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all Shippingaddress instances");
        try {
            String queryString = "from Shippingaddress";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Shippingaddress merge(Shippingaddress detachedInstance) {
        log.debug("merging Shippingaddress instance");
        try {
            Shippingaddress result = getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Override
    public void attachDirty(Shippingaddress instance) throws RuntimeException{
        log.debug("attaching dirty Shippingaddress instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Shippingaddress instance) {
        log.debug("attaching clean Shippingaddress instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}