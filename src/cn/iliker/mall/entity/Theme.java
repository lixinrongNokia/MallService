package cn.iliker.mall.entity;

import java.io.Serializable;

public class Theme implements Serializable {
    private Integer themeid;
    private String themeName;
    private String introduction;
    private String themeURL;
    private String startTime;
    private String endTime;

    public Integer getThemeid() {
        return themeid;
    }

    public void setThemeid(Integer themeid) {
        this.themeid = themeid;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getThemeURL() {
        return themeURL;
    }

    public void setThemeURL(String themeURL) {
        this.themeURL = themeURL;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
