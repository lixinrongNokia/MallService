package cn.iliker.mall.service;

import cn.iliker.mall.entity.Collection;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Goods;
import cn.iliker.mall.error.DelError;
import com.alibaba.fastjson.JSONArray;

import java.sql.SQLException;
import java.util.List;

public interface IGoodsSvc {
    GeneralList<Collection> findByUserId(Integer id, int offset, int pageCount);

    void addBuyBean(List<Goods> list) throws SQLException;

    boolean save(Goods transientInstance);

    void delete(Goods goods) throws DelError;

    Goods findById(java.lang.Integer id);

    GeneralList<Goods> findAll(int offset, int pagesize, String search_where, String search_value);

    boolean updateGood(Goods good);

    List findByGoodCode(Object goodCode);

    int editDivided_into(String divided_into, int goodid);

//    List<Goods> query(String keyword, int firstResult, int maxResult);

    JSONArray loadLimit(String entityName, String[] columns, String where, Object[] selectionArgs,
                        String groupBy, String having, String orderBy);

    Goods findGoodsByGoodsCode(Goods goods);
}