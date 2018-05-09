package cn.iliker.mall.dao;


import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.error.ExistsError;
import cn.iliker.mall.error.MyError;

import java.util.List;

public interface IadminManagerDAO {

    void save(Adminmanager transientInstance) throws ExistsError, MyError;

    void delete(Adminmanager persistentInstance);

    Adminmanager findById(java.lang.Integer id);

    Adminmanager findByNickname(String nickname);

    List<Adminmanager> findAll();

    boolean update(Adminmanager persistentInstance);
}