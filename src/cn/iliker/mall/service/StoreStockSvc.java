package cn.iliker.mall.service;

import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.StoreStockInfo;

import java.util.List;

public interface StoreStockSvc {
    StoreStockInfo getStoreStockByStoreId(int storeId, int goodsId, String color);

    void saveStoreStockInfo(StoreStockInfo storeStockInfo) throws Exception;

    GeneralList<StoreStockInfo> getStoreStockByStoreId(int storeId, int indexPage, int pageSize);


    List getRunBrands(int storeId);
}
