package cn.iliker.mall.dao.daoimpl;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IWXTokenDAO;
import cn.iliker.mall.entity.WXToken;

public class WxTokenDAO extends HibernateDaoSupport implements IWXTokenDAO {

    @Override
    public boolean updateToken(WXToken token) {
        try {
            getHibernateTemplate().saveOrUpdate(token);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public WXToken getToken(int id) {
        return getHibernateTemplate().get(WXToken.class, id);
    }
}
