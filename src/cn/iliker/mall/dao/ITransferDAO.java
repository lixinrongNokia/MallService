package cn.iliker.mall.dao;

import cn.iliker.mall.entity.Transfer;

import java.util.List;


public interface ITransferDAO {

    Transfer findById(java.lang.Integer id);

    List findByPhone(Object phone);

    List findByDealTag(Object dealTag);

    List findAll();

    void attachDirty(Transfer instance) throws RuntimeException;

}