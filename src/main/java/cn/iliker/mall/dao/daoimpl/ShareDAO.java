package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IShareDAO;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Share;

public class ShareDAO extends HibernateDaoSupport implements IShareDAO {
    private static final Logger log = LoggerFactory.getLogger(ShareDAO.class);
    // property constants
    private static final String CONTENT = "content";
    private static final String LOCATION = "location";
    private static final String PIC = "pic";
    private static final String PICCOUNT = "piccount";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(Share transientInstance) throws RuntimeException {
        log.debug("saving Share instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(Share persistentInstance) {
        log.debug("deleting Share instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public Share findById(java.lang.Integer shareId) {
        log.debug("getting Share instance with id: " + shareId);
        try {
            return getHibernateTemplate().get(
                    Share.class, shareId);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Share instance) {
        log.debug("finding Share instance by example");
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
        log.debug("finding Share instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Share as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public GeneralList<Share> findAll(int offset, int pagesize, int node) {
        int totalsize;
        int totalPage = 0;
        GeneralList<Share> sharelist = null;
        Session session = getHibernateTemplate().getSessionFactory()
                .openSession();
        String queryString;
        NativeQuery query;
        try {
            if (node != 0) {
                queryString = "select s.* from `share` s  where s.releaseTime < (select date_sub(date_sub(date_format(now(),'%y-%m-%d'),interval extract(day from now()) day),interval ? month)) order by s.releaseTime DESC";
                query = session.createNativeQuery(queryString);
                query.addEntity(Share.class);
                query.setParameter(0, node);
            } else {
                queryString = "select s.* from `share` s order by s.releaseTime DESC";
                query = session.createNativeQuery(queryString);
                query.addEntity(Share.class);
            }

            if (query.list().size() > 0) {
                totalsize = query.list().size();
                sharelist = new GeneralList<>();
                if (offset != 0 && pagesize != 0) {
                    totalPage = totalsize % pagesize == 0 ? totalsize / pagesize
                            : totalsize / pagesize + 1;
                    query.setFirstResult((offset - 1) * pagesize);
                    query.setMaxResults(pagesize);
                }
                sharelist.setTotalSize(totalsize);
                sharelist.setTotalPage(totalPage);
                sharelist.setList(query.list());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return sharelist;
    }

    public Share merge(Share detachedInstance) {
        log.debug("merging Share instance");
        try {
            Share result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Share instance) {
        log.debug("attaching dirty Share instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Share instance) {
        log.debug("attaching clean Share instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Override
    public boolean delShare(Integer shareId) {
        Share share = getHibernateTemplate().get(Share.class, shareId);
        if (share != null) {
            try {
                getHibernateTemplate().delete(share);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*@Override
    public Share findShare(Integer shareId) {
        Share share = null;
        Session session = getHibernateTemplate().getSessionFactory()
                .openSession();
        try {
            share = (Share) session.get(Share.class, shareId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return share;
    }*/
}