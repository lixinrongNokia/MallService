package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IJSWXTotenDAO;
import cn.iliker.mall.entity.JSWXToken;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class JSWXTotenDAO extends HibernateDaoSupport implements IJSWXTotenDAO {
    @Override
    public boolean saveJSWXToken(JSWXToken jswxToken) {
        try {
            getHibernateTemplate().saveOrUpdate(jswxToken);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public JSWXToken getJSWXTokenByOpenId(String openid) {
        List<JSWXToken> list = findByProperty("openid", openid);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    private List findByProperty(String propertyName, Object value) {
        try {
            String queryString = "from JSWXToken as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            return null;
        }
    }
}
