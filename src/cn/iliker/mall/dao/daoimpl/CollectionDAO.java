package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.entity.Collection;
import cn.iliker.mall.entity.GeneralList;
import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class CollectionDAO extends HibernateDaoSupport implements cn.iliker.mall.dao.ICollectionDAO {
    private static final Logger log = LoggerFactory
            .getLogger(CollectionDAO.class);
    // property constants
    private static final String COLLTIME = "colltime";
    private static final String COLOR = "color";
    private static final String SIZE = "size";

    protected void initDao() {
        // do nothing
    }

    public void save(Collection transientInstance) {
        log.debug("saving Collection instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(Collection persistentInstance) {
        log.debug("deleting Collection instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public Collection findById(java.lang.Integer id) {
        log.debug("getting Collection instance with id: " + id);
        try {
            return (Collection) getHibernateTemplate().get(Collection.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    @Override
    public GeneralList<Collection> findByUserId(Integer uid, int offset, int pageCount) {
        GeneralList<Collection> collectionGeneralList = new GeneralList<>();
        Session session = getSessionFactory().openSession();
        Criteria criteria = session.createCriteria(Collection.class, "coll");
        criteria.add(Restrictions.eq("coll.userinfo.uid", uid));
        criteria.setFirstResult((offset - 1) * pageCount);
        int totalsize = criteria.list().size();
        if (totalsize > 0) {
            int totalPage = totalsize % pageCount == 0 ? totalsize / pageCount
                    : totalsize / pageCount + 1;
            collectionGeneralList.setTotalSize(totalsize);
            collectionGeneralList.setTotalPage(totalPage);
            criteria.setMaxResults(pageCount);
            criteria.addOrder(Order.desc("coll.colltime"));
            collectionGeneralList.setList(criteria.list());
            return collectionGeneralList;
        }
        return null;
    }

    public List findByExample(Collection instance) {
        log.debug("finding Collection instance by example");
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

    private List findByProperty(String propertyName, Object value) throws RuntimeException {
        log.debug("finding Collection instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Collection as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findAll() {
        log.debug("finding all Collection instances");
        try {
            String queryString = "from Collection";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Collection merge(Collection detachedInstance) {
        log.debug("merging Collection instance");
        try {
            Collection result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Collection instance) {
        log.debug("attaching dirty Collection instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Collection instance) {
        log.debug("attaching clean Collection instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}