package cn.iliker.mall.entity;


import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import com.opensymphony.xwork2.util.KeyProperty;

public class StoreStockInfo implements java.io.Serializable {
    private Integer storeStockId;
    private Goods goods;
    private String color;//颜色文字
    private StoreInfo storeInfo;
    private Timestamp addTime;
    @KeyProperty("stockItemId")
    private Set<StoreStockItem> stockItems = new LinkedHashSet<>();

    public Integer getStoreStockId() {
        return storeStockId;
    }

    public void setStoreStockId(Integer storeStockId) {
        this.storeStockId = storeStockId;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Timestamp getAddTime() {
        return addTime;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    public Set<StoreStockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(Set<StoreStockItem> stockItems) {
        this.stockItems = stockItems;
    }
}
