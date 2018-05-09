package cn.iliker.mall.service;

import cn.iliker.mall.entity.KdniaoPushResult;

import java.util.List;

public interface IKdniaopushresultSvc {
    void save(KdniaoPushResult transientInstance);

    KdniaoPushResult findById(Integer id);

    List findByCount(Object count);

    List findAll();

    KdniaoPushResult merge(KdniaoPushResult detachedInstance);
}
