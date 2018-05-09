package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IGoods;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Goods;
import cn.iliker.mall.entity.StockInfo;
import cn.iliker.mall.error.DelError;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.util.TextUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class GoodsDAO extends HibernateDaoSupport implements IGoods {
    private static final Logger log = LoggerFactory.getLogger(GoodsDAO.class);
    private static final String GOOD_CODE = "goodCode";

    /*批量添加商品*/
    @Override
    public void addBuyBean(List<Goods> list) throws SQLException {
        Connection connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("INSERT into goods (goodCode,goodName,clothestypeid,goodsDesc,price,imgpath,illustrations,market_date,brandid,divided_into,colors,sizes,stock) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ");
            for (Goods goods : list) {
                preparedStatement.setString(1, goods.getGoodCode());
                preparedStatement.setString(2, goods.getGoodName());
                preparedStatement.setInt(3, goods.getClothestype().getId());
                preparedStatement.setString(4, goods.getGoodsDesc());
                preparedStatement.setDouble(5, goods.getPrice());
                preparedStatement.setString(6, goods.getIllustrations().split("#")[0]);
                preparedStatement.setString(7, goods.getIllustrations());
                preparedStatement.setString(8, goods.getMarketDate());
                preparedStatement.setInt(9, goods.getBrand().getBrandId());
                preparedStatement.setDouble(10, goods.getDivided_into());
                preparedStatement.setString(11, goods.getColors());
                preparedStatement.setString(12, goods.getSizes());
                preparedStatement.setInt(13, goods.getStock());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw e;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.clearParameters();
                    preparedStatement.close();
                }
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean save(Goods transientInstance) {
        Session session = getHibernateTemplate().getSessionFactory()
                .openSession();
        try {
            session.save(transientInstance);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    @Override
    public void delete(Goods goods) throws DelError {
        try {
            getHibernateTemplate().delete(goods);
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DelError("清理库存订单再删除");
        }
    }

    @Override
    public Goods findById(java.lang.Integer id) {
        log.debug("getting Goods instance with id: " + id);
        try {
            return getHibernateTemplate().get(Goods.class, id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Goods instance) {
        log.debug("finding Goods instance by example");
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
        log.debug("finding Goods instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Goods as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findByGoodCode(Object goodCode) {
        return findByProperty(GOOD_CODE, goodCode);
    }

    private static void getTotal(GeneralList<Goods> orderlist, Criteria criteria, int pagesize) {
        List list = criteria.list();
        int totalsize = list.size();
        if (totalsize > 0) {
            int totalPage = totalsize % pagesize == 0 ? totalsize / pagesize
                    : totalsize / pagesize + 1;
            orderlist.setTotalSize(totalsize);
            orderlist.setTotalPage(totalPage);
        }
    }

    @Override
    public GeneralList<Goods> findAll(int offset, int pagesize, String search_where, String search_value) {
        GeneralList<Goods> goodlist = new GeneralList<>();
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        try {
            Criteria criteria = session.createCriteria(Goods.class, "goods");
            if (search_where != null && search_value != null) {
                switch (search_where) {
                    case "goods.brand.brandId":
                    case "goods.clothestype.id":
                        criteria.add(Restrictions.eq(search_where, Integer.parseInt(search_value)));
                        break;
                    case "goods.goodCode":
                        criteria.add(Restrictions.eq(search_where, search_value));
                        break;
                    case "goods.price":
                        String[] strings = search_value.split("-");
                        criteria.add(Restrictions.between("price", Double.parseDouble(strings[0]), Double.parseDouble(strings[1])));
                        break;
                }

            }
            getTotal(goodlist, criteria, pagesize);
            if (goodlist.getTotalSize() == 0) {
                return null;
            }
            criteria.setFirstResult((offset - 1) * pagesize);
            criteria.setMaxResults(pagesize);
            criteria.addOrder(Order.desc("goods.marketDate"));
            goodlist.setList(criteria.list());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goodlist;
    }

    public Goods merge(Goods detachedInstance) {
        log.debug("merging Goods instance");
        try {
            Goods result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(Goods instance) {
        log.debug("attaching dirty Goods instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(Goods instance) {
        log.debug("attaching clean Goods instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Override
    public boolean updateGood(Goods good) {
        try {
            getHibernateTemplate().merge(good);
            return true;
        } catch (HibernateException e) {
            return false;
        }

    }

    @Override
    public int editDivided_into(String divided_into, int goodid) {
        try {
            Goods goods = getHibernateTemplate().get(Goods.class, goodid);
            goods.setDivided_into(Double.parseDouble(divided_into));
            getHibernateTemplate().saveOrUpdate(goods);
            return 1;
        } catch (DataAccessException e) {
            return 0;
        }
    }

    @Override
    public JSONArray loadLimit(String entityName, String[] columns, String where, Object[] selectionArgs,
                               String groupBy, String having, String orderBy) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        JSONArray jsonArray = null;
        try {
            String sql = buildQueryString(entityName, columns, where, groupBy, having, orderBy);
            Query query = session.createQuery(sql);
            if (selectionArgs != null) {
                for (int i = 0; i < selectionArgs.length; i++) {
                    query.setParameter(i, selectionArgs[i]);
                }
            }
            List<Object[]> list;
            if (orderBy != null) {
                list = query.setFirstResult(1).setMaxResults(3).list();
            } else {
                list = query.list();
            }
            if (!list.isEmpty()) {
                jsonArray = new JSONArray();
                for (int i = 0; i < list.size(); i++) {
                    Object[] objects = list.get(i);
                    JSONObject jsonObject = new JSONObject();
                    for (int j = 0; j < columns.length; j++) {
                        jsonObject.put("" + columns[j] + "", objects[j]);
                    }
                    jsonArray.add(i, jsonObject);
                }
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return jsonArray;
    }

    @Override
    public void updateStockInfo(StockInfo stockInfo) {
        try {
            getHibernateTemplate().saveOrUpdate(stockInfo);
        } catch (RuntimeException e) {
            throw e;
        }
    }

    @Override
    public Goods findGoodsByGoodsCode(Goods goods) {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Query query = session.createQuery("FROM Goods g where g.brand.brandId=" + goods.getBrand().getBrandId() + "and g.goodCode='" + goods.getGoodCode() + "' and g.visible=1");
        return (Goods) query.uniqueResult();
    }

    private static String buildQueryString(String tables, String[] columns, String where,
                                           String groupBy, String having, String orderBy) {
        if (TextUtils.isEmpty(groupBy) && !TextUtils.isEmpty(having)) {
            throw new IllegalArgumentException(
                    "HAVING clauses are only permitted when using a groupBy clause");
        }
        StringBuilder query = new StringBuilder(120);

        query.append("SELECT ");
        if (columns != null && columns.length != 0) {
            appendColumns(query, columns);
        } else {
            query.append("* ");
        }
        query.append("FROM ");
        query.append(tables);
        appendClause(query, " WHERE ", where);
        appendClause(query, " GROUP BY ", groupBy);
        appendClause(query, " HAVING ", having);
        appendClause(query, " ORDER BY ", orderBy);
        return query.toString();
    }

    public static void appendColumns(StringBuilder s, String[] columns) {
        int n = columns.length;

        for (int i = 0; i < n; i++) {
            String column = columns[i];
            if (column != null) {
                if (i > 0) {
                    s.append(", ");
                }
                s.append(column);
            }
        }
        s.append(' ');
    }


    private static void appendClause(StringBuilder s, String name, String clause) {
        if (!TextUtils.isEmpty(clause)) {
            s.append(name);
            s.append(clause);
        }
    }

}