package cn.iliker.mall.service;


import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cn.iliker.mall.entity.StoreInfo;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.UnpackOrder;
import cn.iliker.mall.entity.UnpackOrderId;

public interface IStoreManagerService {
    StoreInfo findById(int id);

    List findByProperty(String propertyName, Object value);

    void updateStoreInfo(StoreInfo storeInfo) throws Exception;
    JSONArray getNearStores(double latitude, double longitude, int dis);

    void saveUnpackOrder(UnpackOrder unpackOrder) throws Exception;

    List getUnpackOrders();

    UnpackOrder find(UnpackOrderId unpackOrderId);

    void confirmReceived(TOrder tOrder, UnpackOrder foundUnpackOrder) throws Exception;

    List<UnpackOrder> pageGetUnpackOrder(int storeId,int pageIndex,int pageCount);
}
