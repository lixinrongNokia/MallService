package cn.iliker.mall.service;

import cn.iliker.mall.entity.Dynamicitem;

import java.util.List;

public interface IDynamicitemSvc {
    void save(Dynamicitem transientInstance);

    List findAll();
}
