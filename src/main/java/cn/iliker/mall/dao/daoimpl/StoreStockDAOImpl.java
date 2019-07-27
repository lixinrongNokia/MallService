package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.StoreStockDAO;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.StoreStockInfo;

public class StoreStockDAOImpl extends HibernateDaoSupport implements StoreStockDAO {

    @Override
    public StoreStockInfo getStoreStockByStoreId(int storeId, int goodsId, String color) {
        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.createQuery("select ssi from StoreStockInfo ssi where ssi.storeInfo.id=? and ssi.goods.id=? and ssi.color=?");
            query.setParameter(0, storeId);
            query.setParameter(1, goodsId);
            query.setParameter(2, color);
            return (StoreStockInfo) query.uniqueResult();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public GeneralList<StoreStockInfo> getStoreStockByStoreId(int storeId, int indexPage, int pageSize) {
        Session session = null;
        GeneralList<StoreStockInfo> infoGeneralList = new GeneralList<>();
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.createQuery("select ssi from StoreStockInfo ssi where ssi.storeInfo.id=?");
            query.setParameter(0, storeId);
            int totalSize = query.list().size();
            if (totalSize > 0) {
                int totalPage = totalSize % pageSize == 0 ? totalSize / pageSize
                        : totalSize / pageSize + 1;
                infoGeneralList.setTotalPage(totalPage);
                infoGeneralList.setTotalSize(totalSize);
                query.setFirstResult((indexPage - 1) * pageSize);
                query.setMaxResults(pageSize);
                infoGeneralList.setList(query.list());
                return infoGeneralList;
            }
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveStoreStockInfo(StoreStockInfo storeStockInfo) throws Exception {
        try {
            getHibernateTemplate().saveOrUpdate(storeStockInfo);
        } catch (DataAccessException e) {
            throw e;
        }
    }

    @Override
    public List getRunBrands(int storeId) {
        Session session = getSessionFactory().openSession();
        NativeQuery nativeQuery = session.createNativeQuery("SELECT brand.brandid,brand.brandName FROM store_stockinfo ss,goods,brand where ss.goodsId=goods.id AND goods.brandid=brand.brandid AND  ss.storeId=:storeId GROUP BY brand.brandName");
        nativeQuery.setParameter("storeId", storeId);
        return nativeQuery.list();
    }
}
