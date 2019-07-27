package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.CourierCompany;

public interface ICourierDAO {

    void save(CourierCompany transientInstance);

    void delete(CourierCompany persistentInstance);

    CourierCompany findById(java.lang.Integer id);

    List findAll();

    CourierCompany merge(CourierCompany detachedInstance);

}