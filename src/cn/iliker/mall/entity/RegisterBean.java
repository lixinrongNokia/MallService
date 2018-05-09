package cn.iliker.mall.entity;

import java.io.Serializable;

//注册用户表单数据封装
public class RegisterBean implements Serializable {
    private String phoneNum;
    private String code;
    private String smscode;
    private String backPhone;
    private long time;
    private String nickname;
    private String password;
    private String superiornum;
    private String unionID;
    private String openid;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSmscode() {
        return smscode;
    }

    public void setSmscode(String smscode) {
        this.smscode = smscode;
    }

    public String getBackPhone() {
        return backPhone;
    }

    public void setBackPhone(String backPhone) {
        this.backPhone = backPhone;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSuperiornum() {
        return superiornum;
    }

    public void setSuperiornum(String superiornum) {
        this.superiornum = superiornum;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
