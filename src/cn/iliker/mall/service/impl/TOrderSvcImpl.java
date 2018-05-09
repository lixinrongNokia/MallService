package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.ITOrderDao;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.service.ITOrderSvc;

import java.util.List;

public class TOrderSvcImpl implements ITOrderSvc {
    private ITOrderDao dao;

    public void setDao(ITOrderDao dao) {
        this.dao = dao;
    }

    @Override
    public List findAll() {
        return dao.findAll();
    }

    @Override
    public List findOrderByTrade_no(String trade_no) {
        return dao.findOrderByTrade_no(trade_no);
    }

    @Override
    public int updateTorder(int id, String columnName, String parameter) {
        return dao.updateTorder(id, columnName, parameter);
    }

    @Override
    public List findByOrderid(String orderid) throws Exception{
        return dao.findByOrderid(orderid);
    }

    @Override
    public GeneralList<TOrder> findAllByProperty(int offset, int pagesize,
                                                 String propertyName, String queryval) {
        return dao.findAllByProperty(offset, pagesize, propertyName, queryval);
    }

    @Override
    public TOrder findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public void save(TOrder transientInstance) throws RuntimeException{
        dao.save(transientInstance);
    }
}
