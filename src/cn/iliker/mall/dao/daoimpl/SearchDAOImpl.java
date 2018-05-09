package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.ISearchDAO;
import cn.iliker.mall.entity.Goods;
import cn.iliker.mall.entity.Userinfo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import java.util.List;

public class SearchDAOImpl<T> implements ISearchDAO {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<T> searchObject(int start, int pageSize, String[] fields, String matching, Class T) {
        if (start < 1) start = 1;
        if (pageSize < 1) pageSize = 1;
        if (T == Goods.class || T == Userinfo.class) {
            Session session = sessionFactory.openSession();
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            Transaction tx = fullTextSession.beginTransaction();

            QueryBuilder qb = fullTextSession.getSearchFactory()
                    .buildQueryBuilder().forEntity(T).get();
            org.apache.lucene.search.Query query = qb
                    .keyword()
                    .onFields(fields)
                    .matching(matching)
                    .createQuery();
            org.hibernate.query.Query hibQuery =
                    fullTextSession.createFullTextQuery(query, T);
            hibQuery.setFirstResult((start - 1) * pageSize);
            hibQuery.setMaxResults(pageSize);
            List<T> result = hibQuery.list();
            tx.commit();
            session.close();
            return result;
        }
        return null;
    }
}
