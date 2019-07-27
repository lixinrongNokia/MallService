package cn.iliker.mall.dao;

import java.util.List;

import cn.iliker.mall.entity.UnpackOrder;
import cn.iliker.mall.entity.UnpackOrderId;

public interface IUnpackOrderDAO {
    void save(UnpackOrder unpackOrder) throws Exception;
    List getUnpackOrders();
    UnpackOrder find(UnpackOrderId unpackOrderId);

    List<UnpackOrder> pageGetUnpackOrder(int storeId, int pageIndex, int pageCount);
}
