package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.StoreStockDAO;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.StoreStockInfo;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.service.StoreStockSvc;

import java.util.List;

public class StoreStockSvcImpl implements StoreStockSvc {
    private StoreStockDAO storeStockDAO;

    public StoreStockDAO getStoreStockDAO() {
        return storeStockDAO;
    }

    public void setStoreStockDAO(StoreStockDAO storeStockDAO) {
        this.storeStockDAO = storeStockDAO;
    }

    @Override
    public StoreStockInfo getStoreStockByStoreId(int storeId, int goodsId, String color) {
        return storeStockDAO.getStoreStockByStoreId(storeId, goodsId, color);
    }

    @Override
    public void saveStoreStockInfo(StoreStockInfo storeStockInfo) throws Exception {
        storeStockDAO.saveStoreStockInfo(storeStockInfo);
    }

    @Override
    public GeneralList<StoreStockInfo> getStoreStockByStoreId(int storeId, int indexPage, int pageSize) {
        return storeStockDAO.getStoreStockByStoreId(storeId,indexPage,pageSize);
    }

    @Override
    public List getRunBrands(int storeId) {
        return storeStockDAO.getRunBrands(storeId);
    }
}
