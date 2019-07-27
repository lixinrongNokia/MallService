package cn.iliker.mall.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

/**
 * Userinfo entity. @author MyEclipse Persistence Tools
 */
@Indexed
@Analyzer(impl = SmartChineseAnalyzer.class)//分词器
public class Userinfo implements java.io.Serializable {

    // Fields
    @DocumentId
    private Integer uid;
    private String realname;
    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
    private String nickname;
    private String email;
    private String password;
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    private String phone;
    private String sex;
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    private String headimg;
    private String signature;
    private String birthday;
    private String gid;
    private String superiornum;
    private Userinfo parent;
    private Date registered;
    private Integer level = 0;
    private Boolean modifySuper;
    private String paymentCode;
    private Set userdatas = new HashSet(0);
    private Set shippingaddresses = new HashSet(0);
    private Set shares = new HashSet(0);
    private Set collections = new HashSet(0);
    private Wallet wallet;
    private GiveAccount giveAccount;
    private IntegralAccount integralAccount;
    private Boolean isEnable = true;
    private String guestNO;
    private String rongYunToken;
// Constructors

    /**
     * default constructor
     */
    public Userinfo() {
    }

    public Userinfo(Integer uid) {
        this.uid = uid;
    }

    /**
     * minimal constructor
     */
    public Userinfo(String nickname, String email) {
        this.nickname = nickname;
        this.email = email;
    }

    /**
     * minimal constructor
     */
    public Userinfo(String nickname, String password, String phone) {
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
    }

    public Userinfo(String nickname, String password, String phone, Userinfo parent) {
        this.nickname = nickname;
        this.password = password;
        this.phone = phone;
        this.parent = parent;
    }
// Property accessors

    public String getSuperiornum() {
        return superiornum;
    }

    public void setSuperiornum(String superiornum) {
        this.superiornum = superiornum;
    }

    public Integer getUid() {
        return this.uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getRealname() {
        return this.realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Userinfo getParent() {
        return parent;
    }

    public void setParent(Userinfo parent) {
        this.parent = parent;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getModifySuper() {
        return modifySuper;
    }

    public void setModifySuper(Boolean modifySuper) {
        this.modifySuper = modifySuper;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadimg() {
        return this.headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getSignature() {
        return this.signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGid() {
        return this.gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public Set getUserdatas() {
        return this.userdatas;
    }

    public void setUserdatas(Set userdatas) {
        this.userdatas = userdatas;
    }

    public Set getShippingaddresses() {
        return this.shippingaddresses;
    }

    public void setShippingaddresses(Set shippingaddresses) {
        this.shippingaddresses = shippingaddresses;
    }

    public Set getShares() {
        return this.shares;
    }

    public void setShares(Set shares) {
        this.shares = shares;
    }

    public Set getCollections() {
        return this.collections;
    }

    public void setCollections(Set collections) {
        this.collections = collections;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public GiveAccount getGiveAccount() {
        return giveAccount;
    }

    public void setGiveAccount(GiveAccount giveAccount) {
        this.giveAccount = giveAccount;
    }

    public IntegralAccount getIntegralAccount() {
        return integralAccount;
    }

    public void setIntegralAccount(IntegralAccount integralAccount) {
        this.integralAccount = integralAccount;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getGuestNO() {
        return guestNO;
    }

    public void setGuestNO(String guestNO) {
        this.guestNO = guestNO;
    }

    public String getRongYunToken() {
        return rongYunToken;
    }

    public void setRongYunToken(String rongYunToken) {
        this.rongYunToken = rongYunToken;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof Userinfo))
            return false;
        Userinfo castOther = (Userinfo) other;

        return ((this.getUid() == castOther.getUid()) || (this
                .getUid() != null && castOther.getUid() != null && this
                .getUid().equals(castOther.getUid())));
    }

    public int hashCode() {
        int result = 17;

        result = 37
                * result
                + (getUid() == null ? 0 : this.getUid()
                .hashCode());
        return result;
    }
}