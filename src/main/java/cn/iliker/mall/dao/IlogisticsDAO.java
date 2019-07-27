package cn.iliker.mall.dao;

import java.util.List;

import cn.iliker.mall.entity.Logistics;

public interface IlogisticsDAO {


    void delete(Logistics persistentInstance);

    Logistics findById(java.lang.Integer id);

    List findAll();

    void add(Logistics logistics) throws RuntimeException;

    List findByProperty(String propertyName, Object value);

}