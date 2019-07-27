package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.ICourierDAO;
import cn.iliker.mall.entity.CourierCompany;
import cn.iliker.mall.service.ICourierSvc;

public class CourierSvcImpl implements ICourierSvc {
	private ICourierDAO courierdao;

	public void setCourierdao(ICourierDAO courierdao) {
		this.courierdao = courierdao;
	}

	@Override
	public void save(CourierCompany transientInstance) {
		courierdao.save(transientInstance);
	}

	@Override
	public void delete(CourierCompany persistentInstance) {
		courierdao.delete(persistentInstance);
	}

	@Override
	public CourierCompany findById(Integer id) {
		return courierdao.findById(id);
	}

	@Override
	public List findAll() {
		return courierdao.findAll();
	}

	@Override
	public CourierCompany merge(CourierCompany detachedInstance) {
		return courierdao.merge(detachedInstance);
	}

}
