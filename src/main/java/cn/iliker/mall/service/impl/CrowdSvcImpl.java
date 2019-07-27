package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.ICrowd;
import cn.iliker.mall.entity.Crowd;
import cn.iliker.mall.service.ICrowdSvc;

public class CrowdSvcImpl implements ICrowdSvc {
	private ICrowd crowd;

	public void setCrowd(ICrowd crowd) {
		this.crowd = crowd;
	}

	@Override
	public void save(Crowd transientInstance) {
		crowd.save(transientInstance);
	}

	@Override
	public void delete(Crowd persistentInstance) {
		crowd.delete(persistentInstance);
	}

	@Override
	public Crowd findById(Integer id) {
		return crowd.findById(id);
	}

	@Override
	public List findByName(Object name) {
		return crowd.findByName(name);
	}

	@Override
	public List findAll() {
		return crowd.findAll();
	}

	@Override
	public Crowd merge(Crowd detachedInstance) {
		return crowd.merge(detachedInstance);
	}

}
