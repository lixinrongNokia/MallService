package cn.iliker.mall.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Transfer entity. @author MyEclipse Persistence Tools
 */

public class Transfer implements java.io.Serializable {

    // Fields

    private Integer transferId;
    private String phone;
    private String account;
    private String realname;
    private Double amount;
    private Date putinforTime;
    private Date manageTime;
    private Boolean dealTag;
    private String note;
    private String nickname;
    private Set transferDetails = new HashSet(0);
    // Constructors

    /**
     * default constructor
     */
    public Transfer() {
    }

    /**
     * minimal constructor
     */
    public Transfer(String phone, String account, String realname, Double amount) {
        this.phone = phone;
        this.account = account;
        this.realname = realname;
        this.amount = amount;
    }

    public Transfer(Integer transferId, String phone, String account, String realname, Double amount, String note) {
        this.transferId = transferId;
        this.phone = phone;
        this.account = account;
        this.realname = realname;
        this.amount = amount;
        this.note = note;
    }

    /**
     * full constructor
     */
    public Transfer(String phone, String account, String realname,
                    Double amount, Date putinforTime,
                    Boolean dealTag, Set transferDetails) {
        this.phone = phone;
        this.account = account;
        this.realname = realname;
        this.amount = amount;
        this.putinforTime = putinforTime;
        this.dealTag = dealTag;
        this.transferDetails = transferDetails;
    }

    // Property accessors

    public Integer getTransferId() {
        return this.transferId;
    }

    public void setTransferId(Integer transferId) {
        this.transferId = transferId;
    }

    public String getPhone() {
        return phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }


    public Date getPutinforTime() {
        return this.putinforTime;
    }

    public void setPutinforTime(Date putinforTime) {
        this.putinforTime = putinforTime;
    }

    public Date getManageTime() {
        return manageTime;
    }

    public void setManageTime(Date manageTime) {
        this.manageTime = manageTime;
    }

    public Boolean getDealTag() {
        return this.dealTag;
    }

    public void setDealTag(Boolean dealTag) {
        this.dealTag = dealTag;
    }

    public Set getTransferDetails() {
        return this.transferDetails;
    }

    public void setTransferDetails(Set transferDetails) {
        this.transferDetails = transferDetails;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}