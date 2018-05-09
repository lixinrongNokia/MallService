package cn.iliker.mall.control;

import cn.iliker.mall.entity.*;
import cn.iliker.mall.entity.stateattr.OrderState;
import cn.iliker.mall.entity.stateattr.PostMethod;
import cn.iliker.mall.interceptor.Permission;
import cn.iliker.mall.service.ICommonQuerySvc;
import cn.iliker.mall.service.ICourierSvc;
import cn.iliker.mall.service.ITOrderSvc;
import cn.iliker.mall.service.ITransferSvc;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static cn.iliker.mall.alipay.util.UtilDate.getOrderNum;

public class OrderManagerAction extends ActionSupport {
    private int id;//订单id
    private String orderid;//订单号
    private ITOrderSvc tordersvc;
    private int offset;
    private int pagesize = 0;
    private int totalPage;
    private int totalSize;
    private String propertyName;
    private String queryVal;
    private String propertyName2;
    private String queryVal2;
    //    private String orderDetails;
    private String orderInfo;
    private JSONObject msg = new JSONObject();
    private ICommonQuerySvc querySvc;
    private String where_clause;
    private String order_clause;
    private String requestCode;
    private TOrder tOrder;
    private ITransferSvc transferSvc;

    public void setTransferSvc(ITransferSvc transferSvc) {
        this.transferSvc = transferSvc;
    }

    public TOrder gettOrder() {
        return tOrder;
    }

