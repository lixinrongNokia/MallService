package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.error.ExistsError;
import cn.iliker.mall.error.MyError;

public interface IadminManagerDAO {

    void save(Adminmanager transientInstance) throws ExistsError, MyError;

    void delete(Adminmanager persistentInstance);

    Adminmanager findById(java.lang.Integer id);

    Adminmanager findByNickname(String nickname);

    List<Adminmanager> findAll();

    boolean update(Adminmanager persistentInstance);
}