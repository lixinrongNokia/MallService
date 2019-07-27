package cn.iliker.mall.entity;


import java.util.LinkedHashSet;
import java.util.Set;

import com.opensymphony.xwork2.util.KeyProperty;

public class StockInfo implements java.io.Serializable {
    private Integer stockId;
//    private Goods goods;//所属商品
    private String color;//颜色文字
    private String img;//该颜色图片
    @KeyProperty("stockItemId")
    private Set<StockItem> stockItems = new LinkedHashSet<>();

    public Integer getStockId() {
        return stockId;
    }

    public void setStockId(Integer stockId) {
        this.stockId = stockId;
    }

   /* public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }*/

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Set<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(Set<StockItem> stockItems) {
        this.stockItems = stockItems;
    }
}
