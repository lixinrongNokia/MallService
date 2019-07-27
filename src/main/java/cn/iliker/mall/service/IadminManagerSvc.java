package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.error.ExistsError;
import cn.iliker.mall.error.MyError;

public interface IadminManagerSvc {

    void save(Adminmanager transientInstance) throws ExistsError, MyError;

    void delete(Adminmanager persistentInstance);

    Adminmanager findById(java.lang.Integer id);

    Adminmanager findByNickname(String nickname);

    Adminmanager loginSvc(String nickname, String pwd);

    List<Adminmanager> findAll();

    boolean update(Adminmanager persistentInstance);
}