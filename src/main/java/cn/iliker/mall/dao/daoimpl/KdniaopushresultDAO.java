package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IKdniaopushresultDAO;
import cn.iliker.mall.entity.KdniaoPushResult;

public class KdniaopushresultDAO extends HibernateDaoSupport implements IKdniaopushresultDAO {
    public static final String EBUSINESS_ID = "ebusinessId";
    public static final String COUNT = "count";
    public static final String PUSH_TIME = "pushTime";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(KdniaoPushResult transientInstance) {
        try {
            getHibernateTemplate().save(transientInstance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void delete(KdniaoPushResult persistentInstance) {
        try {
            getHibernateTemplate().delete(persistentInstance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public KdniaoPushResult findById(java.lang.Integer id) {
        try {
            KdniaoPushResult instance = getHibernateTemplate()
                    .get(KdniaoPushResult.class, id);
            return instance;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByExample(KdniaoPushResult instance) {
        try {
            List results = getHibernateTemplate().findByExample(instance);
            return results;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        try {
            String queryString = "from KdniaoPushResult as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByEbusinessId(Object ebusinessId) {
        return findByProperty(EBUSINESS_ID, ebusinessId);
    }

    @Override
    public List findByCount(Object count) {
        return findByProperty(COUNT, count);
    }

    public List findByPushTime(Object pushTime) {
        return findByProperty(PUSH_TIME, pushTime);
    }

    @Override
    public List findAll() {
        try {
            return getHibernateTemplate().find("from KdniaoPushResult");
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public KdniaoPushResult merge(KdniaoPushResult detachedInstance) {
        try {
            KdniaoPushResult result = getHibernateTemplate()
                    .merge(detachedInstance);
            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void attachDirty(KdniaoPushResult instance) {
        try {
            getHibernateTemplate().saveOrUpdate(instance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void attachClean(KdniaoPushResult instance) {
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
        } catch (RuntimeException re) {
            throw re;
        }
    }

}