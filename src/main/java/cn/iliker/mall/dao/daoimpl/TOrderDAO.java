package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.ITOrderDao;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.stateattr.OrderState;

public class TOrderDAO extends HibernateDaoSupport implements ITOrderDao {
    private static final Logger log = LoggerFactory.getLogger(TOrderDAO.class);
    // property constants
    private static final String ORDERID = "orderid";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(TOrder transientInstance) throws RuntimeException {
        log.debug("saving TOrder instance");
        try {
            getHibernateTemplate().saveOrUpdate(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TOrder persistentInstance) {
        log.debug("deleting TOrder instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public TOrder findById(java.lang.Integer id) {
        log.debug("getting TOrder instance with id: " + id);
        try {
            return (TOrder) getHibernateTemplate().get(
                    "cn.iliker.mall.entity.TOrder", id);
        } catch (RuntimeException re) {
            return null;
        }
    }

    public List findByExample(TOrder instance) {
        log.debug("finding TOrder instance by example");
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

    private List findByProperty(String propertyName, Object value) throws RuntimeException{
        log.debug("finding TOrder instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from TOrder as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findByOrderid(String orderid) throws RuntimeException{
        return findByProperty(ORDERID, orderid);
    }

    @Override
    public List findAll() {
        log.debug("finding all TOrder instances");
        try {
            String queryString = "from cn.iliker.mall.entity.TOrder torder Order by torder.orderdate desc";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    private static void getTotal(GeneralList<TOrder> orderlist, Criteria criteria, int pagesize) {
        int totalsize = criteria.list().size();
        if (totalsize > 0) {
            int totalPage = totalsize % pagesize == 0 ? totalsize / pagesize
                    : totalsize / pagesize + 1;
            orderlist.setTotalSize(totalsize);
            orderlist.setTotalPage(totalPage);
        }
    }

    @Override
    public GeneralList<TOrder> findAllByProperty(int offset, int pagesize,
                                                 String propertyName, String queryval) {
        GeneralList<TOrder> orderlist = new GeneralList<>();
        try {
            Session session = getHibernateTemplate().getSessionFactory()
                    .openSession();
            Criteria criteria = session.createCriteria(TOrder.class);
            if (propertyName != null && !propertyName.equals("all")) {
                if (queryval != null && !queryval.isEmpty()) {
                    criteria.add(Restrictions.eq(propertyName, queryval));
                } else {
                    if ("waitpayment".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.WAITPAYMENT.getName()));
                    }
                    if ("waitconfirm".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.WAITCONFIRM.getName()));
                    }
                    if ("packaging".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.ADMEASUREPRODUCT.getName()));
                    }
                    if ("waitdeliver".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.WAITDELIVER.getName()));
                    }
                    if ("cancel".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.CANCEL.getName()));
                    }
                    if ("delivered".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.DELIVERED.getName()));
                    }
                    if ("received".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.RECEIVED.getName()));
                    }
                    if ("refunding".equals(propertyName)) {
                        criteria.add(Restrictions.eq("orderstate", OrderState.REFUNDING.getName()));
                    }
                }
            }
            getTotal(orderlist, criteria, pagesize);
            criteria.setFirstResult((offset - 1) * pagesize);
            criteria.setMaxResults(pagesize);
            criteria.addOrder(Order.desc("orderdate"));
            orderlist.setList(criteria.list());
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return orderlist;
    }

    public TOrder merge(TOrder detachedInstance) {
        log.debug("merging TOrder instance");
        try {
            TOrder result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TOrder instance) {
        log.debug("attaching dirty TOrder instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(TOrder instance) {
        log.debug("attaching clean TOrder instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Override
    public List findOrderByTrade_no(String trade_no) {
        String queryString = "from TOrder as model where model.trade_no= ?";
        return getHibernateTemplate().find(queryString, trade_no);
    }

    @Override
    public int updateTorder(int id, String columnName, String parameter) {
        Session session = getHibernateTemplate().getSessionFactory()
                .openSession();
        String sql = "update t_order set " + columnName + "=? where id=?";
        NativeQuery query = session.createNativeQuery(sql);
        query.setParameter(1, parameter);
        query.setParameter(2, id);
        int i = query.executeUpdate();
        session.close();
        return i;
    }
}