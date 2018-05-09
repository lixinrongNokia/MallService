package cn.iliker.mall.service;


import cn.iliker.mall.entity.FlashSale;

import java.util.List;

public interface IFlashsaleSvc {
    void save(FlashSale transientInstance);

    void delete(FlashSale persistentInstance);

    FlashSale findById(Integer id);

    List findByProperty(String propertyName, Object value);

    List findAll();
}