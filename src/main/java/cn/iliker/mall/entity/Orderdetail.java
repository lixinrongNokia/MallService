package cn.iliker.mall.entity;

/**
 * Orderdetail entity. @author MyEclipse Persistence Tools
 */

public class Orderdetail implements java.io.Serializable {

    // Fields

    private Integer id;
    private Goods goods;
    private Double productPrice;//商品销售价
    private Integer orderamount;
    private TOrder TOrder;
    private String color;
    private String size;

    // Constructors

    /**
     * default constructor
     */
    public Orderdetail() {
    }

    /**
     * full constructor
     */
    public Orderdetail(Goods goods, TOrder TOrder, Integer orderamount, String color, String size) {
        this.goods = goods;
        this.TOrder = TOrder;
        this.orderamount = orderamount;
        this.color = color;
        this.size = size;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Goods getGoods() {
        return this.goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public TOrder getTOrder() {
        return this.TOrder;
    }

    public void setTOrder(TOrder TOrder) {
        this.TOrder = TOrder;
    }

    public Integer getOrderamount() {
        return this.orderamount;
    }

    public void setOrderamount(Integer orderamount) {
        this.orderamount = orderamount;
    }

    public Double getSaletotalprice() {
        return productPrice * orderamount;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}