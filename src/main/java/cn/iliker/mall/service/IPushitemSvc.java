package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.PushItem;

public interface IPushitemSvc {
    void save(PushItem transientInstance);

    PushItem findById(Integer id);

    List findAll();

    PushItem merge(PushItem detachedInstance);

    void attachDirty(PushItem instance);
}
