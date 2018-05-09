package cn.iliker.mall.service;

import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.Transfer;

import java.util.List;

public interface ITransferSvc {

    Transfer findById(java.lang.Integer id);

    List findByPhone(Object phone);

    List findAll();

    void addTransfer(Transfer instance) throws Exception;

    void transferAppAccount(TOrder tOrder) throws Exception;

    void refunds(TOrder order) throws Exception;
}