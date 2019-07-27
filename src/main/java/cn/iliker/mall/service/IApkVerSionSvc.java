package cn.iliker.mall.service;

import cn.iliker.mall.entity.ApkVersion;

public interface IApkVerSionSvc {

    ApkVersion find();

    boolean updateApkVersion(ApkVersion apkversion);

}