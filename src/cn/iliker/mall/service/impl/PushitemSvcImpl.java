package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IPushitemDAO;
import cn.iliker.mall.entity.PushItem;
import cn.iliker.mall.service.IPushitemSvc;

import java.util.List;

public class PushitemSvcImpl implements IPushitemSvc {
    private IPushitemDAO pushitemDAO;

    public void setPushitemDAO(IPushitemDAO pushitemDAO) {
        this.pushitemDAO = pushitemDAO;
    }

    @Override
    public void save(PushItem transientInstance) {
        pushitemDAO.save(transientInstance);
    }

    @Override
    public PushItem findById(Integer id) {
        return pushitemDAO.findById(id);
    }

    @Override
    public List findAll() {
        return pushitemDAO.findAll();
    }

    @Override
    public PushItem merge(PushItem detachedInstance) {
        return pushitemDAO.merge(detachedInstance);
    }

    @Override
    public void attachDirty(PushItem instance) {
        pushitemDAO.attachDirty(instance);
    }
}
