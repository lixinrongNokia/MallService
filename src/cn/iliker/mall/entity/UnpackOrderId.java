package cn.iliker.mall.entity;

import java.util.Objects;

public class UnpackOrderId implements java.io.Serializable {
    private TOrder orderInfo;
    private StoreInfo storeInfo;

    public UnpackOrderId() {
    }

    public UnpackOrderId(TOrder orderInfo, StoreInfo storeInfo) {
        this.orderInfo = orderInfo;
        this.storeInfo = storeInfo;
    }

    public TOrder getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(TOrder orderInfo) {
        this.orderInfo = orderInfo;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof UnpackOrderId))
            return false;
        UnpackOrderId castOther = (UnpackOrderId) other;

        return ((Objects.equals(this.getOrderInfo().getId(), castOther.getOrderInfo().getId())) || (this
                .getOrderInfo().getId() != null && castOther.getOrderInfo().getId() != null && this
                .getOrderInfo().getId().equals(castOther.getOrderInfo().getId())))
                && ((Objects.equals(this.getStoreInfo().getId(), castOther.getStoreInfo().getId())) || (this.getStoreInfo().getId() != null
                && castOther.getStoreInfo().getId() != null && this.getStoreInfo().getId()
                .equals(castOther.getStoreInfo().getId())));
    }

    public int hashCode() {
        int result = 17;
        result = 37
                * result
                + (getOrderInfo().getId() == null ? 0 : this.getOrderInfo().getId()
                .hashCode());
        result = 37 * result
                + (getStoreInfo().getId() == null ? 0 : this.getStoreInfo().getId().hashCode());
        return result;
    }
}
