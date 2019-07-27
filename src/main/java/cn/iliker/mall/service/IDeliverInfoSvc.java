package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.Shippingaddress;

public interface IDeliverInfoSvc {
    void save(Shippingaddress transientInstance);

    void delete(Shippingaddress persistentInstance);

    List findByProperty(String propertyName, Object value);

    void attachDirty(Shippingaddress instance) throws RuntimeException;

}
