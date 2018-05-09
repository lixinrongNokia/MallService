package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IWXUserDAO;
import cn.iliker.mall.entity.Userinfo;
import cn.iliker.mall.entity.WXUser;
import cn.iliker.mall.service.IUserinfoSvc;
import cn.iliker.mall.service.IWXUserService;

public class WXUserSvcImpl implements IWXUserService {
    private IWXUserDAO userDAO;
    private IUserinfoSvc userinfoSvc;

    public void setUserDAO(IWXUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setUserinfoSvc(IUserinfoSvc userinfoSvc) {
        this.userinfoSvc = userinfoSvc;
    }

    @Override
    public void saveWXUser(WXUser user) throws RuntimeException {
        userDAO.saveWXUser(user);
    }

    @Override
    public WXUser getWXUserByUnionid(String unionid) {
        return userDAO.getWXUserByUnionid(unionid);
    }

    @Override
    public WXUser getWXUserByUID(int uid) {
        return userDAO.getWXUserByUID(uid);
    }

    @Override
    public void delWXUser(WXUser wxUser) throws Exception {
        userDAO.delWXUser(wxUser);
    }

    @Override
    public void bindWXToAPP(WXUser wxUseru, Userinfo userinfo) throws Exception {
        userinfoSvc.save(userinfo);
        this.saveWXUser(wxUseru);
    }
}
