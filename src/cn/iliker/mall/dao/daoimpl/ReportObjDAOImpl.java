package cn.iliker.mall.dao.daoimpl;

import cn.iliker.mall.dao.IReportObjDAO;
import cn.iliker.mall.entity.ReportShare;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.List;

public class ReportObjDAOImpl extends HibernateDaoSupport implements IReportObjDAO {
    @Override
    public void addReportShare(ReportShare reportShare) throws Exception {
        getHibernateTemplate().save(reportShare);
    }

    @Override
    public void updateReportShare(ReportShare reportShare) throws Exception {
        getHibernateTemplate().update(reportShare);
    }

    @Override
    public List<ReportShare> queryMyReportForShare(int uid, int startPage, int pageSize) {
        return null;
    }
}
