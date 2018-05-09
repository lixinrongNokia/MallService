package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.ICourierDAO;
import cn.iliker.mall.entity.CourierCompany;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class CourierCompanyDAO extends HibernateDaoSupport implements ICourierDAO {
    private static final Logger log = LoggerFactory
            .getLogger(CourierCompanyDAO.class);
    // property constants
    private static final String COMPANY_CODE = "companyCode";
    private static final String COMPANY_NAME = "companyName";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(CourierCompany transientInstance) {
        log.debug("saving CourierCompany instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(CourierCompany persistentInstance) {
        log.debug("deleting CourierCompany instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public CourierCompany findById(java.lang.Integer id) {
        log.debug("getting CourierCompany instance with id: " + id);
        try {
            return (CourierCompany) getHibernateTemplate()
                    .get(CourierCompany.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(CourierCompany instance) {
        log.debug("finding CourierCompany instance by example");
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
        log.debug("finding CourierCompany instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from CourierCompany as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findAll() {
        log.debug("finding all CourierCompany instances");
        try {
            return getHibernateTemplate().find("from CourierCompany");
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    @Override
    public CourierCompany merge(CourierCompany detachedInstance) {
        log.debug("merging CourierCompany instance");
        try {
            CourierCompany result = getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(CourierCompany instance) {
        log.debug("attaching dirty CourierCompany instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(CourierCompany instance) {
        log.debug("attaching clean CourierCompany instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}