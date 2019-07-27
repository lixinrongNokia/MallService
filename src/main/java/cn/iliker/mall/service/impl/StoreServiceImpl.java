package cn.iliker.mall.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cn.iliker.mall.dao.IStoreInfoDAO;
import cn.iliker.mall.dao.IUnpackOrderDAO;
import cn.iliker.mall.entity.StoreInfo;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.UnpackOrder;
import cn.iliker.mall.entity.UnpackOrderId;
import cn.iliker.mall.service.IStoreManagerService;
import cn.iliker.mall.service.ITOrderSvc;

public class StoreServiceImpl implements IStoreManagerService {

    private IStoreInfoDAO storeInfoDAO;
    private IUnpackOrderDAO unpackOrderDAO;
    private ITOrderSvc orderSvc;

    public void setOrderSvc(ITOrderSvc orderSvc) {
        this.orderSvc = orderSvc;
    }

    public void setStoreInfoDAO(IStoreInfoDAO storeInfoDAO) {
        this.storeInfoDAO = storeInfoDAO;
    }

    public void setUnpackOrderDAO(IUnpackOrderDAO unpackOrderDAO) {
        this.unpackOrderDAO = unpackOrderDAO;
    }

    @Override
    public StoreInfo findById(int id) {
        return storeInfoDAO.findById(id);
    }

    @Override
    public List findByProperty(String propertyName, Object value) {
        return storeInfoDAO.findByProperty(propertyName, value);
    }

    @Override
    public void updateStoreInfo(StoreInfo storeInfo) throws Exception {
        storeInfoDAO.attachDirty(storeInfo);
    }

    @Override
    public JSONArray getNearStores(double latitude, double longitude, int dis) {
        return storeInfoDAO.getNearStores(latitude, longitude, dis);
    }

    @Override
    public void saveUnpackOrder(UnpackOrder unpackOrder) throws Exception {
        unpackOrderDAO.save(unpackOrder);
    }

    @Override
    public List getUnpackOrders() {
        return unpackOrderDAO.getUnpackOrders();
    }

    @Override
    public UnpackOrder find(UnpackOrderId unpackOrderId) {
        return unpackOrderDAO.find(unpackOrderId);
    }

    @Override
    public void confirmReceived(TOrder tOrder, UnpackOrder foundUnpackOrder) throws Exception{
        this.saveUnpackOrder(foundUnpackOrder);
        orderSvc.save(tOrder);
    }

    @Override
    public List<UnpackOrder> pageGetUnpackOrder(int storeId, int pageIndex, int pageCount) {
        return unpackOrderDAO.pageGetUnpackOrder(storeId,pageIndex,pageCount);
    }
}
