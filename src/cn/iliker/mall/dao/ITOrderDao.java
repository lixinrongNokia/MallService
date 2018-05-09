package cn.iliker.mall.dao;

import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.TOrder;

import java.util.List;

public interface ITOrderDao {

    List findByOrderid(String orderid) throws Exception;

    List findAll();

    List findOrderByTrade_no(String trade_no);

    int updateTorder(int id, String columnName, String parameter);

    GeneralList<TOrder> findAllByProperty(int offset, int pagesize,
                                          String propertyName, String queryval);

    void save(TOrder transientInstance) throws RuntimeException;

    TOrder findById(Integer id);

}