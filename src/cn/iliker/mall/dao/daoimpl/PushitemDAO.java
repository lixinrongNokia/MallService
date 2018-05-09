package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IPushitemDAO;
import cn.iliker.mall.entity.PushItem;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PushitemDAO extends HibernateDaoSupport implements IPushitemDAO {
    public static final String STATE = "state";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(PushItem transientInstance) {
        try {
            getHibernateTemplate().save(transientInstance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void delete(PushItem persistentInstance) {
        try {
            getHibernateTemplate().delete(persistentInstance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public PushItem findById(java.lang.Integer id) {
        try {
            return (PushItem) getHibernateTemplate().get(
                    "cn.iliker.mall.entity.PushItem", id);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByExample(PushItem instance) {
        try {
            return getHibernateTemplate().findByExample(instance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        try {
            String queryString = "from PushItem as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByState(Object state) {
        return findByProperty(STATE, state);
    }

    @Override
    public List findAll() {
        try {
            return getHibernateTemplate().find("from PushItem");
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public PushItem merge(PushItem detachedInstance) {
        try {
            return getHibernateTemplate()
                    .merge(detachedInstance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void attachDirty(PushItem instance) {
        try {
            getHibernateTemplate().saveOrUpdate(instance);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void attachClean(PushItem instance) {
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
        } catch (RuntimeException re) {
            throw re;
        }
    }

}