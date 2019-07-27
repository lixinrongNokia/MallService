package cn.iliker.mall.service;

import java.util.List;

import cn.iliker.mall.entity.ReportShare;

public interface IReportObjSvc {
    void addReportShare(ReportShare reportShare) throws Exception;

    void updateReportShare(ReportShare reportShare) throws Exception;

    List<ReportShare> queryMyReportForShare(int uid, int startPage, int pageSize);
}
