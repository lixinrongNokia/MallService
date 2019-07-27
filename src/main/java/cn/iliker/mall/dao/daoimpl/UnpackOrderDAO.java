package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IUnpackOrderDAO;
import cn.iliker.mall.entity.UnpackOrder;
import cn.iliker.mall.entity.UnpackOrderId;

public class UnpackOrderDAO extends HibernateDaoSupport implements IUnpackOrderDAO {
    @Override
    public void save(UnpackOrder unpackOrder) throws Exception {
        getHibernateTemplate().saveOrUpdate(unpackOrder);
    }

    @Override
    public List getUnpackOrders() {
        return getHibernateTemplate().find("from cn.iliker.mall.entity.UnpackOrder");
    }

    @Override
    public UnpackOrder find(UnpackOrderId unpackOrderId){
        try (Session session = getSessionFactory().openSession()) {
            Query query = session.createQuery("from UnpackOrder u WHERE u.unpackOrderId.orderInfo.id=:orderId AND u.unpackOrderId.storeInfo.id=:storeId");
            query.setParameter("orderId", unpackOrderId.getOrderInfo().getId());
            query.setParameter("storeId", unpackOrderId.getStoreInfo().getId());
            return (UnpackOrder) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<UnpackOrder> pageGetUnpackOrder(int storeId, int pageIndex, int pageCount) {
        try (Session session = getSessionFactory().openSession()) {
            Query query = session.createQuery("from UnpackOrder u WHERE u.unpackOrderId.storeInfo.id=:storeId");
            query.setParameter("storeId", storeId);
            query.setFirstResult((pageIndex - 1) * pageCount);
            query.setMaxResults(pageCount);
            return query.list();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
