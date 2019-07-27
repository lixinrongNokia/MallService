package cn.iliker.mall.entity;

import java.util.Date;

//积分收入记录实体
public class Integralincome {
    private Integer integralincomeID;
    private Date incomeTime;
    private String incomeDESC;
    private Integer incomeAmount;
    private String phone;

    public Integralincome() {
    }

    public Integralincome(Integer incomeAmount, String phone) {
        this.incomeAmount = incomeAmount;
        this.phone = phone;
    }

    public Integer getIntegralincomeID() {
        return integralincomeID;
    }

    public void setIntegralincomeID(Integer integralincomeID) {
        this.integralincomeID = integralincomeID;
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

    public Integer getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(Integer incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
