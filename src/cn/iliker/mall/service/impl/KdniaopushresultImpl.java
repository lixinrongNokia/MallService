package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IKdniaopushresultDAO;
import cn.iliker.mall.entity.KdniaoPushResult;
import cn.iliker.mall.service.IKdniaopushresultSvc;

import java.util.List;

public class KdniaopushresultImpl implements IKdniaopushresultSvc {
    private IKdniaopushresultDAO kdniaopushresultDAO;

    public void setKdniaopushresultDAO(IKdniaopushresultDAO kdniaopushresultDAO) {
        this.kdniaopushresultDAO = kdniaopushresultDAO;
    }

    @Override
    public void save(KdniaoPushResult transientInstance) {
        kdniaopushresultDAO.save(transientInstance);
    }

    @Override
    public KdniaoPushResult findById(Integer id) {
        return kdniaopushresultDAO.findById(id);
    }

    @Override
    public List findByCount(Object count) {
        return kdniaopushresultDAO.findByCount(count);
    }

    @Override
    public List findAll() {
        return kdniaopushresultDAO.findAll();
    }

    @Override
    public KdniaoPushResult merge(KdniaoPushResult detachedInstance) {
        return kdniaopushresultDAO.merge(detachedInstance);
    }
}
