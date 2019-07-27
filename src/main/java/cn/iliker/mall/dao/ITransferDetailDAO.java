package cn.iliker.mall.dao;


import java.sql.SQLException;
import java.util.List;

import cn.iliker.mall.entity.TransferDetail;

public interface ITransferDetailDAO {
    void attachDirty(TransferDetail instance);

    List findByBatchNo(String batchNo);

    List findByStatTag(Object statTag);

    void batchUpdate(List<TransferDetail> transferDetails) throws SQLException;
}
