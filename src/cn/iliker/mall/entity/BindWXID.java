package cn.iliker.mall.entity;

public class BindWXID implements java.io.Serializable{
    private String unionID;
    private Userinfo userinfo;

    public BindWXID() {
    }

    public BindWXID(String unionID, Userinfo userinfo) {
        this.unionID = unionID;
        this.userinfo = userinfo;
    }

    public String getUnionID() {
        return unionID;
    }

    public void setUnionID(String unionID) {
        this.unionID = unionID;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }
    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof BindWXID))
            return false;
        BindWXID castOther = (BindWXID) other;

        return ((this.getUserinfo().getUid().equals(castOther.getUserinfo().getUid()) || (this
                .getUserinfo().getUid() != null && castOther.getUserinfo().getUid() != null && this
                .getUserinfo().getUid().equals(castOther.getUserinfo().getUid())))
                && ((this.getUnionID().equals(castOther.getUnionID())) || (this.getUnionID() != null
                && castOther.getUnionID() != null && this.getUnionID()
                .equals(castOther.getUnionID()))));
    }

    public int hashCode() {
        int result = 17;
        result = 37
                * result
                + (getUserinfo().getUid() == null ? 0 : getUserinfo().getUid()
                .hashCode());
        result = 37 * result
                + (getUnionID() == null ? 0 : getUnionID().hashCode());
        return result;
    }
}
