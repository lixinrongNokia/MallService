package cn.iliker.mall.entity;

import java.io.Serializable;
import java.util.Date;

/*充值订单*/
public class Rechargeableorder implements Serializable {
    private Integer id;
    private String rechargeableid;
    private Double amount;
    private Date createtime;
    private String paytype;
    private Boolean rechargeablestate;
    private String fromPhone;
    private String taggetPhone;
    private String trade_no;
    private String remarks;
    private Boolean sys_flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRechargeableid() {
        return rechargeableid;
    }

    public void setRechargeableid(String rechargeableid) {
        this.rechargeableid = rechargeableid;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Boolean getRechargeablestate() {
        return rechargeablestate;
    }

    public void setRechargeablestate(Boolean rechargeablestate) {
        this.rechargeablestate = rechargeablestate;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getFromPhone() {
        return fromPhone;
    }

    public void setFromPhone(String fromPhone) {
        this.fromPhone = fromPhone;
    }

    public String getTaggetPhone() {
        return taggetPhone;
    }

    public void setTaggetPhone(String taggetPhone) {
        this.taggetPhone = taggetPhone;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getSys_flag() {
        return sys_flag;
    }

    public void setSys_flag(Boolean sys_flag) {
        this.sys_flag = sys_flag;
    }
}
