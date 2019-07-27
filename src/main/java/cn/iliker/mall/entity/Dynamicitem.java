package cn.iliker.mall.entity;

import java.io.Serializable;

/**
 * 快递鸟推送单个物流单信息每个时间段状态信息
 */
public class Dynamicitem implements Serializable {
    private Integer dynamicId;
    private String AcceptTime;//时间
    private String AcceptStation;//描述
    private String Remark;//备注
    private PushItem push_item;

    public Integer getDynamicId() {
        return dynamicId;
    }

    public void setDynamicId(Integer dynamicId) {
        this.dynamicId = dynamicId;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public PushItem getPush_item() {
        return push_item;
    }

    public void setPush_item(PushItem push_item) {
        this.push_item = push_item;
    }
}
