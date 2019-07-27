package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IBrandDAO;
import cn.iliker.mall.entity.Brand;
import cn.iliker.mall.error.BrandSaveError;
import cn.iliker.mall.error.DelError;

public class BrandDAO extends HibernateDaoSupport implements IBrandDAO {
    private static final Logger log = LoggerFactory.getLogger(BrandDAO.class);
    private final String BRANDID = "brandId";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(Brand transientInstance) throws BrandSaveError {
        log.debug("saving Crowd instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (HibernateException re) {
            log.error("save failed", re);
            throw new BrandSaveError();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int brandId) throws DelError {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        String sql = "delete from Brand b where b." + BRANDID + "=" + brandId;
        Query query = session.createQuery(sql);
        try {
            query.executeUpdate();
        } catch (HibernateException e) {
            throw new DelError();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            session.close();
        }
    }

    @Override
    public Brand findById(Integer id) {
        log.debug("getting Goods instance with id: " + id);
        try {
            return (Brand) getHibernateTemplate().get(
                    "cn.iliker.entity.Brand", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    @Override
    public boolean updateBrand(Brand brand) {
        Session session = getHibernateTemplate().getSessionFactory()
                .openSession();
        try {
            session.saveOrUpdate(session.merge(brand));
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public List findAll() {
        log.debug("finding all Crowd instances");
        try {
            return getHibernateTemplate().find("from Brand");
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    @Override
    public List findBrands() throws RuntimeException {
        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            String sql = "select b.brandId,b.BrandName from Brand b";
            List<Object[]> list = session.createNativeQuery(sql).list();
            if (!list.isEmpty()) {
                return list;
            }
            return null;
        } catch (RuntimeException re) {
            throw re;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}