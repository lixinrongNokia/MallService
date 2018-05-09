package cn.iliker.mall.service;

import cn.iliker.mall.entity.TransferRecord;

import java.util.List;

public interface ITransferRecordSvc {

    void save(TransferRecord transientInstance);

    List findByBatchNo(Object batchNo);

    List findByBatchNum(Object batchNum);

    List findAll();

}