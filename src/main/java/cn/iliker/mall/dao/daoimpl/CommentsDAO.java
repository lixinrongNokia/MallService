package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.ICommentsDAO;
import cn.iliker.mall.entity.Comments;

public class CommentsDAO extends HibernateDaoSupport implements ICommentsDAO {
    private static final Logger log = LoggerFactory
            .getLogger(CommentsDAO.class);
    // property constants
    private static final String NICKNAME = "nickname";
    private static final String COMMAUDIOPATH = "commaudiopath";
    private static final String COMMTEXT = "commtext";

    protected void initDao() {
        // do nothing
    }

    public void save(Comments transientInstance) {
        log.debug("saving Comments instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(Comments persistentInstance) {
        log.debug("deleting Comments instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Comments findById(java.lang.Integer id) {
        log.debug("getting Comments instance with id: " + id);
        try {
            return (Comments) getHibernateTemplate().get(
                    "cn.iliker.entity.Comments", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Comments instance) {
        log.debug("finding Comments instance by example");
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
        log.debug("finding Comments instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Comments as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findAll(int shareId) throws RuntimeException{
        log.debug("finding all Comments instances");
        try {
            return findByProperty("share.shareId",shareId);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Comments merge(Comments detachedInstance) {
        log.debug("merging Comments instance");
        try {
            Comments result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Comments instance) {
        log.debug("attaching dirty Comments instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Comments instance) {
        log.debug("attaching clean Comments instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}