    public void settOrder(TOrder tOrder) {
        this.tOrder = tOrder;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public void setWhere_clause(String where_clause) {
        this.where_clause = where_clause;
    }

    public void setOrder_clause(String order_clause) {
        this.order_clause = order_clause;
    }

    public String getQueryVal2() {
        return queryVal2;
    }

    public void setQueryVal2(String queryVal2) {
        this.queryVal2 = queryVal2;
    }

    public String getPropertyName2() {
        return propertyName2;
    }

    public void setPropertyName2(String propertyName2) {
        this.propertyName2 = propertyName2;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    private ICourierSvc couriersvc;

    public void setQuerySvc(ICommonQuerySvc querySvc) {
        this.querySvc = querySvc;
    }

    public void setCouriersvc(ICourierSvc couriersvc) {
        this.couriersvc = couriersvc;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getQueryVal() {
        return queryVal;
    }

    public void setQueryVal(String queryVal) {
        this.queryVal = queryVal;
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public void setTordersvc(ITOrderSvc tordersvc) {
        this.tordersvc = tordersvc;
    }

    private int starttime;

    public void setStarttime(int starttime) {
        this.starttime = starttime;
    }

    public String queryOrder() throws Exception {
        if (offset <= 0) {
            offset = 1;
        }
        GeneralList<TOrder> orders = tordersvc.findAllByProperty(offset, 5, propertyName, queryVal);
        if (orders != null && orders.getList().size() > 0) {
            totalSize = orders.getTotalSize();
            totalPage = orders.getTotalPage();
            ActionContext.getContext().put("orders", orders.getList());
            queryVal = "";
            return SUCCESS;
        }
        propertyName = null;
        queryVal = null;
        return NONE;
    }

    public String findByorderid() {
        TOrder orderinfo = tordersvc.findById(id);
        if (orderinfo != null) {
            ActionContext.getContext().put("orderinfo", orderinfo);
            id = 0;
            return SUCCESS;
        }
        return ERROR;
    }

    public String inputPostInfo() {
        List<CourierCompany> list = couriersvc.findAll();
        if (!list.isEmpty()) {
            ActionContext.getContext().put("couriercompanys", list);
            return SUCCESS;
        }
        return NONE;
    }

    public String addOrder() {
        msg.clear();
        TOrder tOrder = parseOrder(orderInfo);
        if (tOrder != null && !tOrder.getOrderdetails().isEmpty()) {
            try {
                tordersvc.save(tOrder);
                msg.put("result_code", "SUCCESS");
                msg.put("data", tOrder.getOrderid());
                if (requestCode != null) {
                    msg.put("requestCode", Integer.parseInt(requestCode));
                }
                msg.put("totalPrice", tOrder.getToalprice());
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.put("result_code", "FAIL");
        return ERROR;
    }

    private static TOrder parseOrder(String orderInfo) {
        TOrder tOrder = JSON.parseObject(orderInfo, TOrder.class);
        tOrder.setOrderdate(new Timestamp(new Date().getTime()));
        tOrder.setOrderid("c" + getOrderNum());
        for (Orderdetail orderdetail : tOrder.getOrderdetails()) {
            orderdetail.setTOrder(tOrder);
        }
        return tOrder;
    }


    /*查询订单*/
    public String queryOrderByProperty() {
        msg.clear();
        String _where2 = "";
        if (propertyName2 != null) {
            _where2 = " and torder." + propertyName2 + "='" + queryVal2 + "'";
        }
        msg = querySvc.getAll("torder.id,torder.orderid,torder.orderamount,torder.toalprice,torder.recevername,torder.receveraddr,torder.recevertel,torder.orderstate,torder.orderdate,torder.postmethod,torder.paymethod,torder.paymentstate,GROUP_CONCAT(goods.imgpath) imgpath", "t_order torder join orderdetail detail on (torder.id=detail.orderid) join goods on (detail.goodid=goods.id)",
                "torder." + propertyName + "='" + queryVal + "'" + _where2, "torder.id", "torder.orderdate desc", offset, pagesize == 0 ? 20 : pagesize);
        if (msg != null) {
            return SUCCESS;
        }
        return NONE;
    }

    /*查询同类目下的商品*/
    public String loadProductList() {
        msg.clear();
        msg = querySvc.getAll("id,goodCode,goodName,goodsDesc,price,imgpath,illustrations,sales,market_date,divided_into,colors,sizes,stock", "goods", where_clause, null, order_clause, offset, pagesize == 0 ? 20 : pagesize);
        if (msg != null) {
            return SUCCESS;
        }
        return NONE;
    }

    /*查询限时优惠商品*/
    public String loadTimelimitList() {
        msg.clear();
        JSONObject rootJSON = querySvc.getAll("f.fid, g.id,g.goodCode,g.goodName,g.goodsDesc,g.price,g.imgpath,g.illustrations,g.sales,g.colors,g.sizes,f.number,f.`STATUS`,f.discount", "flashsale f join goods g on (f.goodid=g.id)", "starttime=" + starttime, null, "g.price", offset, count);
        if (rootJSON != null) {
            JSONArray jsonArray = rootJSON.getJSONArray("dataSet");
            JSONArray saleses = new JSONArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONObject goodsJSON = new JSONObject();
                JSONObject flashsaleJSON = new JSONObject();
                goodsJSON.put("id", jsonObject.getInteger("id"));
                goodsJSON.put("goodCode", jsonObject.getString("goodCode"));
                goodsJSON.put("goodName", jsonObject.getString("goodName"));
                goodsJSON.put("goodsDesc", jsonObject.getString("goodsDesc"));
                goodsJSON.put("price", jsonObject.getDouble("price"));
                goodsJSON.put("imgpath", jsonObject.getString("imgpath"));
                String illustrations = jsonObject.getString("illustrations");
                goodsJSON.put("illustrations", illustrations == null ? "" : illustrations);
                goodsJSON.put("sales", jsonObject.getInteger("sales"));
                goodsJSON.put("colors", jsonObject.getString("colors"));
                goodsJSON.put("sizes", jsonObject.getString("sizes"));
                flashsaleJSON.put("fid", jsonObject.getInteger("fid"));
                flashsaleJSON.put("number", jsonObject.getInteger("number"));
                flashsaleJSON.put("STATUS", jsonObject.getInteger("STATUS"));
                flashsaleJSON.put("discount", jsonObject.getDouble("discount"));
                flashsaleJSON.put("goods", goodsJSON);
                saleses.add(flashsaleJSON);
            }
            msg.put("saleses", saleses);
            msg.put("totalSize", rootJSON.getInteger("totalSize"));
            msg.put("pageCount", rootJSON.getInteger("pageCount"));
            return SUCCESS;
        }
        return NONE;
    }

    public String orderDetail() {
        msg.clear();
        TOrder orderinfo = null;
        if (id > 0) {
            orderinfo = tordersvc.findById(id);
        }
        if (orderinfo != null) {
            msg.put("orderInfo", paseOrderJSON(orderinfo));
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    private static JSONObject paseOrderJSON(TOrder orderinfo) {
        JSONObject orderJson = new JSONObject();
        Set<Orderdetail> orderdetails = orderinfo.getOrderdetails();
        JSONArray orderItemJson = new JSONArray();
        for (Orderdetail orderdetail : orderdetails) {
            Goods goods = orderdetail.getGoods();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderamount", orderdetail.getOrderamount());
            jsonObject.put("color", orderdetail.getColor());
            jsonObject.put("size", orderdetail.getSize());
            jsonObject.put("productprice", orderdetail.getProductPrice());
            jsonObject.put("goodname", goods.getGoodName());
            jsonObject.put("imgpath", goods.getImgpath());
            jsonObject.put("goodCode", goods.getGoodCode());
            jsonObject.put("goodsid", goods.getId());
            orderItemJson.add(jsonObject);
        }
        orderJson.put("orderItem", orderItemJson);
        orderJson.put("id", orderinfo.getId());
        orderJson.put("orderdate", orderinfo.getOrderdate());
        orderJson.put("orderstate", orderinfo.getOrderstate());
        orderJson.put("paymentstate", orderinfo.getPaymentstate());
        orderJson.put("orderid", orderinfo.getOrderid());
        orderJson.put("toalprice", orderinfo.getToalprice());
        orderJson.put("goodsTotalPrice", orderinfo.getGoodsTotalPrice());
        orderJson.put("deliverFee", orderinfo.getDeliverFee());
        orderJson.put("paymethod", orderinfo.getPaymethod());
        orderJson.put("postmethod", orderinfo.getPostmethod());
        orderJson.put("sys_flag", orderinfo.getSys_flag());
        if (orderinfo.getPostmethod().equals(PostMethod.POINT.getName())) {
            StoreInfo storeInfo = orderinfo.getStoreInfo();
            if (storeInfo != null) {
                JSONObject storeJson = new JSONObject();
                storeJson.put("id", storeInfo.getId());
                storeJson.put("loginEmail", storeInfo.getLoginEmail());
                storeJson.put("address", storeInfo.getAddress());
                storeJson.put("storeName", storeInfo.getStoreName());
                storeJson.put("tell", storeInfo.getTell());
                storeJson.put("latitude", storeInfo.getLatitude());
                storeJson.put("longitude", storeInfo.getLongitude());
                storeJson.put("faceIcon", storeInfo.getFaceIcon());
                orderJson.put("storeInfo", storeJson);
            }
        } else {
            orderJson.put("receveraddr", orderinfo.getReceveraddr());
            orderJson.put("recevername", orderinfo.getRecevername());
            orderJson.put("recevertel", orderinfo.getRecevertel());
        }
        return orderJson;
    }

    /*后台取消订单*/
    @Permission(module = "order", privilege = "cancelOrder")
    public String cancelOrder() {
        msg.clear();
        TOrder tOrder = tordersvc.findById(id);
        if (tOrder != null) {
            tOrder.setOrderstate(OrderState.CANCEL.getName());
            try {
                tordersvc.save(tOrder);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /*财务确认已付款*/
    @Permission(module = "order", privilege = "confirmPayment")
    public String confirmPayment() {
        TOrder tOrder = tordersvc.findById(id);
        if (tOrder != null && OrderState.CONFIRMORDERED.getName().equals(tOrder.getOrderstate())) {
            tOrder.setOrderstate(OrderState.ADMEASUREPRODUCT.getName());
            try {
                tordersvc.save(tOrder);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /*原路退款*/
    /*@Permission(module = "order", privilege = "quickRefund")
    public String refunds() {
        msg.clear();
        TOrder order = tordersvc.findById(id);
        try {
            if (OrderState.REFUNDING.getName().equals(order.getOrderstate())) {
                iTransferDetailSvc.refunds(order);
                msg.put("success", true);
                return SUCCESS;
            }
        } catch (Exception e) {
            msg.put("success", false);
            msg.put("msg", e.getMessage());
        }
        return ERROR;
    }*/
    /*退款至app账户*/
    @Permission(module = "order", privilege = "refundAppAccount")
    public String transferAppAccount() {
        msg.clear();
        TOrder foundOrder = tordersvc.findById(id);
        try {
            if (OrderState.REFUNDING.getName().equals(foundOrder.getOrderstate())) {
                transferSvc.transferAppAccount(foundOrder);
                msg.put("success", true);
                return SUCCESS;
            }
        } catch (Exception e) {
            msg.put("success", false);
            msg.put("msg", e.getMessage());
        }
        return ERROR;
    }

    /*完成备货转等待发货*/
    @Permission(module = "order", privilege = "turnWaitdeliver")
    public String prepareDelivery() {
        TOrder tOrder = tordersvc.findById(id);
        if (tOrder != null && OrderState.ADMEASUREPRODUCT.getName().equals(tOrder.getOrderstate())) {
            tOrder.setOrderstate(OrderState.WAITDELIVER.getName());
            try {
                tordersvc.save(tOrder);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /*待审核转审核通过*/
    @Permission(module = "order", privilege = "confirmOrder")
    public String confirmOrdering() {
        TOrder tOrder = tordersvc.findById(id);
        if (tOrder != null && OrderState.WAITCONFIRM.getName().equals(tOrder.getOrderstate())) {
            tOrder.setOrderstate(OrderState.CONFIRMORDERED.getName());
            try {
                tordersvc.save(tOrder);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return ERROR;
    }

    /*客户取消订单*/
    public String userCancelOrder() {
        msg.clear();
        TOrder foundOrder = tordersvc.findById(tOrder.getId());
        foundOrder.setMemo(tOrder.getMemo());
        if (Arrays.asList(OrderState.allowsCancel).contains(foundOrder.getOrderstate())) {
            if (foundOrder.getPaymentstate()) {
                foundOrder.setOrderstate(OrderState.REFUNDING.getName());
            } else {
                foundOrder.setOrderstate(OrderState.CANCEL.getName());
            }
            try {
                tordersvc.save(foundOrder);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        msg.put("msg", "不符合取消订单条件");
        msg.put("success", false);
        return ERROR;
    }

    /*财务拒绝退款*/
    public String rejectRefunds() {
        TOrder foundOrder = tordersvc.findById(tOrder.getId());
        if (foundOrder != null) {
            foundOrder.setMemo(tOrder.getMemo());
            try {
                tordersvc.save(foundOrder);
                msg.put("success", true);
                return SUCCESS;
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

}
