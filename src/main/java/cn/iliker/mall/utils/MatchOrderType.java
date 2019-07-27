package cn.iliker.mall.utils;

public final class MatchOrderType {

    public static char matchOrderType(String orderid) {
        return orderid.toCharArray()[0];
    }
}
