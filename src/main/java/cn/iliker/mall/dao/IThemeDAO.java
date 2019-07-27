package cn.iliker.mall.dao;


import java.util.List;

import cn.iliker.mall.entity.Theme;

public interface IThemeDAO {
    void save(Theme transientInstance);

    void delete(Theme persistentInstance);

    List findAll();
}
