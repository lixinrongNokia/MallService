package cn.iliker.mall.entity;

import cn.iliker.mall.entity.stateattr.ClearingType;

import java.io.Serializable;
import java.sql.Timestamp;


public class UnpackOrder implements Serializable {
    private UnpackOrderId unpackOrderId;
    private ClearingType clearingType;
    private Timestamp createTime;
    private Boolean user_confirm;
    private Boolean sys_flag;

    public UnpackOrder() {
    }

    public UnpackOrder(UnpackOrderId unpackOrderId) {
        this.unpackOrderId = unpackOrderId;
    }

    public UnpackOrderId getUnpackOrderId() {
        return unpackOrderId;
    }

    public void setUnpackOrderId(UnpackOrderId unpackOrderId) {
        this.unpackOrderId = unpackOrderId;
    }

    public ClearingType getClearingType() {
        return clearingType;
    }

    public void setClearingType(ClearingType clearingType) {
        this.clearingType = clearingType;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Boolean getUser_confirm() {
        return user_confirm;
    }

    public void setUser_confirm(Boolean user_confirm) {
        this.user_confirm = user_confirm;
    }

    public Boolean getSys_flag() {
        return sys_flag;
    }

    public void setSys_flag(Boolean sys_flag) {
        this.sys_flag = sys_flag;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof UnpackOrder))
            return false;
        UnpackOrder castOther = (UnpackOrder) other;

        return ((this.getUnpackOrderId() == castOther.getUnpackOrderId()) || (this
                .getUnpackOrderId() != null && castOther.getUnpackOrderId() != null && this
                .getUnpackOrderId().equals(castOther.getUnpackOrderId())));
    }

    public int hashCode() {
        int result = 17;

        result = 37
                * result
                + (getUnpackOrderId() == null ? 0 : this.getUnpackOrderId()
                .hashCode());
        return result;
    }
}