package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IadminManagerDAO;
import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.error.ExistsError;
import cn.iliker.mall.error.MyError;

public class AdminmanagerDAO extends HibernateDaoSupport implements IadminManagerDAO {
    private static final Logger log = LoggerFactory
            .getLogger(AdminmanagerDAO.class);
    // property constants
    private static final String NICKNAME = "nickname";
    private static final String PASSWORD = "password";

    protected void initDao() {
    }

    @Override
    public void save(Adminmanager transientInstance) throws ExistsError, MyError {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        try {
            Adminmanager adminmanager = findByNickname(transientInstance.getNickname());
            if (adminmanager != null) {
                throw new ExistsError("帐号名已使用");
            }
            session.save(transientInstance);
        } catch (HibernateException re) {
            throw new MyError("添加失败");
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Adminmanager persistentInstance) {
        log.debug("deleting Adminmanager instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public Adminmanager findById(java.lang.Integer id) {
        log.debug("getting Adminmanager instance with id: " + id);
        try {
            return (Adminmanager) getHibernateTemplate().get(Adminmanager.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Adminmanager instance) {
        log.debug("finding Adminmanager instance by example");
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
        log.debug("finding Adminmanager instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from cn.iliker.mall.entity.Adminmanager as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public Adminmanager findByNickname(String Nickame) {
        Adminmanager adminmanager = null;
        if (Nickame != null && !"".equals(Nickame)) {
            Session session = getHibernateTemplate().getSessionFactory().openSession();
            String queryString = "from cn.iliker.mall.entity.Adminmanager u where u.nickname=:nickname";
            Query query = session.createQuery(queryString);
            query.setParameter("nickname", Nickame);
            try {
                adminmanager = (Adminmanager) query.uniqueResult();
            } catch (HibernateException e) {
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return adminmanager;
    }

    public Adminmanager merge(Adminmanager detachedInstance) {
        log.debug("merging Adminmanager instance");
        try {
            Adminmanager result = getHibernateTemplate().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Adminmanager instance) {
        log.debug("attaching dirty Adminmanager instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Adminmanager instance) {
        log.debug("attaching clean Adminmanager instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Override
    public List<Adminmanager> findAll() {
        return (List<Adminmanager>) getHibernateTemplate().find("from cn.iliker.mall.entity.Adminmanager m where m.nickname <> '高石花道'");
        /*List<Adminmanager> admins = null;
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Object[]> list = session.createNativeQuery("select m.id,m.nickname,m.password,m.goodsManager,m.commentsManager,m.appManager,m.emsManager,m.status,m.isadmin,m.pricinga_power,m.financial from adminmanager m where m.nickname <> '高石花道'").list();
        if (list != null) {
            admins = new ArrayList<>();
            for (Object[] obj : list) {
                Adminmanager admin = new Adminmanager();
                admin.setId((Integer) obj[0]);
                admin.setNickname((String) obj[1]);
                admin.setPassword((String) obj[2]);
                admin.setGoodsManager((Boolean) obj[3]);
                admin.setCommentsManager((Boolean) obj[4]);
                admin.setAppManager((Boolean) obj[5]);
                admin.setEmsManager((Boolean) obj[6]);
                admin.setStatus((Boolean) obj[7]);
                admin.setIsadmin((Boolean) obj[8]);
                admin.setPricinga_power((Boolean) obj[9]);
                admin.setFinancial((Boolean) obj[10]);
                admins.add(admin);
            }
        }
        session.close();
        return admins;*/
    }

    @Override
    public boolean update(Adminmanager persistentInstance) {
        try {
            getHibernateTemplate().saveOrUpdate(persistentInstance);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}