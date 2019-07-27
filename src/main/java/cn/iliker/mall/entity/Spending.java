package cn.iliker.mall.entity;

import java.util.Date;

//消费记录表
public class Spending implements java.io.Serializable{
    private Integer spendID;
    private Date spendTime;
    private String spendDESC;
    private Double spendAmount;
    private String phone;

    public Spending() {
    }

    public Spending(Double spendAmount, String phone) {
        this.spendAmount = spendAmount;
        this.phone = phone;
    }

    public Spending(String spendDESC, Double spendAmount, String phone) {
        this.spendDESC = spendDESC;
        this.spendAmount = spendAmount;
        this.phone = phone;
    }

    public Integer getSpendID() {
        return spendID;
    }

    public void setSpendID(Integer spendID) {
        this.spendID = spendID;
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

    public Double getSpendAmount() {
        return spendAmount;
    }

    public void setSpendAmount(Double spendAmount) {
        this.spendAmount = spendAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
