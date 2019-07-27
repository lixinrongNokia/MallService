package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.TransferRecord;

public interface ITransferRecordDAO {

    void save(TransferRecord transientInstance);

    List findByBatchNo(Object batchNo);

    List findByBatchNum(Object batchNum);

    List findAll();

}