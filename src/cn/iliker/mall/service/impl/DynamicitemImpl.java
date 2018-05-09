package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IDynamicitemDAO;
import cn.iliker.mall.entity.Dynamicitem;
import cn.iliker.mall.service.IDynamicitemSvc;

import java.util.List;

public class DynamicitemImpl implements IDynamicitemSvc {
    private IDynamicitemDAO dynamicitemDAO;

    public void setDynamicitemDAO(IDynamicitemDAO dynamicitemDAO) {
        this.dynamicitemDAO = dynamicitemDAO;
    }

    @Override
    public void save(Dynamicitem transientInstance) {
        dynamicitemDAO.save(transientInstance);
    }

    @Override
    public List findAll() {
        return dynamicitemDAO.findAll();
    }
}
