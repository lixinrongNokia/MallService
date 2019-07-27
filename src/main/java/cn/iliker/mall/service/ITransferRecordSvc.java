package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.TransferRecord;

public interface ITransferRecordSvc {

    void save(TransferRecord transientInstance);

    List findByBatchNo(Object batchNo);

    List findByBatchNum(Object batchNum);

    List findAll();

}