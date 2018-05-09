package cn.iliker.mall.service;

import cn.iliker.mall.entity.Shippingaddress;

import java.util.List;

public interface IDeliverInfoSvc {
    void save(Shippingaddress transientInstance);

    void delete(Shippingaddress persistentInstance);

    List findByProperty(String propertyName, Object value);

    void attachDirty(Shippingaddress instance) throws RuntimeException;

}
