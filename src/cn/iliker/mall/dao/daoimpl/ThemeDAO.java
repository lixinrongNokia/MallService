package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IThemeDAO;
import cn.iliker.mall.entity.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class ThemeDAO extends HibernateDaoSupport implements IThemeDAO {
    private static final Logger log = LoggerFactory
            .getLogger(ThemeDAO.class);

    // property constants
    @Override
    public void save(Theme transientInstance) {
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
    public void delete(Theme persistentInstance) {
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
    public List findAll() {
        log.debug("finding all Crowd instances");
        try {
            return getHibernateTemplate().find("from Theme");
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }
}
