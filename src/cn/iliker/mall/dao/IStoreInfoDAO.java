package cn.iliker.mall.dao;

import cn.iliker.mall.entity.StoreInfo;
import cn.iliker.mall.entity.StoreStockInfo;
import com.alibaba.fastjson.JSONArray;

import javax.mail.Store;
import java.util.List;

public interface IStoreInfoDAO {
    StoreInfo findById(int id);

    List findByProperty(String propertyName, Object value);

    void attachDirty(StoreInfo storeInfo) throws Exception;

    /**
     *获取附近门店
     * @param latitude 参照物纬度
     * @param longitude 参照物经度
     * @param dis 距离(千米)
     * @return 门店列表
     */
    JSONArray getNearStores(double latitude, double longitude, int dis);

}
