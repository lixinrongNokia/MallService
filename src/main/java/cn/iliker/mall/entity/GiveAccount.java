package cn.iliker.mall.entity;

import java.io.Serializable;

public class GiveAccount implements Serializable {
    private Integer uid;
    private String phone;
    private Double balance = 0.00;
    private Userinfo userinfo;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public GiveAccount() {
    }

    public GiveAccount(String phone,Userinfo userinfo) {
        this.phone = phone;
        this.userinfo=userinfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }
}
