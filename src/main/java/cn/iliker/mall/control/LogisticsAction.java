package cn.iliker.mall.control;

import static cn.iliker.mall.entity.stateattr.OrderState.WAITDELIVER;
import static cn.iliker.mall.utils.DateParseUtils.getFormatDate;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.entity.KdniaoPushResult;
import cn.iliker.mall.entity.Logistics;
import cn.iliker.mall.entity.TOrder;
import cn.iliker.mall.entity.stateattr.OrderState;
import cn.iliker.mall.interceptor.Permission;
import cn.iliker.mall.service.IKdniaopushresultSvc;
import cn.iliker.mall.service.ITOrderSvc;
import cn.iliker.mall.service.IlogisticsSvc;
import cn.iliker.mall.utils.KdniaoSubscribeAPI;

public class LogisticsAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    private int id;
    private String orderId;
    private String logisticscode;
    private IlogisticsSvc logisticsSvc;
    private ITOrderSvc tordersvc;
    private String companyCode;
    private IKdniaopushresultSvc kdniaopushresultSvc;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;
    private JSONObject msg = new JSONObject();

    public void setKdniaopushresultSvc(IKdniaopushresultSvc kdniaopushresultSvc) {
        this.kdniaopushresultSvc = kdniaopushresultSvc;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getLogisticscode() {
        return logisticscode;
    }

    public void setLogisticscode(String logisticscode) {
        this.logisticscode = logisticscode;
    }

    public void setLogisticsSvc(IlogisticsSvc logisticsSvc) {
        this.logisticsSvc = logisticsSvc;
    }

    public void setTordersvc(ITOrderSvc tordersvc) {
        this.tordersvc = tordersvc;
    }

    public JSONObject getMsg() {
        return msg;
    }

    @Permission(module = "order", privilege = "turnDelivered")
    public String addLogisticsinfo() {
        msg.clear();
        TOrder tOrder = tordersvc.findById(id);
        if (tOrder != null && WAITDELIVER.getName().equals(tOrder.getOrderstate())) {
            try {
                tOrder.setOrderstate(OrderState.DELIVERED.getName());
                logisticsSvc.add(new Logistics(companyCode, tOrder, logisticscode), tOrder);
                String result = KdniaoSubscribeAPI.orderTracesSubByJson("{\"ShipperCode\":\"" + companyCode + "\",\"LogisticCode\":\"" + logisticscode + "\",\"OrderCode\":\"" + orderId + "\"}");
                JSONObject object = JSONObject.parseObject(result);
                boolean success = object.getBoolean("Success");
                if (success) {
                    msg.put("success", true);
                    msg.put("msg", "物流跟踪已订阅");
                    return SUCCESS;
                }
            } catch (RuntimeException e) {
                msg.put("success", false);
                msg.put("msg", "物流录入失败");
                return ERROR;
            } catch (Exception ex) {
                msg.put("success", true);
                msg.put("msg", "物流跟踪订阅失败");
                return ERROR;
            }
        }
        return NONE;
    }

    public String queryLogistics() throws IOException {
        ServletActionContext.getResponse().setContentType(
                "text/html;charset=utf-8");
        List<Logistics> list = logisticsSvc.findByProperty("TOrder.id", id);
        if (list.isEmpty()) {
            PrintWriter out = ServletActionContext.getResponse().getWriter();
            out.print("你的订单还没发货，请耐心等待");
            out.flush();
            out.close();
            return NONE;
        }
        Logistics logistics = list.get(0);
        logisticscode = logistics.getLogisticscode();
        companyCode = logistics.getCompanyCode();
        id = 0;
        return SUCCESS;
    }

    public String executePush() throws IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        String requestData = httpServletRequest.getParameter("RequestData");
        KdniaoPushResult kdniaoPushResult = JSON.parseObject(requestData, KdniaoPushResult.class);
        if (kdniaoPushResult != null) {
            kdniaopushresultSvc.save(kdniaoPushResult);
            writer.println("{\"EBusinessID\":\"1268135\",\"UpdateTime\":\"" + getFormatDate("yyyy-MM-dd_HH:mm:ss") + "\",\"Success\":\"true\",\"Reason\":\"\"}");
            writer.flush();
            writer.close();
            return NONE;
        }
        writer.println("{\"EBusinessID\":\"1268135\",\"UpdateTime\":\"" + getFormatDate("yyyy-MM-dd_HH:mm:ss") + "\",\"Success\":\"false\",\"Reason\":\"\"}");
        writer.flush();
        writer.close();
        return NONE;
    }

    @Override
    public void setServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public void setServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
}
