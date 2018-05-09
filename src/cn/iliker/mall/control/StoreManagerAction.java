package cn.iliker.mall.control;

import cn.iliker.mall.entity.*;
import cn.iliker.mall.entity.stateattr.OrderState;
import cn.iliker.mall.push.PushTools;
import cn.iliker.mall.service.*;
import cn.iliker.mall.utils.EmailJob;
import cn.iliker.mall.utils.MD5Util;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static cn.iliker.mall.push.Custom_Signature.CustomSignature.CustomSignature;

public class StoreManagerAction extends ActionSupport {
    private IStoreManagerService managerService;
    private EmailJob testJob;
    private int id;
    private String validateCode;
    private Double mylat;
    private Double mylng;
    private JSONObject msg = new JSONObject();
    private ICommonQuerySvc querySvc;
    private int indexPage;
    private int pageCount;
    private StoreStockSvc storeStockSvc;
    private IGoodsSvc goodsSvc;
    private UnpackOrder unpackOrder;
    private ITOrderSvc orderSvc;

    private String storeStockInfoStr;
    private String brandName;
    private boolean stockStatus;
    private int storeId;
    private TOrder foundOrder;
    private IUserinfoSvc usersvc;
    private String unPackOrderInfo;
    private String confirmBack;
    private String unPackOrderId;
    private IAliYunAccountSvc aliYunAccountSvc;

    public String getUnPackOrderId() {
        return unPackOrderId;
    }

    public void setAliYunAccountSvc(IAliYunAccountSvc aliYunAccountSvc) {
        this.aliYunAccountSvc = aliYunAccountSvc;
    }

    public void setUnPackOrderId(String unPackOrderId) {
        this.unPackOrderId = unPackOrderId;
    }

    public String getConfirmBack() {
        return confirmBack;
    }

    public void setConfirmBack(String confirmBack) {
        this.confirmBack = confirmBack;
    }

    public TOrder getFoundOrder() {
        return foundOrder;
    }

    public String getUnPackOrderInfo() {
        return unPackOrderInfo;
    }

    public void setUnPackOrderInfo(String unPackOrderInfo) {
        this.unPackOrderInfo = unPackOrderInfo;
    }

    public void setFoundOrder(TOrder foundOrder) {
        this.foundOrder = foundOrder;
    }

    public void setUsersvc(IUserinfoSvc usersvc) {
        this.usersvc = usersvc;
    }

    public UnpackOrder getUnpackOrder() {
        return unpackOrder;
    }

    public void setOrderSvc(ITOrderSvc orderSvc) {
        this.orderSvc = orderSvc;
    }

    public void setUnpackOrder(UnpackOrder unpackOrder) {
        this.unpackOrder = unpackOrder;
    }

