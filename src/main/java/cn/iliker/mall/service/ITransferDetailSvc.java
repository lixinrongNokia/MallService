package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.TransferDetail;

public interface ITransferDetailSvc {
    void attachDirty(TransferDetail transferDetail);

    List findByStatTag(Object statTag);

    List findByBatchNo(String batchNo);

    void batchUpdate(List<TransferDetail> transferDetails) throws Exception;

}
