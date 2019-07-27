package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.Shippingaddress;

public interface IDeliverInfoDAO {
    void save(Shippingaddress transientInstance);

    void delete(Shippingaddress persistentInstance);

    List findByProperty(String propertyName, Object value);

    void attachDirty(Shippingaddress instance) throws RuntimeException;

}
