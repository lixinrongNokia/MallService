package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.Dynamicitem;

public interface IDynamicitemSvc {
    void save(Dynamicitem transientInstance);

    List findAll();
}
