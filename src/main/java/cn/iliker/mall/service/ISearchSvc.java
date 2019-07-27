package cn.iliker.mall.service;

import java.util.List;

public interface ISearchSvc<T> {
    List<T> searchObject(int start, int pageSize, String[] fields, String matching, Class<T> T);
}
