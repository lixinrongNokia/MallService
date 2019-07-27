package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.Theme;

public interface IThemeSvc {
    void save(Theme transientInstance);

    void delete(Theme persistentInstance);

    List findAll();
}
