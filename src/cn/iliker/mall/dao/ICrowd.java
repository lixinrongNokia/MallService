package cn.iliker.mall.dao;


import cn.iliker.mall.entity.Crowd;

import java.util.List;

public interface ICrowd {

    void save(Crowd transientInstance);

    void delete(Crowd persistentInstance);

    Crowd findById(java.lang.Integer id);

    List findByName(Object name);

    List findAll();

    Crowd merge(Crowd detachedInstance);

}