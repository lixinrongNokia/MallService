package cn.iliker.mall.service;

import cn.iliker.mall.entity.Theme;

import java.util.List;

public interface IThemeSvc {
    void save(Theme transientInstance);

    void delete(Theme persistentInstance);

    List findAll();
}
