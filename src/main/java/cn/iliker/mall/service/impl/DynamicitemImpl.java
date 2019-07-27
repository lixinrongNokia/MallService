package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.IDynamicitemDAO;
import cn.iliker.mall.entity.Dynamicitem;
import cn.iliker.mall.service.IDynamicitemSvc;

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
