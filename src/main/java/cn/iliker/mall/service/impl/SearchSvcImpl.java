package cn.iliker.mall.service.impl;

import java.util.List;

import cn.iliker.mall.dao.ISearchDAO;
import cn.iliker.mall.service.ISearchSvc;

public class SearchSvcImpl<T> implements ISearchSvc {
    private ISearchDAO searchDAO;

    public void setSearchDAO(ISearchDAO searchDAO) {
        this.searchDAO = searchDAO;
    }

    @Override
    public List<T> searchObject(int start, int pageSize, String[] fields, String matching, Class T) {
        return searchDAO.searchObject(start, pageSize, fields, matching, T);
    }
}
