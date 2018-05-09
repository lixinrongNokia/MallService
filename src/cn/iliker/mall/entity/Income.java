package cn.iliker.mall.entity;

import java.util.Date;

public class Income implements java.io.Serializable{
    private Integer incomeID;
    private Date incomeTime;
    private String incomeDESC;
    private Double incomeAmount;
    private String phone;

    public Income() {
    }

    public Income(String incomeDESC, Double incomeAmount, String phone) {
        this.incomeDESC = incomeDESC;
        this.incomeAmount = incomeAmount;
        this.phone = phone;
    }

    public Integer getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(Integer incomeID) {
        this.incomeID = incomeID;
    }

    public Date getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(Date incomeTime) {
        this.incomeTime = incomeTime;
    }

    public String getIncomeDESC() {
        return incomeDESC;
    }

    public void setIncomeDESC(String incomeDESC) {
        this.incomeDESC = incomeDESC;
    }

    public Double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(Double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
