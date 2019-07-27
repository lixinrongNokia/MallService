package cn.iliker.mall.control;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.entity.Adminmanager;
import cn.iliker.mall.entity.stateattr.Gender;
import cn.iliker.mall.error.ExistsError;
import cn.iliker.mall.error.MyError;
import cn.iliker.mall.privilege.PrivilegeGroup;
import cn.iliker.mall.privilege.SystemPrivilege;
import cn.iliker.mall.service.IPrivilegeManagerSvc;
import cn.iliker.mall.service.IadminManagerSvc;
import cn.iliker.mall.utils.MD5Util;

public class SystemInitAction extends ActionSupport {
    private IPrivilegeManagerSvc privilegeManagerSvc;
    private JSONObject msg = new JSONObject();
    private IadminManagerSvc managerSvc;

    public JSONObject getMsg() {
        return msg;
    }

    public void setManagerSvc(IadminManagerSvc managerSvc) {
        this.managerSvc = managerSvc;
    }

    public void setPrivilegeManagerSvc(IPrivilegeManagerSvc privilegeManagerSvc) {
        this.privilegeManagerSvc = privilegeManagerSvc;
    }

    /**
     * 初始化管理员账号
     */
    public String initAdmin() {
        Adminmanager adminmanager = new Adminmanager();
        adminmanager.setNickname("高石花道");
        adminmanager.setPassword(MD5Util.getMD5Str("lxr123583"));
        adminmanager.setGender(Gender.MAN);
        adminmanager.setEmail("wdhtct8788@live.com");
        adminmanager.setStatus(true);
        adminmanager.setPhone("18680602795");
        try {
            managerSvc.save(adminmanager);
            return SUCCESS;
        } catch (ExistsError existsError) {
            existsError.printStackTrace();
        } catch (MyError myError) {
            myError.printStackTrace();
        }
        return ERROR;
    }

    public String initPrivilege2Manager() {
        Adminmanager adminmanager = managerSvc.findByNickname("高石花道");
        adminmanager.getPrivilegeGroups().addAll(privilegeManagerSvc.findAllPrivilegeGroup());
        managerSvc.update(adminmanager);
        return NONE;
    }

    public String initPrivilegeGroup() {
        PrivilegeGroup group = new PrivilegeGroup();
        group.getSystemPrivileges().addAll(privilegeManagerSvc.findAllSystemPrivilege());
        group.setName("技术组");
        privilegeManagerSvc.savePrivilegeGroup(group);
        return NONE;
    }

    /**
     * 初始化权限
     */
    public String initSystemPrivilege() {
        if (privilegeManagerSvc.findAllSystemPrivilege().isEmpty()) {
            List<SystemPrivilege> privileges = new ArrayList<>();
            //            privileges.add(new SystemPrivilege("order","turnReceived","转已收货订单"));
            privileges.add(new SystemPrivilege("account", "transfer", "app资金转账"));
            privileges.add(new SystemPrivilege("order", "quickRefund", "原路退款"));
            privileges.add(new SystemPrivilege("order", "refundAppAccount", "退款至app账户"));
            privileges.add(new SystemPrivilege("order", "turnDelivered", "转已发货订单"));
            privileges.add(new SystemPrivilege("order", "turnWaitdeliver", "转等待发货订单"));
            privileges.add(new SystemPrivilege("order", "cancelOrder", "取消订单"));
            privileges.add(new SystemPrivilege("order", "confirmOrder", "审核通过订单"));
            privileges.add(new SystemPrivilege("order", "confirmPayment", "财务确认订单已付款"));
            privileges.add(new SystemPrivilege("order", "allUnLock", "批量解锁订单"));
            privileges.add(new SystemPrivilege("order", "view", "订单查看"));
            privileges.add(new SystemPrivilege("order", "modifyContactInfo", "订单联系信息修改"));
            privileges.add(new SystemPrivilege("order", "modifyDeliverInfo", "订单配送信息修改"));
            privileges.add(new SystemPrivilege("order", "modifyPaymentWay", "支付方式修改"));
            privileges.add(new SystemPrivilege("order", "modifyDeliverWay", "配送方式修改"));
            privileges.add(new SystemPrivilege("order", "modifyProductAmount", "商品购买数量修改"));
            privileges.add(new SystemPrivilege("order", "modifyDeliverFee", "配送费修改"));
            privileges.add(new SystemPrivilege("order", "deleteOrderItem", "删除订单项"));

            privileges.add(new SystemPrivilege("employee", "leave", "管理员激活设置"));
            privileges.add(new SystemPrivilege("employee", "insert", "管理员添加"));
            privileges.add(new SystemPrivilege("employee", "update", "管理员信息修改"));
            privileges.add(new SystemPrivilege("employee", "view", "管理员查看"));
            privileges.add(new SystemPrivilege("employee", "privilegeGroupSet", "管理员权限设置"));

            privileges.add(new SystemPrivilege("brand", "insert", "品牌添加"));
            privileges.add(new SystemPrivilege("brand", "update", "品牌信息修改"));
            privileges.add(new SystemPrivilege("brand", "view", "品牌查看"));

            privileges.add(new SystemPrivilege("product", "insert", "产品添加"));
            privileges.add(new SystemPrivilege("product", "update", "产品信息修改"));
            privileges.add(new SystemPrivilege("product", "view", "产品查看"));
            privileges.add(new SystemPrivilege("product", "visible", "产品上/下架"));
            privileges.add(new SystemPrivilege("product", "commend", "产品推荐"));

            privileges.add(new SystemPrivilege("productType", "insert", "产品类别添加"));
            privileges.add(new SystemPrivilege("productType", "update", "产品类别修改"));
            privileges.add(new SystemPrivilege("productType", "view", "产品类别查看"));

            privileges.add(new SystemPrivilege("buyer", "enable", "网站用户启用"));
            privileges.add(new SystemPrivilege("buyer", "delete", "网站用户禁用"));
            privileges.add(new SystemPrivilege("buyer", "view", "网站用户查看"));
            privileges.add(new SystemPrivilege("community", "view", "社区帖子查看"));
            privilegeManagerSvc.batchSave(privileges);
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }
}
