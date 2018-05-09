package cn.iliker.mall.dao;


import cn.iliker.mall.entity.WXToken;

public interface IWXTokenDAO {
    boolean updateToken(WXToken token);

    WXToken getToken(int id);
}
