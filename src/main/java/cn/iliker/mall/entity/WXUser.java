package cn.iliker.mall.entity;

import java.io.Serializable;

public class WXUser implements Serializable {
    private BindWXID bindWXID;

    public WXUser() {
    }

    public WXUser(String unionID, Userinfo userinfo) {
        this.bindWXID = new BindWXID(unionID, userinfo);
    }

    public BindWXID getBindWXID() {
        return bindWXID;
    }

    public void setBindWXID(BindWXID bindWXID) {
        this.bindWXID = bindWXID;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bindWXID == null) ? 0 : bindWXID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final WXUser other = (WXUser) obj;
        if (bindWXID == null) {
            if (other.bindWXID != null)
                return false;
        } else if (!bindWXID.equals(other.bindWXID))
            return false;
        return true;
    }
}
