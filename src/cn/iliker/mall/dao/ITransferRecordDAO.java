package cn.iliker.mall.dao;


import cn.iliker.mall.entity.TransferRecord;

import java.util.List;

public interface ITransferRecordDAO {

    void save(TransferRecord transientInstance);

    List findByBatchNo(Object batchNo);

    List findByBatchNum(Object batchNum);

    List findAll();

}