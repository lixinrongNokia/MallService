package cn.iliker.mall.dao;


import cn.iliker.mall.entity.Dynamicitem;

import java.util.List;

public interface IDynamicitemDAO {
    void save(Dynamicitem transientInstance);

    List findAll();
}
