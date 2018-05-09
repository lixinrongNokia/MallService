package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IApkVerSionDao;
import cn.iliker.mall.entity.ApkVersion;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public class ApkVersionDAO extends HibernateDaoSupport implements IApkVerSionDao {

    @Override
    public ApkVersion find() {
        try {
            return getHibernateTemplate().get(ApkVersion.class, 1);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public boolean updateApkVersion(ApkVersion apkversion) {
        try {
            getHibernateTemplate().saveOrUpdate(apkversion);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}