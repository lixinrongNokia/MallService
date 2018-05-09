package cn.iliker.mall.dao;

import com.alibaba.fastjson.JSONObject;

public interface ICommonQueryDAO {
    JSONObject getAll(String _fields, String _tables, String _where, String _groupby, String _orderby, int _pageindex, int _pagesize);
}
