package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.CourierCompany;

public interface ICourierSvc {

    void save(CourierCompany transientInstance);

    void delete(CourierCompany persistentInstance);

    CourierCompany findById(java.lang.Integer id);

    List findAll();

    CourierCompany merge(CourierCompany detachedInstance);

}