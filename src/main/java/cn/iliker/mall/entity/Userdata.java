package cn.iliker.mall.entity;

import java.sql.Timestamp;

/**
 * Userdata entity. @author MyEclipse Persistence Tools
 */

public class Userdata implements java.io.Serializable {

    // Fields

    private Integer id;
    private Userinfo userinfo;
    private Double height;//身高
    private Double onchest;//上胸围
    private Double underchest;//下胸围
    private Double waist;//腰围值
    private Double hip;//臀围值
    private String weight;//体重
    private String bodytype;//身型名称
    private Double bmi;//身高体重比
    private String cuptype;//杯型
    private String tags;//标签
    private String pants;//裤码
    private Double shoulderWidth;//肩宽
    private Timestamp syntime;

    // Constructors

    /**
     * default constructor
     */
    public Userdata() {
    }

    public Userdata(Double height, Double onchest, Double underchest, Double waist, Double hip, String weight) {
        this.height = height;
        this.onchest = onchest;
        this.underchest = underchest;
        this.waist = waist;
        this.hip = hip;
        this.weight = weight;
    }

    /**
     * minimal constructor
     */
    public Userdata(Timestamp syntime) {
        this.syntime = syntime;
    }

    /**
     * full constructor
     */
    public Userdata(Userinfo userinfo, Double height, Double onchest,
                    Double underchest, Double waist, Double hip, String weight,
                    String bodytype, Double bmi, String cuptype, String tags, String pants, Timestamp syntime) {
        this.userinfo = userinfo;
        this.height = height;
        this.onchest = onchest;
        this.underchest = underchest;
        this.waist = waist;
        this.hip = hip;
        this.weight = weight;
        this.bodytype = bodytype;
        this.bmi = bmi;
        this.cuptype = cuptype;
        this.tags = tags;
        this.pants = pants;
        this.syntime = syntime;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Userinfo getUserinfo() {
        return this.userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Double getHeight() {
        return this.height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getOnchest() {
        return this.onchest;
    }

    public void setOnchest(Double onchest) {
        this.onchest = onchest;
    }

    public Double getUnderchest() {
        return this.underchest;
    }

    public void setUnderchest(Double underchest) {
        this.underchest = underchest;
    }

    public Double getWaist() {
        return this.waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Double getHip() {
        return this.hip;
    }

    public void setHip(Double hip) {
        this.hip = hip;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBodytype() {
        return this.bodytype;
    }

    public void setBodytype(String bodytype) {
        this.bodytype = bodytype;
    }

    public Double getBmi() {
        return this.bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public String getCuptype() {
        return this.cuptype;
    }

    public void setCuptype(String cuptype) {
        this.cuptype = cuptype;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getPants() {
        return this.pants;
    }

    public void setPants(String pants) {
        this.pants = pants;
    }

    public Double getShoulderWidth() {
        return shoulderWidth;
    }

    public void setShoulderWidth(Double shoulderWidth) {
        this.shoulderWidth = shoulderWidth;
    }

    public Timestamp getSyntime() {
        return this.syntime;
    }

    public void setSyntime(Timestamp syntime) {
        this.syntime = syntime;
    }

}