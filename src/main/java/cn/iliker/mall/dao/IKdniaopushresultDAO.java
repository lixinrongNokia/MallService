package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.KdniaoPushResult;

public interface IKdniaopushresultDAO {
    void save(KdniaoPushResult transientInstance);

    KdniaoPushResult findById(Integer id);

    List findByCount(Object count);

    List findAll();

    KdniaoPushResult merge(KdniaoPushResult detachedInstance);
}
