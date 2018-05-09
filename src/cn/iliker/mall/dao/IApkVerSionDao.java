package cn.iliker.mall.dao;


import cn.iliker.mall.entity.ApkVersion;

public interface IApkVerSionDao {

    ApkVersion find();

    boolean updateApkVersion(ApkVersion apkversion);

}