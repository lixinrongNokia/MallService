package cn.iliker.mall.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 快递鸟推送物流信息
 */
public class KdniaoPushResult implements Serializable {
    private Integer PushId;
    private String EBusinessID;//用户电商ID
    private String Count;//推送物流单号轨迹个数
    private String PushTime;//推送时间
    private List<PushItem> Data = new ArrayList<>();//推送物流单号轨迹集合
    public Integer getPushId() {
        return PushId;
    }

    public void setPushId(Integer pushId) {
        PushId = pushId;
    }
    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String count) {
        Count = count;
    }

    public String getPushTime() {
        return PushTime;
    }

    public void setPushTime(String pushTime) {
        PushTime = pushTime;
    }

    public List<PushItem> getData() {
        return Data;
    }

    public void setData(List<PushItem> data) {
        Data = data;
    }
}
