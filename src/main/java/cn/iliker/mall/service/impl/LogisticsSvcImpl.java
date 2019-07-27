package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.ITOrderDao;
import cn.iliker.mall.dao.IlogisticsDAO;
import cn.iliker.mall.entity.Logistics;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.service.IlogisticsSvc;

public class LogisticsSvcImpl implements IlogisticsSvc {
	private IlogisticsDAO logisticsdao;
	private ITOrderDao itOrderDao;

	public void setItOrderDao(ITOrderDao itOrderDao) {
		this.itOrderDao = itOrderDao;
	}

	public void setLogisticsdao(IlogisticsDAO logisticsdao) {
		this.logisticsdao = logisticsdao;
	}


	@Override
	public void delete(Logistics persistentInstance) {
		logisticsdao.delete(persistentInstance);
	}

	@Override
	public Logistics findById(Integer id) {
		return logisticsdao.findById(id);
	}

	@Override
	public List findAll() {
		return logisticsdao.findAll();
	}


	@Override
	public void add(Logistics logistics,TOrder tOrder) throws RuntimeException{
		logisticsdao.add(logistics);
		itOrderDao.save(tOrder);
	}


	@Override
	public List findByProperty(String propertyName, Object value) {
		return logisticsdao.findByProperty(propertyName, value);
	}

}
