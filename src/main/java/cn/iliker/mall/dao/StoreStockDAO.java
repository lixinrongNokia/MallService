package cn.iliker.mall.dao;

import java.util.List;

import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.StoreStockInfo;

public interface StoreStockDAO {
    StoreStockInfo getStoreStockByStoreId(int storeId,int goodsId,String color);

    GeneralList<StoreStockInfo> getStoreStockByStoreId(int storeId, int indexPage, int pageSize);

    void saveStoreStockInfo(StoreStockInfo storeStockInfo) throws Exception;

    List getRunBrands(int storeId);
}
