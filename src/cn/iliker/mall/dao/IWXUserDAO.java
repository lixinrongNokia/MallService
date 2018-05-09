package cn.iliker.mall.dao;

import cn.iliker.mall.entity.WXUser;

public interface IWXUserDAO {
    void saveWXUser(WXUser user) throws RuntimeException;

    WXUser getWXUserByUnionid(String unionid);

    WXUser getWXUserByUID(int uid);

    void delWXUser(WXUser wxUser);
}
