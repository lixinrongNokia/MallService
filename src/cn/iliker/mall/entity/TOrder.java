package cn.iliker.mall.entity;

import cn.iliker.mall.entity.stateattr.PayMethod;
import cn.iliker.mall.entity.stateattr.PostMethod;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * TOrder entity. @author MyEclipse Persistence Tools
 */
public class TOrder implements java.io.Serializable {

    // Fields

    private Integer id;//主键
    private String phone;//用户电话
    private String orderid;//订单号
    private Timestamp orderdate;//订购时间
    private Integer orderamount;//订购数量
    private String message;//买家留言
    private String postmethod = PostMethod.NORMAL.getName();//配送方式
    private String paymethod = PayMethod.ONLING.getName();//付款方式
    private String paymentTool;//付款方式
    private Boolean paymentstate;//支付状态
    private String recevername;//收件人姓名
    private String receveraddr;//收货地址
    private String recevertel;//联系电话
    private String memo;//备注
    private Double goodsTotalPrice = 0.00;//商品总金额
    private Double deliverFee = 0.00;//配送费
    private Double toalprice;//订单总金额
    private String orderstate;//订单状态
    private String tradeNo;//交易号第三方支付工具返回的交易成功凭证
    private StoreInfo storeInfo;//用户到店自提门店
    private String commission;//订单完成后可分佣信息
    private Boolean sys_flag;//记录订单是否后台处理过
    private Set<Orderdetail> orderdetails = new HashSet(0);
    private Set logisticses = new HashSet(0);
    private Set<UnpackOrder> unpackOrders = new HashSet<>();
    private Transfer transfer;

    // Constructors

    /**
     * default constructor
     */
    public TOrder() {
    }

    public TOrder(Integer id) {
        this.id = id;
    }

    /**
     * minimal constructor
     */
    public TOrder(String phone, String orderid, Timestamp orderdate,
                  Integer orderamount, String postmethod, String paymethod,
                  String recevername, String receveraddr, String recevertel, Double toalprice) {
        this.phone = phone;
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.orderamount = orderamount;
        this.postmethod = postmethod;
        this.paymethod = paymethod;
        this.recevername = recevername;
        this.receveraddr = receveraddr;
        this.recevertel = recevertel;
        this.toalprice = toalprice;
    }

    /**
     * full constructor
     */
    public TOrder(String phone, String orderid, Timestamp orderdate,
                  Integer orderamount, String message, String postmethod,
                  String paymethod, String recevername, String receveraddr,
                  String recevertel, String memo, Double toalprice, String tradeNo, Set orderdetails,
                  Set logisticses) {
        this.phone = phone;
        this.orderid = orderid;
        this.orderdate = orderdate;
        this.orderamount = orderamount;
        this.message = message;
        this.postmethod = postmethod;
        this.paymethod = paymethod;
        this.recevername = recevername;
        this.receveraddr = receveraddr;
        this.recevertel = recevertel;
        this.memo = memo;
        this.toalprice = toalprice;
        this.tradeNo = tradeNo;
        this.orderdetails = orderdetails;
        this.logisticses = logisticses;
    }

    // Property accessors

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOrderid() {
        return this.orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public Timestamp getOrderdate() {
        return this.orderdate;
    }

    public void setOrderdate(Timestamp orderdate) {
        this.orderdate = orderdate;
    }

    public Integer getOrderamount() {
        return this.orderamount;
    }

    public void setOrderamount(Integer orderamount) {
        this.orderamount = orderamount;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPostmethod() {
        return this.postmethod;
    }

    public void setPostmethod(String postmethod) {
        this.postmethod = postmethod;
    }

    public String getPaymethod() {
        return this.paymethod;
    }

    public void setPaymethod(String paymethod) {
        this.paymethod = paymethod;
    }

    public String getPaymentTool() {
        return paymentTool;
    }

    public void setPaymentTool(String paymentTool) {
        this.paymentTool = paymentTool;
    }

    public Boolean getPaymentstate() {
        return paymentstate;
    }

    public void setPaymentstate(Boolean paymentstate) {
        this.paymentstate = paymentstate;
    }

    public String getRecevername() {
        return this.recevername;
    }

    public void setRecevername(String recevername) {
        this.recevername = recevername;
    }

    public String getReceveraddr() {
        return this.receveraddr;
    }

    public void setReceveraddr(String receveraddr) {
        this.receveraddr = receveraddr;
    }

    public String getRecevertel() {
        return this.recevertel;
    }

    public void setRecevertel(String recevertel) {
        this.recevertel = recevertel;
    }

    public StoreInfo getStoreInfo() {
        return storeInfo;
    }

    public void setStoreInfo(StoreInfo storeInfo) {
        this.storeInfo = storeInfo;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public Boolean getSys_flag() {
        return sys_flag;
    }

    public void setSys_flag(Boolean sys_flag) {
        this.sys_flag = sys_flag;
    }

    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Double getToalprice() {
        return this.toalprice;
    }

    public void setToalprice(Double toalprice) {
        this.toalprice = toalprice;
    }

    public String getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(String orderstate) {
        this.orderstate = orderstate;
    }

    public String getTradeNo() {
        return this.tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Set<Orderdetail> getOrderdetails() {
        return this.orderdetails;
    }

    public void setOrderdetails(Set<Orderdetail> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public Set getLogisticses() {
        return this.logisticses;
    }

    public void setLogisticses(Set logisticses) {
        this.logisticses = logisticses;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public Double getGoodsTotalPrice() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(Double goodsTotalPrice) {
        this.goodsTotalPrice = goodsTotalPrice;
    }

    public Double getDeliverFee() {
        return deliverFee;
    }

    public void setDeliverFee(Double deliverFee) {
        this.deliverFee = deliverFee;
    }

    public Set<UnpackOrder> getUnpackOrders() {
        return unpackOrders;
    }

    public void setUnpackOrders(Set<UnpackOrder> unpackOrders) {
        this.unpackOrders = unpackOrders;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof StoreInfo))
            return false;
        TOrder castOther = (TOrder) other;

        return ((Objects.equals(this.getId(), castOther.getId())) || (this
                .getId() != null && castOther.getId() != null && this
                .getId().equals(castOther.getId())));
    }

    public int hashCode() {
        int result = 17;

        result = 37
                * result
                + (getId() == null ? 0 : this.getId()
                .hashCode());
        return result;
    }
}