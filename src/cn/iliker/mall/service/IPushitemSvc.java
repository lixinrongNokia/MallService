package cn.iliker.mall.service;

import cn.iliker.mall.entity.PushItem;

import java.util.List;

public interface IPushitemSvc {
    void save(PushItem transientInstance);

    PushItem findById(Integer id);

    List findAll();

    PushItem merge(PushItem detachedInstance);

    void attachDirty(PushItem instance);
}
