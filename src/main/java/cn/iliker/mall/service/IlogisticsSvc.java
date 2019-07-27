package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.Logistics;
import cn.iliker.mall.entity.TOrder;

public interface IlogisticsSvc {


    void delete(Logistics persistentInstance);

    Logistics findById(java.lang.Integer id);

    List findAll();

    void add(Logistics logistics,TOrder tOrder) throws RuntimeException;

    List findByProperty(String propertyName, Object value);
}