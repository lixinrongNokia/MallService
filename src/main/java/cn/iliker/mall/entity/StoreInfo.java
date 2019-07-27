package cn.iliker.mall.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StoreInfo implements java.io.Serializable {
    private Integer id;
    private String loginEmail;//登錄郵箱
    private String loginPwd;//登錄密碼
    private String storeName;//門店名稱
    private String faceIcon;//門店圖片
    private Double latitude;//纬度
    private Double longitude;//经度
    private String tell;//電話
    private String contacts;//店長
    private String address;//門店地址
    private Integer visible;//是否激活
    private String regTime;//注冊時間
    private Long outTime;//找回密碼登記時間
    private Integer arbitration;//是否審核通過
    private Double distance;//距离
    private Set<UnpackOrder> unpackOrders = new HashSet<>();//门店自提订单
    private StoreWallet storeWallet;

    public StoreInfo() {
    }

    public StoreInfo(Integer id) {
        this.id = id;
    }

    public StoreInfo(Integer id, String loginEmail, String loginPwd, String storeName, String faceIcon, String tell, String contacts, String address, Integer visible, String regTime, Long outTime, Integer arbitration) {
        this.id = id;
        this.loginEmail = loginEmail;
        this.loginPwd = loginPwd;
        this.storeName = storeName;
        this.faceIcon = faceIcon;
        this.tell = tell;
        this.contacts = contacts;
        this.address = address;
        this.visible = visible;
        this.regTime = regTime;
        this.outTime = outTime;
        this.arbitration = arbitration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginEmail() {
        return loginEmail;
    }

    public void setLoginEmail(String loginEmail) {
        this.loginEmail = loginEmail;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getFaceIcon() {
        return faceIcon;
    }

    public void setFaceIcon(String faceIcon) {
        this.faceIcon = faceIcon;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public Long getOutTime() {
        return outTime;
    }

    public void setOutTime(Long outTime) {
        this.outTime = outTime;
    }

    public Integer getArbitration() {
        return arbitration;
    }

    public void setArbitration(Integer arbitration) {
        this.arbitration = arbitration;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Set<UnpackOrder> getUnpackOrders() {
        return unpackOrders;
    }

    public void setUnpackOrders(Set<UnpackOrder> unpackOrders) {
        this.unpackOrders = unpackOrders;
    }

    public StoreWallet getStoreWallet() {
        return storeWallet;
    }

    public void setStoreWallet(StoreWallet storeWallet) {
        this.storeWallet = storeWallet;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof StoreInfo))
            return false;
        StoreInfo castOther = (StoreInfo) other;

        return ((Objects.equals(this.getId(), castOther.getId())) || (this
                .getId() != null && castOther.getId() != null && this
                .getId().equals(castOther.getId())));
    }

    public int hashCode() {
        int result = 17;

        result = 37
                * result
                + (getId() == null ? 0 : this.getId()
                .hashCode());
        return result;
    }
}
