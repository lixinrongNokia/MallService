package cn.iliker.mall.dao.daoimpl;

import java.util.List;

import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.ITransferRecordDAO;
import cn.iliker.mall.entity.TransferRecord;

public class TransferRecordDAO extends HibernateDaoSupport implements ITransferRecordDAO {
    private static final Logger log = LoggerFactory
            .getLogger(TransferRecordDAO.class);
    // property constants
    private static final String BATCH_FEE = "batchFee";
    private static final String BATCH_NUM = "batchNum";
    private static final String DETAIL_DATA = "detailData";
    private static final String BATCHNO = "batchNo";

    protected void initDao() {
        // do nothing
    }

    @Override
    public void save(TransferRecord transientInstance) {
        log.debug("saving TransferRecord instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TransferRecord persistentInstance) {
        log.debug("deleting TransferRecord instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public List findByExample(TransferRecord instance) {
        log.debug("finding TransferRecord instance by example");
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
        log.debug("finding TransferRecord instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from TransferRecord as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findByBatchNum(Object batchNum) {
        return findByProperty(BATCH_NUM, batchNum);
    }

    @Override
    public List findByBatchNo(Object batchNo) {
        return findByProperty(BATCHNO, batchNo);
    }

    @Override
    public List findAll() {
        log.debug("finding all TransferRecord instances");
        try {
            String queryString = "from TransferRecord";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public TransferRecord merge(TransferRecord detachedInstance) {
        log.debug("merging TransferRecord instance");
        try {
            TransferRecord result = getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(TransferRecord instance) {
        log.debug("attaching dirty TransferRecord instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(TransferRecord instance) {
        log.debug("attaching clean TransferRecord instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}