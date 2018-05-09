package cn.iliker.mall.entity;


import java.io.Serializable;
import java.util.Date;
/*微信网页access_token*/
public class JSWXToken implements Serializable {
    private int id;
    private String access_token;
    private int expires_in;//单位（秒）
    private String refresh_token;//刷新access_token
    private String openid;
    private String scope;
    private String unionid;
    private Date create_date;//创建时间
    private String superiornum;

    public JSWXToken() {
    }

    public JSWXToken(String openid, String superiornum) {
        this.openid = openid;
        this.superiornum = superiornum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public String getSuperiornum() {
        return superiornum;
    }

    public void setSuperiornum(String superiornum) {
        this.superiornum = superiornum;
    }
}
