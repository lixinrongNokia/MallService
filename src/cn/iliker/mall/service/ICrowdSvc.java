package cn.iliker.mall.service;

import cn.iliker.mall.entity.Crowd;

import java.util.List;

public interface ICrowdSvc {

    void save(Crowd transientInstance);

    void delete(Crowd persistentInstance);

    Crowd findById(java.lang.Integer id);

    List findByName(Object name);

    List findAll();

    Crowd merge(Crowd detachedInstance);

}