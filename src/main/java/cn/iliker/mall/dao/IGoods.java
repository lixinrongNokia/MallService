package cn.iliker.mall.dao;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Goods;
import cn.iliker.mall.entity.StockInfo;
import cn.iliker.mall.error.DelError;

public interface IGoods {

    void addBuyBean(List<Goods> list) throws SQLException;

    boolean save(Goods transientInstance);

    void delete(Goods goods) throws DelError;

    Goods findById(java.lang.Integer id);

    GeneralList<Goods> findAll(int offset, int pagesize, String search_where,String search_value);

    boolean updateGood(Goods good);

    List findByGoodCode(Object goodCode);

    int editDivided_into(String divided_into, int goodid);

    JSONArray loadLimit(String entityName, String[] columns, String where, Object[] selectionArgs,
                        String groupBy, String having, String orderBy);

    void updateStockInfo(StockInfo stockInfo) throws RuntimeException;

    Goods findGoodsByGoodsCode(Goods goods);

}