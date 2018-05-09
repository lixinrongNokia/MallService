package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IShareDAO;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Share;
import cn.iliker.mall.service.IShareSvc;

public class ShareSvcImpl implements IShareSvc {
private IShareDAO dao;

	@Override
	public void save(Share transientInstance) throws RuntimeException {
		dao.save(transientInstance);
	}

	public void setDao(IShareDAO dao) {
	this.dao = dao;
}

	@Override
	public void delete(Share persistentInstance) {
		dao.delete(persistentInstance);
	}

	@Override
	public GeneralList findAll(int offset, int pagesize, int node) {
		return dao.findAll(offset,pagesize,node);
	}

	@Override
	public Share findById(Integer id) {
		return dao.findById(id);
	}

	@Override
	public boolean delShare(Integer id) {
		return dao.delShare(id);
	}

}
