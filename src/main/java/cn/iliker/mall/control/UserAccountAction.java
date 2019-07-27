package cn.iliker.mall.control;

import static cn.iliker.mall.alipay.util.UtilDate.getOrderNum;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;

import cn.iliker.mall.entity.GiveAccount;
import cn.iliker.mall.entity.IntegralAccount;
import cn.iliker.mall.entity.Rechargeableorder;
import cn.iliker.mall.entity.Userinfo;
import cn.iliker.mall.entity.Wallet;
import cn.iliker.mall.service.ICommonQuerySvc;
import cn.iliker.mall.service.IUserAccountSvc;
import cn.iliker.mall.service.IUserinfoSvc;
import cn.iliker.mall.utils.MD5Util;

//用户账户相关
public class UserAccountAction extends ActionSupport {
    private IUserAccountSvc userAccountSvc;
    private String phone;
    private JSONObject msg = new JSONObject();
    private ICommonQuerySvc querySvc;
    private int offset = 0;
    private int pageSize = 0;
    private Rechargeableorder rechargeableorder;
    private String payProperty;
    private IUserinfoSvc userinfoSvc;

    public void setPayProperty(String payProperty) {
        this.payProperty = payProperty;
    }

    public void setUserinfoSvc(IUserinfoSvc userinfoSvc) {
        this.userinfoSvc = userinfoSvc;
    }

    public Rechargeableorder getRechargeableorder() {
        return rechargeableorder;
    }

    public void setRechargeableorder(Rechargeableorder rechargeableorder) {
        this.rechargeableorder = rechargeableorder;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public void setQuerySvc(ICommonQuerySvc querySvc) {
        this.querySvc = querySvc;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUserAccountSvc(IUserAccountSvc userAccountSvc) {
        this.userAccountSvc = userAccountSvc;
    }

    public String getIntegral() {
        msg.clear();
        IntegralAccount integralAccount = userAccountSvc.getIntegral(phone);
        if (integralAccount != null) {
            msg.put("integral", integralAccount.getAmount());
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String getWallet() {
        msg.clear();
        Wallet wallet = userAccountSvc.getWallet(phone);
        if (wallet != null) {
            msg.put("balance", wallet.getRemainingSum());
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    /*查找账户优惠券*/
    public String findCouponsByPhone() {
        msg.clear();
        msg = querySvc.getAll("id,denomination,voucher_value,cardUrl", "prepaidcard", "voucher_value>0 AND effective=1 AND phone='" + phone + "'", null, "id", offset, 10);
        if (msg != null) {
            return SUCCESS;
        }
        return NONE;
    }

    public String getGiveAccount() {
        msg.clear();
        GiveAccount giveAccount = userAccountSvc.getGiveAccount(phone);
        if (giveAccount != null) {
            msg.put("gardVoucher", giveAccount.getBalance());
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String getSpendings() {
        msg.clear();
        if (offset == 0) {
            offset = 1;
        }
        if (pageSize == 0 || pageSize > 50) {
            pageSize = 20;
        }
        JSONObject jsonObject = querySvc.getAll("spendID,spendTime,spendDESC,spendAmount", "spending", "phone=\'" + phone + "\'", null, "spendTime DESC", offset, pageSize);
        if (jsonObject != null) {
            msg.put("data", jsonObject);
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String getIncomes() {
        msg.clear();
        if (offset == 0) {
            offset = 1;
        }
        if (pageSize == 0 || pageSize > 50) {
            pageSize = 20;
        }
        JSONObject jsonObject = querySvc.getAll("incomeID,incomeTime,incomeDESC,incomeAmount", "income", "phone=\'" + phone + "\'", null, "incomeTime DESC", offset, pageSize);
        if (jsonObject != null) {
            msg.put("data", jsonObject);
            msg.put("success", true);
            return SUCCESS;
        }
        msg.put("success", false);
        return ERROR;
    }

    public String addRechargeOrder() {
        msg.clear();
        try {
            rechargeableorder.setRechargeableid("d" + getOrderNum());
            userAccountSvc.saveRechargeableorder(rechargeableorder);
            msg.put("total_amount", rechargeableorder.getAmount());
            msg.put("out_trade_no", rechargeableorder.getRechargeableid());
            msg.put("success", true);
            return SUCCESS;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        msg.put("success", false);
        return ERROR;
    }

    public String balancePay() {
        msg.clear();
        JSONObject jsonObject = JSON.parseObject(payProperty);
        if (jsonObject != null) {
            Userinfo userinfo = userinfoSvc.findById(jsonObject.getInteger("uid"));
            if (userinfo.getPaymentCode() == null) {
                msg.put("result_code", "FAIL");
                msg.put("requestCode", jsonObject.getInteger("requestCode"));
                return ERROR;
            }
            if (MD5Util.getMD5Str(new String(MD5Util.decode(jsonObject.getString("payment_code")))).equals(userinfo.getPaymentCode())) {
                try {
                    userAccountSvc.executeInAppPay(userinfo.getPhone(), jsonObject.getString("out_trade_no"), jsonObject.getDouble("payAmount"));
                    msg.put("result_code", "SUCCESS");
                    msg.put("requestCode", jsonObject.getInteger("requestCode"));
                    msg.put("success", true);
                    return SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        msg.put("result_code", "FAIL");
        msg.put("requestCode", jsonObject.getInteger("requestCode"));
        return ERROR;
    }
}
