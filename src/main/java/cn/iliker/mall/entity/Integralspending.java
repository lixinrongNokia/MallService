package cn.iliker.mall.entity;

import java.util.Date;

public class Integralspending {
    private Integer integralspendingID;
    private Date spendTime;
    private String spendDESC;
    private Integer spendAmount;
    private String phone;

    public Integralspending() {
    }

    public Integralspending(Integer spendAmount, String phone) {
        this.spendAmount = spendAmount;
        this.phone = phone;
    }

    public Integer getIntegralspendingID() {
        return integralspendingID;
    }

    public void setIntegralspendingID(Integer integralspendingID) {
        this.integralspendingID = integralspendingID;
    }

    public Date getSpendTime() {
        return spendTime;
    }

    public void setSpendTime(Date spendTime) {
        this.spendTime = spendTime;
    }

    public String getSpendDESC() {
        return spendDESC;
    }

    public void setSpendDESC(String spendDESC) {
        this.spendDESC = spendDESC;
    }

    public Integer getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(Integer spendAmount) {
        this.spendAmount = spendAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
