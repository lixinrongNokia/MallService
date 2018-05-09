package cn.iliker.mall.service;

import cn.iliker.mall.entity.CourierCompany;

import java.util.List;

public interface ICourierSvc {

    void save(CourierCompany transientInstance);

    void delete(CourierCompany persistentInstance);

    CourierCompany findById(java.lang.Integer id);

    List findAll();

    CourierCompany merge(CourierCompany detachedInstance);

}