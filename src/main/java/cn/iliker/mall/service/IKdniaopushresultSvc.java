package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.KdniaoPushResult;

public interface IKdniaopushresultSvc {
    void save(KdniaoPushResult transientInstance);

    KdniaoPushResult findById(Integer id);

    List findByCount(Object count);

    List findAll();

    KdniaoPushResult merge(KdniaoPushResult detachedInstance);
}
