package cn.iliker.mall.entity;

import cn.iliker.mall.utils.MD5Util;

//原始用户自动生成
public class ProtohuMan {
    private String cardId;
    private String realName;
    private String phone;
    private String password;
    private String nickName;

    public ProtohuMan(String cardId,String realName, String phone) {
        this.cardId=cardId;
        setRealName(realName);
        setPhone(phone);
        String tmp = phone.substring(5, phone.length());
        setNickName("AUTO" + tmp);//昵称
        setPassword(MD5Util.getMD5Str(tmp));//密码
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        if (realName != null) {
            this.realName = realName;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
