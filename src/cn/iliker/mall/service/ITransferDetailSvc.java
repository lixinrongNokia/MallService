package cn.iliker.mall.service;

import cn.iliker.mall.entity.TransferDetail;

import java.util.List;

public interface ITransferDetailSvc {
    void attachDirty(TransferDetail transferDetail);

    List findByStatTag(Object statTag);

    List findByBatchNo(String batchNo);

    void batchUpdate(List<TransferDetail> transferDetails) throws Exception;

}
