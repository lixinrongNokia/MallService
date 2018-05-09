package cn.iliker.mall.service;

import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.TOrder;

import java.util.List;

public interface ITOrderSvc {

    List findAll();

    List findOrderByTrade_no(String trade_no);

    int updateTorder(int id, String columnName, String parameter);

    List findByOrderid(String orderid) throws Exception;

    GeneralList<TOrder> findAllByProperty(int offset, int pagesize,
                                          String propertyName, String queryval);

    void save(TOrder transientInstance) throws RuntimeException;

    TOrder findById(Integer id);
}