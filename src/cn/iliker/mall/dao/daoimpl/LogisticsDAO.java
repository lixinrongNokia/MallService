package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IlogisticsDAO;
import cn.iliker.mall.entity.Logistics;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class LogisticsDAO extends HibernateDaoSupport implements IlogisticsDAO {
    private static final Logger log = LoggerFactory
            .getLogger(LogisticsDAO.class);
    // property constants
    private static final String LOGISTICSCODE = "logisticscode";

    protected void initDao() {
        // do nothing
    }

    public void save(Logistics transientInstance) {
        log.debug("saving Logistics instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void add(Logistics logistics) throws RuntimeException {
        getHibernateTemplate().save(logistics);
    }

    @Override
    public void delete(Logistics persistentInstance) {
        log.debug("deleting Logistics instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public Logistics findById(java.lang.Integer id) {
        log.debug("getting Logistics instance with id: " + id);
        try {
            return (Logistics) getHibernateTemplate().get(
                    "cn.iliker.mall.entity.Logistics", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Logistics instance) {
        log.debug("finding Logistics instance by example");
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
        log.debug("finding Logistics instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Logistics as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findAll() {
        log.debug("finding all Logistics instances");
        try {
            String queryString = "from Logistics";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Logistics merge(Logistics detachedInstance) {
        log.debug("merging Logistics instance");
        try {
            Logistics result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Logistics instance) {
        log.debug("attaching dirty Logistics instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Logistics instance) {
        log.debug("attaching clean Logistics instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}