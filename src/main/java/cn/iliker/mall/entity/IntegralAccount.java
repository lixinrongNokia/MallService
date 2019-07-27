package cn.iliker.mall.entity;

import java.io.Serializable;

public class IntegralAccount implements Serializable{
    private Integer uid;
    private String phone;
    private Integer amount;
    private Userinfo userinfo;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public IntegralAccount() {
    }

    public IntegralAccount(String phone,Userinfo userinfo) {
        this.phone = phone;
        this.userinfo=userinfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }
}
