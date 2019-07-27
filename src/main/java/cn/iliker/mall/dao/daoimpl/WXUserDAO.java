package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IWXUserDAO;
import cn.iliker.mall.entity.WXUser;

public class WXUserDAO extends HibernateDaoSupport implements IWXUserDAO {
    @Override
    public void saveWXUser(WXUser user) throws RuntimeException {
        try {
            getHibernateTemplate().saveOrUpdate(user);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    private List findByProperty(String propertyName, Object value) {
        try {
            String queryString = "from cn.iliker.mall.entity.WXUser as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            throw re;
        }
    }


    @Override
    public WXUser getWXUserByUnionid(String unionid) {
        List list = findByProperty("bindWXID.unionID", unionid);
        if (list.isEmpty()) {
            return null;
        } else {
            return (WXUser) list.get(0);
        }
    }

    @Override
    public WXUser getWXUserByUID(int uid) {
        List list = findByProperty("bindWXID.userinfo.uid", uid);
        if (list.isEmpty()) {
            return null;
        } else {
            return (WXUser) list.get(0);
        }
    }

    @Override
    public void delWXUser(WXUser wxUser) {
        getHibernateTemplate().delete(wxUser);
    }
}
