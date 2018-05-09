package cn.iliker.mall.entity;


import com.opensymphony.xwork2.util.KeyProperty;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.hibernate.search.annotations.*;
import org.hibernate.search.bridge.builtin.DoubleBridge;
import org.hibernate.search.bridge.builtin.IntegerBridge;

import java.util.*;

/**
 * Goods entity. @author MyEclipse Persistence Tools
 */
@Indexed(index = "goods")
@Analyzer(impl = SmartChineseAnalyzer.class)//分词器
public class Goods implements java.io.Serializable {
    // Fields
    @DocumentId
    private Integer id;
    @IndexedEmbedded
    private Clothestype clothestype;//二级类别
    @Field(index = Index.YES, analyze = Analyze.NO, store = Store.YES)
    private String goodCode;//商品编号
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    private String goodName;//商品名称
    @Field(index = Index.NO, analyze = Analyze.YES, store = Store.YES)
    private String goodsDesc;//详细说明
    @Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES)
    @FieldBridge(impl = DoubleBridge.class)
    private Double price;//单价
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    private String imgpath;//封面大图
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    private String illustrations;//详情图
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    @FieldBridge(impl = IntegerBridge.class)
    private Integer sales;//销量
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    private String marketDate;//上架时间
    @IndexedEmbedded
    private Brand brand;//所属品牌
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    @FieldBridge(impl = DoubleBridge.class)
    private Double divided_into;//分成比列1-0.1
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    private String colors;//颜色
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    private String sizes;//尺寸
    @Field(index = Index.NO, analyze = Analyze.NO, store = Store.YES)
    @FieldBridge(impl = IntegerBridge.class)
    private Integer stock;//库存
    private Boolean visible = true;//是否可见
    @KeyProperty("stockId")
    private Set<StockInfo> stockInfoSet = new LinkedHashSet<>();

    // Constructors

    /**
     * default constructor
     */
    public Goods() {
    }

    public Goods(Integer id, String goodName, Double price, Integer sales, String imgpath) {
        this.id = id;
        this.goodName = goodName;
        this.price = price;
        this.sales = sales;
        this.imgpath = imgpath;
    }

    /**
     * minimal constructor
     */
    public Goods(Clothestype clothestype, String goodCode, String goodName,
                 String goodsDesc, Double price, String imgpath, String marketDate) {
        this.clothestype = clothestype;
        this.goodCode = goodCode;
        this.goodName = goodName;
        this.goodsDesc = goodsDesc;
        this.price = price;
        this.imgpath = imgpath;
        this.marketDate = marketDate;
    }

    /**
     * full constructor
     */
    public Goods(Clothestype clothestype, String goodCode, String goodName,
                 String goodsDesc, Double price, String imgpath,
                 String illustrations, Integer sales, String marketDate) {
        this.clothestype = clothestype;
        this.goodCode = goodCode;
        this.goodName = goodName;
        this.goodsDesc = goodsDesc;
        this.price = price;
        this.imgpath = imgpath;
        this.illustrations = illustrations;
        this.sales = sales;
        this.marketDate = marketDate;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Clothestype getClothestype() {
        return this.clothestype;
    }

    public void setClothestype(Clothestype clothestype) {
        this.clothestype = clothestype;
    }

    public String getGoodCode() {
        return this.goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return this.goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodsDesc() {
        return this.goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgpath() {
        return this.imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getIllustrations() {
        return this.illustrations;
    }

    public void setIllustrations(String illustrations) {
        this.illustrations = illustrations;
    }

    public Integer getSales() {
        return this.sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getMarketDate() {
        return marketDate;
    }

    public void setMarketDate(String marketDate) {
        this.marketDate = marketDate;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Double getDivided_into() {
        return divided_into;
    }

    public void setDivided_into(Double divided_into) {
        this.divided_into = divided_into;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Set<StockInfo> getStockInfoSet() {
        return stockInfoSet;
    }

    public void setStockInfoSet(Set<StockInfo> stockInfoSet) {
        this.stockInfoSet = stockInfoSet;
    }
}