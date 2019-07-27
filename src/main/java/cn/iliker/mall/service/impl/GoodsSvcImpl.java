package cn.iliker.mall.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.alibaba.fastjson.JSONArray;

import cn.iliker.mall.dao.ICollectionDAO;
import cn.iliker.mall.dao.IGoods;
import cn.iliker.mall.entity.Collection;
import cn.iliker.mall.entity.GeneralList;
import cn.iliker.mall.entity.Goods;
import cn.iliker.mall.error.DelError;
import cn.iliker.mall.service.IGoodsSvc;

public class GoodsSvcImpl implements IGoodsSvc {
    private IGoods good;
    private ICollectionDAO collectionDAO;

    /*private CompassTemplate compassTemplate;

    public void setCompass(Compass compass) {
        this.compassTemplate = new CompassTemplate(compass);
    }*/

    public void setCollectionDAO(ICollectionDAO collectionDAO) {
        this.collectionDAO = collectionDAO;
    }

    @Override
    public GeneralList<Collection> findByUserId(Integer id, int offset, int pageCount) {
        return collectionDAO.findByUserId(id, offset, pageCount);
    }

    @Override
    public void addBuyBean(List<Goods> list) throws SQLException {
        good.addBuyBean(list);
    }

    public void setGood(IGoods good) {
        this.good = good;
    }

    @Override
    public boolean save(Goods transientInstance) {
        return good.save(transientInstance);
    }

    @Override
    public void delete(Goods goods) throws DelError {
        good.delete(goods);
    }

    @Override
    public Goods findById(Integer id) {
        return good.findById(id);
    }

    @Override
    public GeneralList<Goods> findAll(int offset, int pagesize, String search_where, String search_value) {
        return this.good.findAll(offset, pagesize, search_where, search_value);
    }

    @Override
    public boolean updateGood(Goods good) {
        return this.good.updateGood(good);
    }

    @Override
    public List findByGoodCode(Object goodCode) {
        return good.findByGoodCode(goodCode);
    }

    @Override
    public int editDivided_into(String divided_into, int goodid) {
        return good.editDivided_into(divided_into, goodid);
    }

    /*@Override
    public List<Goods> query(String keyword, int firstResult, int maxResult) {
        return compassTemplate.execute(new QueryCallback(keyword, firstResult, maxResult));
    }*/

    @Override
    public JSONArray loadLimit(String entityName, String[] columns, String where, Object[] selectionArgs,
                               String groupBy, String having, String orderBy) {
        return good.loadLimit(entityName, columns, where, selectionArgs,
                groupBy, having, orderBy);
    }

    @Override
    public Goods findGoodsByGoodsCode(Goods goods) {
        return good.findGoodsByGoodsCode(goods);
    }
}
