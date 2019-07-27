package cn.iliker.mall.entity;

import java.sql.Timestamp;

/**
 * Comments entity. @author MyEclipse Persistence Tools
 */

public class Comments implements java.io.Serializable {

    // Fields

    private Integer id;
    private Share share;
    private String nickname;
    private String commaudiopath;
    private String commtext;
    private Timestamp commtime;

    // Constructors

    /**
     * default constructor
     */
    public Comments() {
    }

    /**
     * minimal constructor
     */
    public Comments(Share share, String nickname, Timestamp commtime) {
        this.share = share;
        this.nickname = nickname;
        this.commtime = commtime;
    }

    /**
     * full constructor
     */
    public Comments(Share share, String nickname, String commaudiopath,
                    String commtext, Timestamp commtime) {
        this.share = share;
        this.nickname = nickname;
        this.commaudiopath = commaudiopath;
        this.commtext = commtext;
        this.commtime = commtime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Share getShare() {
        return this.share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCommaudiopath() {
        return this.commaudiopath;
    }

    public void setCommaudiopath(String commaudiopath) {
        this.commaudiopath = commaudiopath;
    }

    public String getCommtext() {
        return this.commtext;
    }

    public void setCommtext(String commtext) {
        this.commtext = commtext;
    }

    public Timestamp getCommtime() {
        return this.commtime;
    }

    public void setCommtime(Timestamp commtime) {
        this.commtime = commtime;
    }

}