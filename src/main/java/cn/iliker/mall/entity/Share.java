package cn.iliker.mall.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Share entity. @author MyEclipse Persistence Tools
 */

public class Share implements java.io.Serializable {

    // Fields

    private Integer shareId;
    private Userinfo userinfo;
    private String nickname;
    private String content;
    private Timestamp releaseTime;
    private String location;
    private String pic;
    private Integer piccount;
    private Set<Comments> comments = new HashSet<>();

    // Constructors

    /**
     * default constructor
     */
    public Share() {
    }

    /**
     * minimal constructor
     */
    public Share(String nickname, Timestamp releaseTime, Integer piccount) {
        this.nickname = nickname;
        this.releaseTime = releaseTime;
        this.piccount = piccount;
    }

    /**
     * full constructor
     */
    public Share(String nickname, String content, Timestamp releaseTime,
                 String location, String pic, Integer piccount) {
        this.nickname = nickname;
        this.content = content;
        this.releaseTime = releaseTime;
        this.location = location;
        this.pic = pic;
        this.piccount = piccount;
    }

    // Property accessors

    public Integer getShareId() {
        return this.shareId;
    }

    public void setShareId(Integer shareId) {
        this.shareId = shareId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getReleaseTime() {
        return this.releaseTime;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public void setReleaseTime(Timestamp releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPic() {
        return this.pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getPiccount() {
        return this.piccount;
    }

    public void setPiccount(Integer piccount) {
        this.piccount = piccount;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }
}