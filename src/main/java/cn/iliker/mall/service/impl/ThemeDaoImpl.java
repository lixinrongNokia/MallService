package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.IThemeDAO;
import cn.iliker.mall.entity.Theme;
import cn.iliker.mall.service.IThemeSvc;

public class ThemeDaoImpl implements IThemeSvc {
    private IThemeDAO themeDAO;

    public void setThemeDAO(IThemeDAO themeDAO) {
        this.themeDAO = themeDAO;
    }

    @Override
    public void save(Theme transientInstance) {
        themeDAO.save(transientInstance);
    }

    @Override
    public void delete(Theme persistentInstance) {
        themeDAO.delete(persistentInstance);
    }

    @Override
    public List findAll() {
        return themeDAO.findAll();
    }
}
