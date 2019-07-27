package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.FlashSale;

public interface IFlashsaleDAO {
    void save(FlashSale transientInstance);

    void delete(FlashSale persistentInstance);

    FlashSale findById(Integer id);

    List findByProperty(String propertyName, Object value);

    List findAll();
}
