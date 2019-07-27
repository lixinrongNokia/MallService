package cn.iliker.mall.dao;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cn.iliker.mall.entity.StoreInfo;

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
