package cn.iliker.mall.entity;

public class Wallet implements java.io.Serializable {
    private Integer uid;
    private String phone;
    private Double remainingSum = 0.00;//余额
    private Double totalSpending = 0.00;//累计支出
    private Userinfo userinfo;
    public Wallet() {
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Wallet(String phone,Userinfo userinfo) {
        this.phone = phone;
        this.userinfo=userinfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getRemainingSum() {
        return remainingSum;
    }

    public void setRemainingSum(Double remainingSum) {
        this.remainingSum = remainingSum;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(Double totalSpending) {
        this.totalSpending = totalSpending;
    }
}
