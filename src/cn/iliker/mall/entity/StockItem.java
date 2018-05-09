package cn.iliker.mall.entity;

public class StockItem implements java.io.Serializable {
    private Integer stockItemId;
//    private StockInfo stockInfo;
    private String size;//尺寸
    private Integer stockCount = 0;//库存数量

    public Integer getStockItemId() {
        return stockItemId;
    }

    public void setStockItemId(Integer stockItemId) {
        this.stockItemId = stockItemId;
    }

//    public StockInfo getStockInfo() {
//        return stockInfo;
//    }
//
//    public void setStockInfo(StockInfo stockInfo) {
//        this.stockInfo = stockInfo;
//    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }
}
