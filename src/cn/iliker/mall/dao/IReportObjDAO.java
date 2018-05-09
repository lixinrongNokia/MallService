package cn.iliker.mall.dao;

import cn.iliker.mall.entity.ReportShare;

import java.util.List;

public interface IReportObjDAO {
    void addReportShare(ReportShare reportShare) throws Exception;

    void updateReportShare(ReportShare reportShare) throws Exception;

    List<ReportShare> queryMyReportForShare(int uid, int startPage, int pageSize);
}
