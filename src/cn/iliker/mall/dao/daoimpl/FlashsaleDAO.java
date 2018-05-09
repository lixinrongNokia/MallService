package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IFlashsaleDAO;
import cn.iliker.mall.entity.FlashSale;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class FlashsaleDAO extends HibernateDaoSupport implements IFlashsaleDAO {
    private static final Logger log = LoggerFactory.getLogger(FlashsaleDAO.class);
    //property constants
    private static final String STATUS = "status";
    private static final String NUMBER = "count";
    private static final String DISCOUNT = "discount";


    protected void initDao() {
        //do nothing
    }

    @Override
    public void save(FlashSale transientInstance) {
        log.debug("saving Flashsale instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(FlashSale persistentInstance) {
        log.debug("deleting FlashSale instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public FlashSale findById(java.lang.Integer id) {
        log.debug("getting Flashsale instance with id: " + id);
        try {
            return getHibernateTemplate()
                    .get(FlashSale.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }


    public List findByExample(FlashSale instance) {
        log.debug("finding Flashsale instance by example");
        try {
            List results = getHibernateTemplate().findByExample(instance);
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    @Override
    public List findByProperty(String propertyName, Object value) {
        log.debug("finding Flashsale instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from FlashSale as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findAll() {
        log.debug("finding all Flashsale instances");
        try {
            String queryString = "from FlashSale";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public FlashSale merge(FlashSale detachedInstance) {
        log.debug("merging Flashsale instance");
        try {
            FlashSale result = getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(FlashSale instance) {
        log.debug("attaching dirty Flashsale instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(FlashSale instance) {
        log.debug("attaching clean Flashsale instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}