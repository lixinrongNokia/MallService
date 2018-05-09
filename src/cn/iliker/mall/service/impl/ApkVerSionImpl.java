package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IApkVerSionDao;
import cn.iliker.mall.entity.ApkVersion;
import cn.iliker.mall.service.IApkVerSionSvc;

public class ApkVerSionImpl implements IApkVerSionSvc {
	private IApkVerSionDao apkdao;

	public void setApkdao(IApkVerSionDao apkdao) {
		this.apkdao = apkdao;
	}

	@Override
	public ApkVersion find() {
		return apkdao.find();
	}

	@Override
	public boolean updateApkVersion(ApkVersion apkversion) {
		return apkdao.updateApkVersion(apkversion);
	}

}
