package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.ITransferRecordDAO;
import cn.iliker.mall.entity.TransferRecord;
import cn.iliker.mall.service.ITransferRecordSvc;

public class TransferRecordImpl implements ITransferRecordSvc {
	private ITransferRecordDAO transferrecordDao;

	public void setTransferrecordDao(ITransferRecordDAO transferrecordDao) {
		this.transferrecordDao = transferrecordDao;
	}

	@Override
	public void save(TransferRecord transientInstance) {
		transferrecordDao.save(transientInstance);
	}

	@Override
	public List findByBatchNo(Object batchNo) {
		return transferrecordDao.findByBatchNo(batchNo);
	}

	@Override
	public List findByBatchNum(Object batchNum) {
		return transferrecordDao.findByBatchNum(batchNum);
	}

	@Override
	public List findAll() {
		return transferrecordDao.findAll();
	}

}
