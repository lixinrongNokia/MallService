package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.entity.Userdata;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class UserdataDAO extends HibernateDaoSupport {
    private static final Logger log = LoggerFactory
            .getLogger(UserdataDAO.class);
    // property constants
    private static final String HEIGHT = "height";
    private static final String ONCHEST = "onchest";
    private static final String UNDERCHEST = "underchest";
    private static final String WAIST = "waist";
    private static final String HIP = "hip";
    private static final String WEIGHT = "weight";
    private static final String BODYTYPE = "bodytype";
    private static final String BMI = "bmi";
    private static final String CUPTYPE = "cuptype";
    private static final String LOVECOLOR = "lovecolor";
    private static final String LOVESTYLE = "lovestyle";
    private static final String PANTS = "pants";

    protected void initDao() {
        // do nothing
    }

    public void save(Userdata transientInstance) {
        log.debug("saving Userdata instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Userdata persistentInstance) {
        log.debug("deleting Userdata instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Userdata findById(java.lang.Integer id) {
        log.debug("getting Userdata instance with id: " + id);
        try {
            return (Userdata) getHibernateTemplate().get(
                    "cn.iliker.mall.entity.Userdata", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Userdata instance) {
        log.debug("finding Userdata instance by example");
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
        log.debug("finding Userdata instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from cn.iliker.mall.entity.Userdata as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all Userdata instances");
        try {
            String queryString = "from Userdata";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Userdata merge(Userdata detachedInstance) {
        log.debug("merging Userdata instance");
        try {
            Userdata result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Userdata instance) {
        log.debug("attaching dirty Userdata instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Userdata instance) {
        log.debug("attaching clean Userdata instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}