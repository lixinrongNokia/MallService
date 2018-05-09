package cn.iliker.mall.service.impl;


import cn.iliker.mall.dao.IadminManagerDAO;
import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.error.ExistsError;
import cn.iliker.mall.error.MyError;
import cn.iliker.mall.service.IadminManagerSvc;

import java.util.List;

public class AdminImpl implements IadminManagerSvc {
    private IadminManagerDAO dao;


    public void setDao(IadminManagerDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(Adminmanager transientInstance) throws ExistsError, MyError {
        dao.save(transientInstance);
    }

    @Override
    public void delete(Adminmanager persistentInstance) {
        dao.delete(persistentInstance);
    }

    @Override
    public Adminmanager findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Adminmanager findByNickname(String nickname) {
        return dao.findByNickname(nickname);
    }

    @Override
    public Adminmanager loginSvc(String nickname, String pwd) {
        Adminmanager admin = this.findByNickname(nickname);
        if (admin != null && pwd.equals(admin.getPassword())) {
            return admin;
        }
        return null;
    }

    @Override
    public List<Adminmanager> findAll() {
        return dao.findAll();
    }

    @Override
    public boolean update(Adminmanager persistentInstance) {
        return dao.update(persistentInstance);
    }

}
