package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.ICrowd;
import cn.iliker.mall.entity.Crowd;

public class CrowdDAO extends HibernateDaoSupport implements ICrowd {
    private static final Logger log = LoggerFactory.getLogger(CrowdDAO.class);
    // property constants
    private static final String NAME = "name";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(Crowd transientInstance) {
        log.debug("saving Crowd instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(Crowd persistentInstance) {
        log.debug("deleting Crowd instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public Crowd findById(java.lang.Integer id) {
        log.debug("getting Crowd instance with id: " + id);
        try {
            return (Crowd) getHibernateTemplate().get(Crowd.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Crowd instance) {
        log.debug("finding Crowd instance by example");
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
        log.debug("finding Crowd instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Crowd as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findByName(Object name) {
        return findByProperty(NAME, name);
    }

    @Override
    public List findAll() {
        log.debug("finding all Crowd instances");
        try {
            String queryString = "from Crowd";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    @Override
    public Crowd merge(Crowd detachedInstance) {
        log.debug("merging Crowd instance");
        try {
            Crowd result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Crowd instance) {
        log.debug("attaching dirty Crowd instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Crowd instance) {
        log.debug("attaching clean Crowd instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}