package cn.iliker.mall.service;

import cn.iliker.mall.entity.Userinfo;
import cn.iliker.mall.entity.WXUser;

public interface IWXUserService {
    void saveWXUser(WXUser user) throws RuntimeException;

    WXUser getWXUserByUnionid(String unionid);

    WXUser getWXUserByUID(int uid);

    void delWXUser(WXUser wxUser) throws Exception;

    void bindWXToAPP(WXUser user, Userinfo userinfo) throws Exception;
}
