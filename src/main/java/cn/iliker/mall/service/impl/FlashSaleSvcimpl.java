package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.IFlashsaleDAO;
import cn.iliker.mall.entity.FlashSale;
import cn.iliker.mall.service.IFlashsaleSvc;

public class FlashSaleSvcimpl implements IFlashsaleSvc {
    private IFlashsaleDAO flashsaleDAO;

    public void setFlashsaleDAO(IFlashsaleDAO flashsaleDAO) {
        this.flashsaleDAO = flashsaleDAO;
    }

    @Override
    public void save(FlashSale transientInstance) {
        flashsaleDAO.save(transientInstance);
    }

    @Override
    public void delete(FlashSale persistentInstance) {
        flashsaleDAO.delete(persistentInstance);
    }

    @Override
    public FlashSale findById(Integer id) {
        return flashsaleDAO.findById(id);
    }

    @Override
    public List findByProperty(String propertyName, Object value) {
        return flashsaleDAO.findByProperty(propertyName, value);
    }

    @Override
    public List findAll() {
        return flashsaleDAO.findAll();
    }
}
