package cn.iliker.mall.dao;

import java.util.List;

import cn.iliker.mall.entity.Clothestype;
import cn.iliker.mall.error.MyError;

public interface IClothesType {

    List<Object[]> findAllType();

    void save(Clothestype transientInstance) throws MyError;

    void delete(Clothestype persistentInstance);

    Clothestype findById(java.lang.Integer id);

    List findAll();

    List findAll(int crowdid);
}