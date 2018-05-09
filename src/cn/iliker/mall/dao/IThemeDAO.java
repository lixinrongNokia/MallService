package cn.iliker.mall.dao;


import cn.iliker.mall.entity.Theme;

import java.util.List;

public interface IThemeDAO {
    void save(Theme transientInstance);

    void delete(Theme persistentInstance);

    List findAll();
}
