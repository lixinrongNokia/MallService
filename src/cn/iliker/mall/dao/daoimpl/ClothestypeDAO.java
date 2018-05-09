package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IClothesType;
import cn.iliker.mall.entity.Clothestype;
import cn.iliker.mall.error.MyError;
import org.hibernate.LockMode;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class ClothestypeDAO extends HibernateDaoSupport implements IClothesType {
    private static final Logger log = LoggerFactory
            .getLogger(ClothestypeDAO.class);
    // property constants
    private static final String NAME = "name";

    protected void initDao() {
        // do nothing
    }

    @Override
    public List<Object[]> findAllType() {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        String sql = "SELECT crowd.id,crowd.name,GROUP_CONCAT(c.id,':',c.name) FROM clothestype c join crowd on(c.crowdid=crowd.id) GROUP BY crowd.id";
        try {
            NativeQuery query = session.createNativeQuery(sql);
            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public void save(Clothestype transientInstance) throws MyError {
        log.debug("saving Clothestype instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (Exception re) {
            log.error("add failed", re);
            throw new MyError("添加失败");
        }
    }

    @Override
    public void delete(Clothestype persistentInstance) {
        log.debug("deleting Clothestype instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public Clothestype findById(java.lang.Integer id) {
        log.debug("getting Clothestype instance with id: " + id);
        try {
            return (Clothestype) getHibernateTemplate().get(
                    "cn.iliker.entity.Clothestype", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Clothestype instance) {
        log.debug("finding Clothestype instance by example");
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
        log.debug("finding Clothestype instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Clothestype as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }


    @Override
    public List findAll() {
        log.debug("finding all Clothestype instances");
        Session session = null;
        try {
            String queryString = "SELECT crowd.name,GROUP_CONCAT(c.id,';',c.name,';',c.typeimg) FROM clothestype c join crowd on(c.crowdid=crowd.id) GROUP BY crowd.id";
            session = getSessionFactory().openSession();
            return session.createNativeQuery(queryString).list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        } finally {
            if (session != null) session.close();
        }
    }

    public Clothestype merge(Clothestype detachedInstance) {
        log.debug("merging Clothestype instance");
        try {
            Clothestype result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Clothestype instance) {
        log.debug("attaching dirty Clothestype instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Clothestype instance) {
        log.debug("attaching clean Clothestype instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public static IClothesType getFromApplicationContext(
            ApplicationContext ctx) {
        return (IClothesType) ctx.getBean("ClothestypeDAO");
    }

    @Override
    public List findAll(int crowdid) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createQuery("from Clothestype c where c.crowd.id=" + crowdid);
        return query.list();
    }
}