package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IWxTicketDAO;
import cn.iliker.mall.entity.WXTicket;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

public class WxTicketDAO extends HibernateDaoSupport implements IWxTicketDAO {

    @Override
    public boolean updateTicket(WXTicket wxTicket) {
        try {
            getHibernateTemplate().saveOrUpdate(wxTicket);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    @Override
    public WXTicket getTicket(int id) {
        return getHibernateTemplate().get(WXTicket.class, id);
    }
}
