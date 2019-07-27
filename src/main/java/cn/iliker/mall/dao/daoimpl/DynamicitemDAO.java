package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.LockMode;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IDynamicitemDAO;
import cn.iliker.mall.entity.Dynamicitem;

public class DynamicitemDAO extends HibernateDaoSupport implements IDynamicitemDAO {
    public static final String ACCEPT_TIME = "acceptTime";
    public static final String ACCEPT_STATION = "acceptStation";
    public static final String REMARK = "remark";
    public static final String TRACES_INDEX = "tracesIndex";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(Dynamicitem transientInstance) {
        try {
            getHibernateTemplate().save(transientInstance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void delete(Dynamicitem persistentInstance) {
        try {
            getHibernateTemplate().delete(persistentInstance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public Dynamicitem findById(java.lang.Integer id) {
        try {
            Dynamicitem instance = (Dynamicitem) getHibernateTemplate().get(Dynamicitem.class, id);
            return instance;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByExample(Dynamicitem instance) {
        try {
            List results = getHibernateTemplate().findByExample(instance);
            return results;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        try {
            String queryString = "from Dynamicitem as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByAcceptTime(Object acceptTime) {
        return findByProperty(ACCEPT_TIME, acceptTime);
    }

    public List findByAcceptStation(Object acceptStation) {
        return findByProperty(ACCEPT_STATION, acceptStation);
    }

    public List findByRemark(Object remark) {
        return findByProperty(REMARK, remark);
    }

    @Override
    public List findAll() {
        try {
            return getHibernateTemplate().find("from Dynamicitem");
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public Dynamicitem merge(Dynamicitem detachedInstance) {
        try {
            Dynamicitem result = getHibernateTemplate().merge(
                    detachedInstance);
            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void attachDirty(Dynamicitem instance) {
        try {
            getHibernateTemplate().saveOrUpdate(instance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void attachClean(Dynamicitem instance) {
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
        } catch (RuntimeException re) {
            throw re;
        }
    }

}