    public void setGoodsSvc(IGoodsSvc goodsSvc) {
        this.goodsSvc = goodsSvc;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public void setStoreStockSvc(StoreStockSvc storeStockSvc) {
        this.storeStockSvc = storeStockSvc;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setIndexPage(int indexPage) {
        this.indexPage = indexPage;
    }

    public void setQuerySvc(ICommonQuerySvc querySvc) {
        this.querySvc = querySvc;
    }

    public String getStoreStockInfoStr() {
        return storeStockInfoStr;
    }

    public void setStoreStockInfoStr(String storeStockInfoStr) {
        this.storeStockInfoStr = storeStockInfoStr;
    }

    public boolean isStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(boolean stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public void setMylng(Double mylng) {
        this.mylng = mylng;
    }

    public void setMylat(Double mylat) {
        this.mylat = mylat;
    }

    public int getId() {
        return id;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTestJob(EmailJob testJob) {
        this.testJob = testJob;
    }

    public void setManagerService(IStoreManagerService managerService) {
        this.managerService = managerService;
    }

    /*发送激活邮箱链接*/
    public String enableEmail() throws IOException {
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        StoreInfo storeInfo = managerService.findById(id);
        if (storeInfo != null) {
            try {
                storeInfo.setOutTime(new Date().getTime());
                managerService.updateStoreInfo(storeInfo);
                testJob.sendEableTemplateMail(id, storeInfo.getStoreName(), MD5Util.getMD5Str(storeInfo.getStoreName()), storeInfo.getLoginEmail());
                writer.write("ok");
                writer.flush();
                writer.close();
                return NONE;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        writer.write("error");
        writer.flush();
        writer.close();
        return NONE;
    }

    public String validateEnableBack() {
        StoreInfo storeInfo = managerService.findById(id);
        Long nowTime = new Date().getTime();
        if (storeInfo.getVisible() == 1) return ERROR;
        if (storeInfo != null && MD5Util.getMD5Str(storeInfo.getStoreName()).equals(validateCode) && (nowTime - storeInfo.getOutTime()) <= 12 * 60 * 60 * 1000) {
            storeInfo.setVisible(1);
            try {
                managerService.updateStoreInfo(storeInfo);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return ERROR;
    }

    /*货物自提确认*/
    /*public String unPackPushConfirm() throws IOException {
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        JSONObject upOrder = JSONObject.parseObject(unPackOrderInfo);
        try {
            for (int i = 0; i < 10; i++) {
                String back = CustomSignature(upOrder.getString("user_NickName"), "<爱内秀>货物自提确认", upOrder.getString("storeName") + "为你提货服务确认！", "{\"unPackOrderID\":\"" + upOrder.getString("unPackOrderID") + "\",\"storeEmail\":\"" + upOrder.getString("storeEmail") + "\",\"storeName\":\"" + upOrder.getString("storeName") + "\",\"storePic\":\"" + upOrder.getString("storePic") + "\"}", "1", "2", "iliker.mall.UnPackConfirmAcitivity");
                JSONObject jsonObject = JSONObject.parseObject(back);
                if (jsonObject.containsKey("ResponseId")) {
                    break;
                }
            }
            writer.print("success");
            writer.flush();
            writer.close();
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.print("error");
        writer.flush();
        writer.close();
        return NONE;
    }*/
    public String unPackPushConfirm() throws IOException {
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        JSONObject upOrder = JSONObject.parseObject(unPackOrderInfo);
        try {
            for (int i = 0; i < 10; i++) {
                String back = CustomSignature(upOrder.getString("user_NickName"), "<爱内秀>货物自提确认", upOrder.getString("storeName") + "为你提货服务确认！", "{\"unPackOrderID\":\"" + upOrder.getString("unPackOrderID") + "\",\"storeEmail\":\"" + upOrder.getString("storeEmail") + "\",\"storeName\":\"" + upOrder.getString("storeName") + "\",\"storePic\":\"" + upOrder.getString("storePic") + "\"}", "1", "2", "iliker.mall.UnPackConfirmAcitivity");
                JSONObject jsonObject = JSONObject.parseObject(back);
                if (jsonObject.containsKey("ResponseId")) {
                    break;
                }
            }
            writer.print("success");
            writer.flush();
            writer.close();
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.print("error");
        writer.flush();
        writer.close();
        return NONE;
    }

    /*门店转单通知*/
    /*public String confirmUnPackBack() throws IOException {
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        JSONObject object = JSONObject.parseObject(confirmBack);
        String pushEmail = object.getString("storeEmail");
        String nickName = object.getString("nickName");
        String unPackOrderID = object.getString("unPackOrderID");
        try {
            for (int i = 0; i < 10; i++) {
                String responseId = PushTools.pushMessageToAndroid_toAll(pushEmail, "{\"messageTitle\":\"爱内秀订单通知\",\"messageContent\":\"系统已经接到你的订单，很快会为你处理！\",\"receiver\":\"" + pushEmail + "\",\"targetActivity\":\"cn.iliker.mall.storemodule.ViewUnPackOrderActivity\",\"unPackOrderID\":\"" + unPackOrderID + "\"}");
                if (responseId != null) {
                    break;
                }
            }
            CustomSignature(pushEmail, "<爱内秀>提货完成通知", "感谢您的服务！", null, "1", "2", "cn.iliker.mall.storemodule.UnPackOrderActivity");
            PushTools.pushMessageToAndroid_toAll(nickName, "{\"messageTitle\":\"爱内秀提货通知\",\"messageContent\":\"你的订单已完成，感谢你的惠顾！\",\"receiver\":\"" + nickName + "\",\"targetActivity\":\"iliker.fragment.person.AllOrderActivity\"}");
            writer.print("success");
            writer.flush();
            writer.close();
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.print("error");
        writer.flush();
        writer.close();
        return NONE;
    }*/

    public String confirmUnPackBack() throws IOException {
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        JSONObject object = JSONObject.parseObject(confirmBack);
        String pushEmail = object.getString("storeEmail");
        String nickName = object.getString("nickName");
        String unPackOrderID = object.getString("unPackOrderID");
        try {
            for (int i = 0; i < 10; i++) {
                String responseId = PushTools.pushMessageToAndroid_toAll(pushEmail, "{\"messageTitle\":\"爱内秀订单通知\",\"messageContent\":\"系统已经接到你的订单，很快会为你处理！\",\"receiver\":\"" + pushEmail + "\",\"targetActivity\":\"cn.iliker.mall.storemodule.ViewUnPackOrderActivity\",\"unPackOrderID\":\"" + unPackOrderID + "\"}");
                if (responseId != null) {
                    break;
                }
            }
            CustomSignature(pushEmail, "<爱内秀>提货完成通知", "感谢您的服务！", null, "1", "2", "cn.iliker.mall.storemodule.UnPackOrderActivity");
            //PushTools.pushMessageToAndroid_toAll(nickName, "{\"messageTitle\":\"爱内秀提货通知\",\"messageContent\":\"你的订单已完成，感谢你的惠顾！\",\"receiver\":\"" + nickName + "\",\"targetActivity\":\"iliker.fragment.person.AllOrderActivity\"}");
            List<String> list = new ArrayList<>();
            list.add(object.getString("phone"));
            aliYunAccountSvc.pushMessage(list, nickName + ":您好！\n您的订单号" + object.getString("orderCode") + "已收货，感谢你的惠顾！");
            writer.print("success");
            writer.flush();
            writer.close();
            return NONE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer.print("error");
        writer.flush();
        writer.close();
        return NONE;
    }

    public String getNearStore() {
        msg.clear();
        JSONArray jsonArray = managerService.getNearStores(mylat, mylng, 20);
        if (jsonArray != null && !jsonArray.isEmpty()) {
            msg.put("stores", jsonArray);
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String storeRegister() {
        msg.clear();
        return ERROR;
    }

    /*获取商家库存*/
    public String getStoreStocks() {
        msg.clear();
        GeneralList<StoreStockInfo> list = storeStockSvc.getStoreStockByStoreId(storeId, indexPage, pageCount);
        if (list != null) {
            List<StoreStockInfo> storeStockInfos = list.getList();
            JSONArray stockArrayJSON = new JSONArray();
            for (StoreStockInfo storeStockInfo : storeStockInfos) {
                JSONObject stockIntoJSON = new JSONObject();
                JSONArray stockitemArrayJSON = new JSONArray();
                JSONObject goodsJSON = new JSONObject();
                String goodsCode = storeStockInfo.getGoods().getGoodCode();
                int goodsId = storeStockInfo.getGoods().getId();
                goodsJSON.put("goodCode", goodsCode);
                goodsJSON.put("id", goodsId);
                stockIntoJSON.put("goods", goodsJSON);
                stockIntoJSON.put("color", storeStockInfo.getColor());
                stockIntoJSON.put("storeStockId", storeStockInfo.getStoreStockId());
                Set<StoreStockItem> storeStockItems = storeStockInfo.getStockItems();
                for (StoreStockItem storeStockItem : storeStockItems) {
                    JSONObject storeStockJSON = new JSONObject();
                    storeStockJSON.put("size", storeStockItem.getSize());
                    storeStockJSON.put("stockCount", storeStockItem.getStockCount());
                    storeStockJSON.put("stockItemId", storeStockItem.getStockItemId());
                    stockitemArrayJSON.add(storeStockJSON);
                }
                stockIntoJSON.put("stockItems", stockitemArrayJSON);
                stockArrayJSON.add(stockIntoJSON);
            }
            msg.put("totalSize", list.getTotalSize());
            msg.put("totalPage", list.getTotalPage());
            msg.put("data", stockArrayJSON);
            return SUCCESS;
        }
        return NONE;
    }

    /*更新商家库存*/
    public String updateStoreStock() {
        msg.clear();
        boolean isextister = false;

        try {
            if (storeStockInfoStr == null || storeStockInfoStr.isEmpty()) {
                msg.put("msg", "empty string or null");
                msg.put("success", false);
                return ERROR;
            }
            StoreStockInfo storeStockInfo = JSON.parseObject(storeStockInfoStr, StoreStockInfo.class);
            Goods goods = goodsSvc.findById(storeStockInfo.getGoods().getId());
            if (brandName == null || !brandName.equals(goods.getBrand().getBrandName())) {
                msg.put("msg", "没有该品牌下的商品");
                msg.put("success", false);
                return ERROR;
            }
            StoreStockInfo oldStorStockInfo = storeStockSvc.getStoreStockByStoreId(storeStockInfo.getStoreInfo().getId(), storeStockInfo.getGoods().getId(), storeStockInfo.getColor());
            if (oldStorStockInfo == null) {
                oldStorStockInfo = storeStockInfo;
                oldStorStockInfo.setAddTime(new Timestamp(new Date().getTime()));
            } else {
                Set<StoreStockItem> storeStockItems = oldStorStockInfo.getStockItems();
                Object[] objects = storeStockInfo.getStockItems().toArray();
                StoreStockItem requstItem = (StoreStockItem) objects[0];
                for (StoreStockItem storeStockItem : storeStockItems) {
                    if (storeStockItem.getSize().equals(requstItem.getSize())) {
                        isextister = true;
                        if (stockStatus) {
                            storeStockItem.setStockCount(storeStockItem.getStockCount() + requstItem.getStockCount());
                        } else {
                            int surplus = storeStockItem.getStockCount() - requstItem.getStockCount();
                            storeStockItem.setStockCount(surplus < 0 ? 0 : surplus);
                        }
                        break;
                    }
                }
                if (!isextister) {
                    storeStockItems.add(requstItem);
                }
            }
            storeStockSvc.saveStoreStockInfo(oldStorStockInfo);
            msg.put("success", true);
            return SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        msg.put("msg", "添加库存失败");
        msg.put("success", false);
        return ERROR;
    }

    /*获取门店运营品牌*/
    public String getStoreRunBrands() {
        msg.clear();
        List<Object[]> list = storeStockSvc.getRunBrands(storeId);
        if (!list.isEmpty()) {
            JSONArray jsonArray = new JSONArray();
            for (Object[] objects : list) {
                JSONObject brandJSON = new JSONObject();
                brandJSON.put("brandId", objects[0]);
                brandJSON.put("brandName", objects[1]);
                jsonArray.add(brandJSON);
            }
            msg.put("brands", jsonArray);
            return SUCCESS;
        }
        return NONE;
    }

    /*获取门店收入*/
    public String getStoreBalance() {
        msg.clear();
        StoreInfo foundStoreInfo = managerService.findById(storeId);
        if (foundStoreInfo != null) {
            Double balance = foundStoreInfo.getStoreWallet().getRemainingSum();
            msg.put("balance", balance);
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    /*门店添加UnpackOrder*/
    public String addUnPackOrder() {
        if (unpackOrder != null) {
            foundOrder = orderSvc.findById(unpackOrder.getUnpackOrderId().getOrderInfo().getId());

            if (foundOrder == null || OrderState.RECEIVED.getName().equals(foundOrder.getOrderstate())) {
                msg.put("success", false);
                msg.put("msg", "order is received");
                return ERROR;
            }
            try {
                UnpackOrder foundUnpackOrder = managerService.find(unpackOrder.getUnpackOrderId());
                if (foundUnpackOrder == null) {
                    unpackOrder.setSys_flag(false);
                    unpackOrder.setUser_confirm(false);
                    unpackOrder.setCreateTime(new Timestamp(new Date().getTime()));
                    managerService.saveUnpackOrder(unpackOrder);
                } else {
                    foundUnpackOrder.setCreateTime(new Timestamp(new Date().getTime()));
                    foundUnpackOrder.setClearingType(unpackOrder.getClearingType());
                    managerService.saveUnpackOrder(foundUnpackOrder);
                }
                Userinfo userinfo = usersvc.findByPhone(foundOrder.getPhone());
                StoreInfo storeInfo = managerService.findById(unpackOrder.getUnpackOrderId().getStoreInfo().getId());
                unPackOrderInfo = "{\"unPackOrderID\":\"" + foundOrder.getId() + "," + storeInfo.getId() + "\",\"user_NickName\":\"" + userinfo.getNickname() + "\",\"storeName\":\"" + storeInfo.getStoreName() + "\",\"storePic\":\"" + storeInfo.getFaceIcon().split("#")[0] + "\",\"storeEmail\":\"" + storeInfo.getLoginEmail() + "\"}";
                msg.put("success", true);
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        msg.put("success", false);
        return ERROR;
    }

    /*用户门店自提确认收货*/
    public String userUnpackConfirmReceived() {
        if (unPackOrderInfo != null) {
            try {
                JSONObject jsonObject = JSON.parseObject(unPackOrderInfo);
                String[] unPackOrderIDs = jsonObject.getString("unPackOrderID").split(",");
                TOrder tOrder = orderSvc.findById(Integer.parseInt(unPackOrderIDs[0]));
                tOrder.setOrderstate(OrderState.RECEIVED.getName());
                UnpackOrder foundUnpackOrder = managerService.find(new UnpackOrderId(new TOrder(Integer.parseInt(unPackOrderIDs[0])), new StoreInfo(Integer.parseInt(unPackOrderIDs[1]))));
                foundUnpackOrder.setUser_confirm(true);
                managerService.confirmReceived(tOrder, foundUnpackOrder);
                confirmBack = "{\"storeEmail\":\"" + jsonObject.getString("storeEmail") + "\",\"unPackOrderID\":\"" + jsonObject.getString("unPackOrderID") + "\",\"nickName\":\"" + jsonObject.getString("nickName") + "\",\"phone\":\"" + tOrder.getPhone() + "\",\"orderCode\":\"" + tOrder.getOrderid() + "\"}";
                return SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ERROR;
    }

    /*获取门店UnpackOrder*/
    public String getStoreUnpackOrder() {
        List<UnpackOrder> unpackOrders = managerService.pageGetUnpackOrder(storeId, indexPage, pageCount);
        if (!unpackOrders.isEmpty()) {
            JSONArray orders = new JSONArray();
            for (UnpackOrder unpackOrder : unpackOrders) {
                JSONObject item = new JSONObject();
                JSONArray orderItem = new JSONArray();
                TOrder tOrder = orderSvc.findById(unpackOrder.getUnpackOrderId().getOrderInfo().getId());
                item.put("unpackOrderId", unpackOrder.getUnpackOrderId().getOrderInfo().getId() + "," + unpackOrder.getUnpackOrderId().getStoreInfo().getId());
                item.put("clearingType", unpackOrder.getClearingType().getName());
                item.put("createTime", DateFormat.getDateTimeInstance().format(unpackOrder.getCreateTime()));
                item.put("user_confirm", unpackOrder.getUser_confirm());
                item.put("sys_flag", unpackOrder.getSys_flag());
                Set<Orderdetail> orderDetails = tOrder.getOrderdetails();
                for (Orderdetail orderdetail : orderDetails) {
                    JSONObject orderDetailJOSN = new JSONObject();
                    orderDetailJOSN.put("color", orderdetail.getColor());
                    orderDetailJOSN.put("size", orderdetail.getSize());
                    orderDetailJOSN.put("productPrice", orderdetail.getProductPrice());
                    JSONObject goodsJSON = new JSONObject();
                    goodsJSON.put("id", orderdetail.getGoods().getId());
                    goodsJSON.put("goodCode", orderdetail.getGoods().getGoodCode());
                    goodsJSON.put("goodName", orderdetail.getGoods().getGoodName());
                    goodsJSON.put("imgpath", orderdetail.getGoods().getImgpath().split("#")[0]);
                    orderDetailJOSN.put("orderamount", orderdetail.getOrderamount());
                    orderDetailJOSN.put("goods", goodsJSON);
                    orderItem.add(orderDetailJOSN);
                }
                item.put("orderItems", orderItem);
                orders.add(item);
            }
            msg.put("orders", orders);
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String GetUnPackOrderById() {
        if (unPackOrderId != null && !unPackOrderId.isEmpty()) {
            String[] strings = unPackOrderId.split(",");
            UnpackOrder foundUnpackOrder = managerService.find(new UnpackOrderId(new TOrder(Integer.parseInt(strings[0])), new StoreInfo(Integer.parseInt(strings[1]))));
            JSONObject item = new JSONObject();
            JSONArray orderItem = new JSONArray();
            TOrder tOrder = orderSvc.findById(foundUnpackOrder.getUnpackOrderId().getOrderInfo().getId());
            item.put("unpackOrderId", foundUnpackOrder.getUnpackOrderId().getOrderInfo().getId() + "," + foundUnpackOrder.getUnpackOrderId().getStoreInfo().getId());
            item.put("clearingType", foundUnpackOrder.getClearingType().getName());
            item.put("createTime", DateFormat.getDateTimeInstance().format(foundUnpackOrder.getCreateTime()));
            item.put("user_confirm", foundUnpackOrder.getUser_confirm());
            item.put("sys_flag", foundUnpackOrder.getSys_flag());
            Set<Orderdetail> orderDetails = tOrder.getOrderdetails();
            for (Orderdetail orderdetail : orderDetails) {
                JSONObject orderDetailJOSN = new JSONObject();
                orderDetailJOSN.put("color", orderdetail.getColor());
                orderDetailJOSN.put("size", orderdetail.getSize());
                orderDetailJOSN.put("productPrice", orderdetail.getProductPrice());
                JSONObject goodsJSON = new JSONObject();
                goodsJSON.put("id", orderdetail.getGoods().getId());
                goodsJSON.put("goodCode", orderdetail.getGoods().getGoodCode());
                goodsJSON.put("goodName", orderdetail.getGoods().getGoodName());
                goodsJSON.put("imgpath", orderdetail.getGoods().getImgpath().split("#")[0]);
                orderDetailJOSN.put("orderamount", orderdetail.getOrderamount());
                orderDetailJOSN.put("goods", goodsJSON);
                orderItem.add(orderDetailJOSN);
            }
            item.put("orderItems", orderItem);
            msg.put("success", true);
            msg.put("unPackOrder", item);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String parsOrderByIdUnPackView() {
        foundOrder = orderSvc.findById(id);
        if (foundOrder != null && foundOrder.getStoreInfo() != null) {
            JSONObject orderJSON = new JSONObject();
            Userinfo userinfo = usersvc.findByPhone(foundOrder.getPhone());
            orderJSON.put("id", foundOrder.getId());
            orderJSON.put("orderid", foundOrder.getOrderid());
            orderJSON.put("phone", foundOrder.getPhone());
            orderJSON.put("user_nickName", userinfo.getNickname());
            orderJSON.put("orderdate", DateFormat.getDateTimeInstance().format(foundOrder.getOrderdate()));
            orderJSON.put("postmethod", foundOrder.getPostmethod());
            orderJSON.put("paymethod", foundOrder.getPaymethod());
            orderJSON.put("orderstate", foundOrder.getOrderstate());
            orderJSON.put("point", foundOrder.getStoreInfo().getStoreName());
            JSONArray orderItems = new JSONArray();
            for (Orderdetail orderdetail : foundOrder.getOrderdetails()) {
                JSONObject orderdetailJSON = new JSONObject();
                orderdetailJSON.put("productPrice", orderdetail.getProductPrice());
                orderdetailJSON.put("orderamount", orderdetail.getOrderamount());
                orderdetailJSON.put("color", orderdetail.getColor());
                orderdetailJSON.put("size", orderdetail.getSize());
                JSONObject goodsJSON = new JSONObject();
                goodsJSON.put("goodCode", orderdetail.getGoods().getGoodCode());
                goodsJSON.put("goodName", orderdetail.getGoods().getGoodName());
                goodsJSON.put("imgpath", orderdetail.getGoods().getImgpath().split("#")[0]);
                orderdetailJSON.put("goods", goodsJSON);
                orderItems.add(orderdetailJSON);
            }
            orderJSON.put("orderItems", orderItems);
            msg.put("success", true);
            msg.put("orderInfo", orderJSON);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }
}
