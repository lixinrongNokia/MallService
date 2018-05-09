package cn.iliker.mall.dao;

import cn.iliker.mall.entity.JSWXToken;

public interface IJSWXTotenDAO {

    boolean saveJSWXToken(JSWXToken jswxToken);

    JSWXToken getJSWXTokenByOpenId(String openid);
}
