package cn.iliker.mall.dao;

import cn.iliker.mall.entity.Collection;
import cn.iliker.mall.entity.GeneralList;

public interface ICollectionDAO {
    GeneralList<Collection> findByUserId(Integer id, int offset, int pageCount);
}
