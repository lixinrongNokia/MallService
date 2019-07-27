package cn.iliker.mall.dao;

import java.util.List;

public interface ISearchDAO<T> {
    List<T> searchObject(int start, int pageSize, String[] fields, String matching, Class<T> T);
}
