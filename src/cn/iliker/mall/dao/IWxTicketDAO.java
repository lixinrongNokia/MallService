package cn.iliker.mall.dao;


import cn.iliker.mall.entity.WXTicket;

public interface IWxTicketDAO {
    boolean updateTicket(WXTicket wxTicket);

    WXTicket getTicket(int id);
}
