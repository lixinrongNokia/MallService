package cn.iliker.mall.dao;


import cn.iliker.mall.entity.TransferDetail;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface ITransferDetailDAO {
    void attachDirty(TransferDetail instance);

    List findByBatchNo(String batchNo);

    List findByStatTag(Object statTag);

    void batchUpdate(List<TransferDetail> transferDetails) throws SQLException;
}
