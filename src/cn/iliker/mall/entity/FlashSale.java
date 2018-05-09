package cn.iliker.mall.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * 商品活动
 * Created by WDHTC on 2016/3/8.
 */
public class FlashSale implements java.io.Serializable {
    private Integer fid;//序列
    private Integer startTime;//开始时间
    private Integer endTime;//结束时间
    private Boolean status;//状态
    private Integer count;//数量
    private double discount;//折扣额
    private Goods goods;

    public FlashSale() {
    }

    public FlashSale(Integer startTime, Integer endTime, Integer count, double discount, Goods goods) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.count = count;
        this.discount = discount;
        this.goods = goods;
    }

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }
}
