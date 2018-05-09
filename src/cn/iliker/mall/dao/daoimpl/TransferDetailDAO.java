package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.ITransferDetailDAO;
import cn.iliker.mall.entity.TransferDetail;
import org.hibernate.LockMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class TransferDetailDAO extends HibernateDaoSupport implements ITransferDetailDAO {
    private static final Logger log = LoggerFactory
            .getLogger(TransferDetailDAO.class);
    // property constants
    private static final String SERIAL_NO = "serialNo";
    private static final String REMARK = "remark";
    private static final String STATETAG = "stateTag";

    protected void initDao() {
        // do nothing
    }

    public void save(TransferDetail transientInstance) {
        log.debug("saving TransferDetail instance");
        try {
            getHibernateTemplate().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(TransferDetail persistentInstance) {
        log.debug("deleting TransferDetail instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public TransferDetail findById(java.lang.Integer id) {
        log.debug("getting TransferDetail instance with id: " + id);
        try {
            return (TransferDetail) getHibernateTemplate()
                    .get("cn.iliker.mall.entity.TransferDetail", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(TransferDetail instance) {
        log.debug("finding TransferDetail instance by example");
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
        log.debug("finding TransferDetail instance with property: "
                + propertyName + ", value: " + value);
        try {
            String queryString = "from TransferDetail as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public List findByBatchNo(String batchNo) {
        return findByProperty("transferRecord.batchNo", batchNo);
    }

    public List findByStatTag(Object statTag) {
        return findByProperty(STATETAG, statTag);
    }

    public List findAll() {
        log.debug("finding all TransferDetail instances");
        try {
            String queryString = "from TransferDetail";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public TransferDetail merge(TransferDetail detachedInstance) {
        log.debug("merging TransferDetail instance");
        try {
            TransferDetail result = getHibernateTemplate()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    @Override
    public void attachDirty(TransferDetail instance) {
        log.debug("attaching dirty TransferDetail instance");
        try {
            getHibernateTemplate().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Override
    public void batchUpdate(List<TransferDetail> transferDetails) throws SQLException {
        Connection connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE transfer_detail SET stateTag=?,remark=? where transferId=?");
            connection.setAutoCommit(false);
            for (TransferDetail transferDetail : transferDetails) {
                preparedStatement.setBoolean(1, transferDetail.getStateTag());
                preparedStatement.setString(2, transferDetail.getRemark());
                preparedStatement.setInt(3, transferDetail.getTransfer().getTransferId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.clearParameters();
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void attachClean(TransferDetail instance) {
        log.debug("attaching clean TransferDetail instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

}