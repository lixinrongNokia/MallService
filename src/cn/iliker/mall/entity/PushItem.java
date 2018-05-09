package cn.iliker.mall.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 快递鸟推送单个物流单信息
 */
public class PushItem implements Serializable {
    private Integer Push_ItemId;
    private String EBusinessID;//商户ID
    private String OrderCode;//订单编号
    private String ShipperCode;//快递公司编码
    private String LogisticCode;//快递单号
    private Boolean Success;//成功与否：true,false
    private String Reason;//失败原因
    private String State;//物流状态: 0-无轨迹，1-已揽收，2-在途中 201-到达派件城市，3-签收,4-问题件
    private String CallBack;//订阅接口的Bk值
    private KdniaoPushResult kdniaoPushResult;
    private List<Dynamicitem> Traces = new ArrayList<>();//

    public Integer getPush_ItemId() {
        return Push_ItemId;
    }

    public void setPush_ItemId(Integer push_ItemId) {
        Push_ItemId = push_ItemId;
    }

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getShipperCode() {
        return ShipperCode;
    }

    public void setShipperCode(String shipperCode) {
        ShipperCode = shipperCode;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCallBack() {
        return CallBack;
    }

    public void setCallBack(String callBack) {
        CallBack = callBack;
    }

    public List<Dynamicitem> getTraces() {
        return Traces;
    }

    public void setTraces(List<Dynamicitem> traces) {
        Traces = traces;
    }

    public KdniaoPushResult getKdniaoPushResult() {
        return kdniaoPushResult;
    }

    public void setKdniaoPushResult(KdniaoPushResult kdniaoPushResult) {
        this.kdniaoPushResult = kdniaoPushResult;
    }
}