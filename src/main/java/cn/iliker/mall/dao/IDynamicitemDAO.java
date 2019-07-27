package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.Dynamicitem;

public interface IDynamicitemDAO {
    void save(Dynamicitem transientInstance);

    List findAll();
}
