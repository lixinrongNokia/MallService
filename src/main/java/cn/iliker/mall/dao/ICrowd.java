package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.Crowd;

public interface ICrowd {

    void save(Crowd transientInstance);

    void delete(Crowd persistentInstance);

    Crowd findById(java.lang.Integer id);

    List findByName(Object name);

    List findAll();

    Crowd merge(Crowd detachedInstance);

}