package cn.iliker.mall.entity;

import java.io.Serializable;
import java.util.Date;

/*卡券订单*/
public class PrepaidCardOrder implements Serializable {
    private String id;
    private Double denomination;//面值
    private Double voucher_value = 0.00;//可用金额
    private String cardUrl;//卡券图片地址
    private Boolean effective = false;//是否付款
    private String phone;//购买卡券的手机号
    private Boolean sys_flag = false;//是否后台处理过
    private Date activation;//激活时间

    public PrepaidCardOrder() {
    }

    public PrepaidCardOrder(String id, Double denomination, String cardUrl, String phone) {
        this.id = id;
        this.denomination = denomination;
        this.cardUrl = cardUrl;
        this.phone = phone;
    }

    public PrepaidCardOrder(String id, Double denomination, Double voucher_value, String cardUrl, Boolean effective, String phone, Boolean sys_flag, Date activation) {
        this.id = id;
        this.denomination = denomination;
        this.voucher_value = voucher_value;
        this.cardUrl = cardUrl;
        this.effective = effective;
        this.phone = phone;
        this.sys_flag = sys_flag;
        this.activation = activation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getDenomination() {
        return denomination;
    }

    public void setDenomination(Double denomination) {
        this.denomination = denomination;
    }

    public Double getVoucher_value() {
        return voucher_value;
    }

    public void setVoucher_value(Double voucher_value) {
        this.voucher_value = voucher_value;
    }

    public String getCardUrl() {
        return cardUrl;
    }

    public void setCardUrl(String cardUrl) {
        this.cardUrl = cardUrl;
    }

    public Boolean getEffective() {
        return effective;
    }

    public void setEffective(Boolean effective) {
        this.effective = effective;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSys_flag() {
        return sys_flag;
    }

    public void setSys_flag(Boolean sys_flag) {
        this.sys_flag = sys_flag;
    }

    public Date getActivation() {
        return activation;
    }

    public void setActivation(Date activation) {
        this.activation = activation;
    }
}
