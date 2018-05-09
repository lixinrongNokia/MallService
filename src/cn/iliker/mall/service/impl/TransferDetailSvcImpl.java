package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.ITransferDetailDAO;
import cn.iliker.mall.entity.TransferDetail;
import cn.iliker.mall.service.ITransferDetailSvc;

import java.util.List;

public class TransferDetailSvcImpl implements ITransferDetailSvc {
    private ITransferDetailDAO transferDetailDAO;


    public void setTransferDetailDAO(ITransferDetailDAO transferDetailDAO) {
        this.transferDetailDAO = transferDetailDAO;
    }

    @Override
    public void attachDirty(TransferDetail instance) {
        transferDetailDAO.attachDirty(instance);
    }

    @Override
    public List findByStatTag(Object statTag) {
        return transferDetailDAO.findByStatTag(statTag);
    }

    @Override
    public List findByBatchNo(String batchNo) {
        return transferDetailDAO.findByBatchNo(batchNo);
    }

    @Override
    public void batchUpdate(List<TransferDetail> transferDetails) throws Exception {
        transferDetailDAO.batchUpdate(transferDetails);
    }
}
