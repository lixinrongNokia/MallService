package cn.iliker.mall.dao.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.iliker.mall.dao.IStoreInfoDAO;
import cn.iliker.mall.entity.StoreInfo;
import cn.iliker.mall.error.DelError;
import cn.iliker.mall.utils.NetSteamUtil;

public class StoreInfoDAO extends HibernateDaoSupport implements IStoreInfoDAO {

    public void delete(int id) throws DelError {
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        String sql = "delete from StoreInfo g where g.id=" + id;
        Query query = session.createQuery(sql);
        try {
            query.executeUpdate();
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new DelError("删除不成功");
        } finally {
            session.close();
        }
    }

    @Override
    public StoreInfo findById(int id) {
        try {
            return getHibernateTemplate().get(StoreInfo.class, id);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public List findByExample(StoreInfo storeInfo) {
        try {
            List results = getHibernateTemplate().findByExample(storeInfo);
            return results;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public List findByProperty(String propertyName, Object value) {
        try {
            String queryString = "from StoreInfo as model where model."
                    + propertyName + "= ?";
            return getHibernateTemplate().find(queryString, value);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public StoreInfo merge(StoreInfo storeInfo) {
        try {
            StoreInfo result = getHibernateTemplate().merge(
                    storeInfo);
            return result;
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public void attachDirty(StoreInfo storeInfo) throws Exception{
        try {
            getHibernateTemplate().saveOrUpdate(storeInfo);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    public void attachClean(StoreInfo storeInfo) {
        try {
            getHibernateTemplate().lock(storeInfo, LockMode.NONE);
        } catch (RuntimeException re) {
            throw re;
        }
    }

    @Override
    public JSONArray getNearStores(double latitude, double longitude, int dis) {
        String sql = "select id,storeName,faceIcon,latitude,longitude,tell,contacts,address,regTime from storeinfo where longitude>=? and longitude <=? and latitude>=? and latitude<=?";
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        try {
            //先计算查询点的经纬度范围
            double r = 6371;//地球半径千米
            double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(latitude * Math.PI / 180));
            dlng = dlng * 180 / Math.PI;//角度转为弧度
            double dlat = dis / r;
            dlat = dlat * 180 / Math.PI;
            double minlat = latitude - dlat;
            double maxlat = latitude + dlat;
            double minlng = longitude - dlng;
            double maxlng = longitude + dlng;
            NativeQuery query = session.createNativeQuery(sql);
            query.setParameter(1, minlng);
            query.setParameter(2, maxlng);
            query.setParameter(3, minlat);
            query.setParameter(4, maxlat);
            List<Object[]> list = query.list();
            JSONArray jsonArray = new JSONArray();
            if (!list.isEmpty()) {
                for (Object[] objects : list) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", objects[0]);
                    jsonObject.put("storeName", objects[1]);
                    jsonObject.put("faceIcon", objects[2]);
                    double vallatitude = (double) objects[3];
                    double vallongitude = (double) objects[4];
                    jsonObject.put("latitude", vallatitude);
                    jsonObject.put("longitude", vallongitude);
                    jsonObject.put("tell", objects[5]);
                    jsonObject.put("contacts", objects[6]);
                    jsonObject.put("address", objects[7]);
                    jsonObject.put("regTime", objects[8]);
                    double distance = NetSteamUtil.GetDistance(longitude, latitude,
                            vallongitude, vallatitude);
                    BigDecimal bigbmi = new BigDecimal(distance);
                    distance = bigbmi.setScale(2, BigDecimal.ROUND_HALF_UP)
                            .doubleValue();
                    jsonObject.put("distance", distance);
                    jsonArray.add(jsonObject);
                }
            }
            return jsonArray;
        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

}