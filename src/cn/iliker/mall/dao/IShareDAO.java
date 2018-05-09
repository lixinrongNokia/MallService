package cn.iliker.mall.dao;

import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Share;

public interface IShareDAO {

    void save(Share transientInstance) throws RuntimeException;

    void delete(Share persistentInstance);

    GeneralList<Share> findAll(int offset, int pagesize, int node);

    Share findById(Integer shareId);

    boolean delShare(Integer shareId);

//    Share findShare(Integer shareId);

}