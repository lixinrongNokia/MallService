package cn.iliker.mall.service;

import cn.iliker.mall.entity.JSWXToken;
import cn.iliker.mall.entity.WXTicket;
import cn.iliker.mall.entity.WXToken;

public interface IWXPaperService {
    boolean updateToken(WXToken token);

    WXToken getToken(int id);

    boolean updateTicket(WXTicket wxTicket);

    WXTicket getTicket(int id);

    boolean saveJSWXToken(JSWXToken jswxToken);

    JSWXToken getJSWXTokenByOpenId(String openid);
}
