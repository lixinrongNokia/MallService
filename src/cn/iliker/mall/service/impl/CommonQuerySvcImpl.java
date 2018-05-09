package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.ICommonQueryDAO;
import cn.iliker.mall.service.ICommonQuerySvc;
import com.alibaba.fastjson.JSONObject;

public class CommonQuerySvcImpl implements ICommonQuerySvc {
    private ICommonQueryDAO commonQueryDAO;

    public void setCommonQueryDAO(ICommonQueryDAO commonQueryDAO) {
        this.commonQueryDAO = commonQueryDAO;
    }

    @Override
    public JSONObject getAll(String _fields, String _tables, String _where, String _groupby, String _orderby, int _pageindex, int _pagesize) {
        return commonQueryDAO.getAll(_fields, _tables, _where, _groupby, _orderby, _pageindex, _pagesize);
    }
}
