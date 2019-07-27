package cn.iliker.mall.dao.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.iliker.mall.dao.IUserinfoDAO;
import cn.iliker.mall.entity.ProtohuMan;
import cn.iliker.mall.entity.Userdata;
import cn.iliker.mall.entity.Userinfo;

public class UserinfoDAO extends HibernateDaoSupport implements IUserinfoDAO {
    private static final Logger log = LoggerFactory
            .getLogger(UserinfoDAO.class);
    // property constants
    private static final String REALNAME = "realname";
    private static final String NICKNAME = "nickname";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String PHONE = "phone";
    private static final String SEX = "sex";
    private static final String HEADIMG = "headimg";
    private static final String SIGNATURE = "signature";
    private static final String BIRTHDAY = "birthday";
    private static final String UID = "uid";

    protected void initDao() {
        // do nothing
    }

    @Override
    public int save(Userinfo transientInstance) throws RuntimeException {
        log.debug("saving Userinfo instance");
        try {
            log.debug("save successful");
            return (int) getHibernateTemplate().save(transientInstance);
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void update(Userinfo transientInstance) throws RuntimeException {
        log.debug("saving Userinfo instance");
        try {
            getHibernateTemplate().saveOrUpdate(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    @Override
    public void delete(Userinfo persistentInstance) {
        log.debug("deleting Userinfo instance");
        try {
            getHibernateTemplate().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    @Override
    public Userinfo findById(java.lang.Integer id) {
        log.debug("getting Userinfo instance with id: " + id);
        try {
            return (Userinfo) getHibernateTemplate().get(
                    "cn.iliker.mall.entity.Userinfo", id);
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(Userinfo instance) {
        log.debug("finding Userinfo instance by example");
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

    @Override
    public List findByProperty(String propertyName, Object value) throws RuntimeException {
        log.debug("finding Userinfo instance with property: " + propertyName
                + ", value: " + value);
        try {
            String queryString = "from Userinfo as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    @Override
    public Userinfo findByPhone(Object phone) {
        List<Userinfo> list = findByProperty(PHONE, phone);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List findAll() {
        log.debug("finding all Userinfo instances");
        try {
            String queryString = "from Userinfo";
            return getHibernateTemplate().find(queryString);
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public Userinfo merge(Userinfo detachedInstance) {
        log.debug("merging Userinfo instance");
        try {
            Userinfo result = getHibernateTemplate().merge(
                    detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachClean(Userinfo instance) {
        log.debug("attaching clean Userinfo instance");
        try {
            getHibernateTemplate().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    @Override
    public Userinfo findByNickName(String nickName) {
        try {
            List list = findByProperty("nickname", nickName);
            if (!list.isEmpty()) {
                return (Userinfo) list.get(0);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Userdata> findByUId(int uid) {
        Userinfo result = (Userinfo) this.findByProperty(UID, uid).get(0);
        return result.getUserdatas();
    }

    @Override
    public List<Userinfo> getAllEmail() {
        List<Userinfo> userinfos = null;
        String sql = "SELECT u.nickname,u.email from Userinfo u where u.email is not null";
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        List<Object[]> list = session.createQuery(sql).list();
        if (list != null && list.size() > 0) {
            userinfos = new ArrayList<>();
            for (Object[] object : list) {
                Userinfo user = new Userinfo(String.valueOf(object[0]), String.valueOf(object[1]));
                userinfos.add(user);
            }
        }
        session.clear();
        session.close();
        return userinfos;
    }

    @Override
    public Userinfo faceLogin(String gId) {
        List<Userinfo> list = findByProperty("gid", gId);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public void batchAddUser(List<ProtohuMan> list) throws SQLException {
        Connection connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT into userinfo (realname,nickname,password,phone,enable,guestNO) VALUES (?,?,?,?,0,?) ");
            connection.setAutoCommit(false);
            for (ProtohuMan protohuMan : list) {
                preparedStatement.setString(1, protohuMan.getRealName());
                preparedStatement.setString(2, protohuMan.getNickName());
                preparedStatement.setString(3, protohuMan.getPassword());
                preparedStatement.setString(4, protohuMan.getPhone());
                preparedStatement.setString(5, protohuMan.getCardId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
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
    public List<Userinfo> getSubclassesByUid(int uid) throws RuntimeException {
        return findByProperty("parent.uid", uid);
    }

    @Override
    public void updateLevel(Userinfo userinfo) {
        Session session = getSessionFactory().openSession();
        String sql;
        Integer count;
        int TotalSpending = (int) userinfo.getWallet().getTotalSpending().doubleValue();
        switch (userinfo.getLevel()) {
            case 0:
                sql = "select count(*) from userinfo where parentid=" + userinfo.getUid() + " and `level`=0";
                count = Integer.parseInt(String.valueOf(session.createNativeQuery(sql).uniqueResult()));
                if (TotalSpending >= 380 && count >= 5) {
                    userinfo.setLevel(1);
                }
                break;
            case 1:
                sql = "select count(*) from userinfo where parentid=" + userinfo.getUid() + " and `level`=1";
                count = Integer.parseInt(String.valueOf(session.createNativeQuery(sql).uniqueResult()));
                if (TotalSpending >= 680 && count >= 10) {
                    userinfo.setLevel(2);
                }
                break;
            case 2:
                sql = "select count(*) from userinfo where parentid=" + userinfo.getUid() + " and `level`=2";
                count = Integer.parseInt(String.valueOf(session.createNativeQuery(sql).uniqueResult()));
                if (TotalSpending >= 1280 && count >= 20) {
                    userinfo.setLevel(3);
                }
                break;
            case 3:
                sql = "select count(*) from userinfo where parentid=" + userinfo.getUid() + " and `level`=3";
                count = Integer.parseInt(String.valueOf(session.createNativeQuery(sql).uniqueResult()));
                if (TotalSpending >= 3680 && count >= 50) {
                    userinfo.setLevel(4);
                }
                break;
        }
        this.update(userinfo);
    }

    @Override
    public void saveSideData(Userdata userdata) throws RuntimeException {
        getHibernateTemplate().save(userdata);
    }

    @Override
    public Userdata findBodyByUID(int uid) {
        try (Session session = getSessionFactory().openSession()) {
            //SELECT * from userdata as model where model.uid=12  order by model.syntime desc LIMIT 1
            NativeQuery query = session.createNativeQuery("select model.height,model.onchest,model.underchest,model.waist,model.hip,model.weight from userdata as model where model.uid=:uid  order by model.syntime desc LIMIT 1");
            query.setParameter("uid", uid);
            Object[] objects = (Object[]) query.uniqueResult();
            return new Userdata((Double) objects[0], (Double) objects[1], (Double) objects[2], (Double) objects[3],(Double) objects[4], (String) objects[5]);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        /*try (Session session = getSessionFactory().openSession()) {
            Criteria criteria = session.createCriteria(Userdata.class, "userData");
            criteria.add(Restrictions.eq("userData.userinfo.uid", uid));
            criteria.setProjection(Projections.projectionList().add(Projections.max("syntime")));
            System.out.println("========================="+criteria.uniqueResult());
        } catch (HibernateException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}