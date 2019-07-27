package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IJSWXTotenDAO;
import cn.iliker.mall.dao.IWXTokenDAO;
import cn.iliker.mall.dao.IWxTicketDAO;
import cn.iliker.mall.entity.JSWXToken;
import cn.iliker.mall.entity.WXTicket;
import cn.iliker.mall.entity.WXToken;
import cn.iliker.mall.service.IWXPaperService;

public class WXTokenServiceImpl implements IWXPaperService {
    private IWXTokenDAO wxTokenDAO;
    private IWxTicketDAO wxTicketDAO;
    private IJSWXTotenDAO ijswxTotenDAO;

    public void setIjswxTotenDAO(IJSWXTotenDAO ijswxTotenDAO) {
        this.ijswxTotenDAO = ijswxTotenDAO;
    }

    public void setWxTokenDAO(IWXTokenDAO wxTokenDAO) {
        this.wxTokenDAO = wxTokenDAO;
    }

    public void setWxTicketDAO(IWxTicketDAO wxTicketDAO) {
        this.wxTicketDAO = wxTicketDAO;
    }

    @Override
    public boolean updateToken(WXToken token) {
        return wxTokenDAO.updateToken(token);
    }

    @Override
    public WXToken getToken(int id) {
        return wxTokenDAO.getToken(id);
    }

    @Override
    public boolean updateTicket(WXTicket wxTicket) {
        return wxTicketDAO.updateTicket(wxTicket);
    }

    @Override
    public WXTicket getTicket(int id) {
        return wxTicketDAO.getTicket(id);
    }

    @Override
    public boolean saveJSWXToken(JSWXToken jswxToken) {
        return ijswxTotenDAO.saveJSWXToken(jswxToken);
    }

    @Override
    public JSWXToken getJSWXTokenByOpenId(String openid) {
        return ijswxTotenDAO.getJSWXTokenByOpenId(openid);
    }
}
