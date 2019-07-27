package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.IDeliverInfoDAO;
import cn.iliker.mall.entity.Shippingaddress;
import cn.iliker.mall.service.IDeliverInfoSvc;

public class DeliverInfoSvcImpl implements IDeliverInfoSvc {
    private IDeliverInfoDAO infoDAO;

    public void setInfoDAO(IDeliverInfoDAO infoDAO) {
        this.infoDAO = infoDAO;
    }

    @Override
    public void save(Shippingaddress transientInstance) {
        infoDAO.save(transientInstance);
    }

    @Override
    public void delete(Shippingaddress persistentInstance) {
        infoDAO.delete(persistentInstance);
    }

    @Override
    public List findByProperty(String propertyName, Object value) {
        return infoDAO.findByProperty(propertyName, value);
    }

    @Override
    public void attachDirty(Shippingaddress instance) throws RuntimeException{
        infoDAO.attachDirty(instance);
    }
}
