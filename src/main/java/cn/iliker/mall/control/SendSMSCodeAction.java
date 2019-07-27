package cn.iliker.mall.control;

import static cn.iliker.mall.utils.NetSteamUtil.isMobileNO;
import static cn.iliker.mall.utils.NetSteamUtil.validateReg;

import java.util.Date;
import java.util.Random;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.taobao.api.ApiException;

import cn.iliker.mall.entity.RegisterBean;
import cn.iliker.mall.entity.Userinfo;
import cn.iliker.mall.service.IAliYunAccountSvc;
import cn.iliker.mall.service.IUserinfoSvc;
import cn.iliker.mall.utils.MD5Util;

public class SendSMSCodeAction extends ActionSupport {
    private IUserinfoSvc userinfoSvc;

    private String phoneNum;//手机号码phoneNum

    private JSONObject msg = new JSONObject();
    private String registerBean;

    private IAliYunAccountSvc aliYunAccountSvc;

    public void setAliYunAccountSvc(IAliYunAccountSvc aliYunAccountSvc) {
        this.aliYunAccountSvc = aliYunAccountSvc;
    }

    public void setRegisterBean(String registerBean) {
        this.registerBean = registerBean;
    }

    private static final char[] s = {'1', '2', '3', '7', '6', '4', '5', '9', '8'};

    public void setUserinfoSvc(IUserinfoSvc userinfoSvc) {
        this.userinfoSvc = userinfoSvc;
    }


    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public JSONObject getMsg() {
        return msg;
    }

    public String regPhone() {
        msg.clear();
        if (!isMobileNO(phoneNum)) {
            msg.put("success", false);
            msg.put("msg", "手机号码非法");
            return ERROR;
        }
        Userinfo userinfo = userinfoSvc.findByPhone(phoneNum);
        //未激活用户激活操作发送验证码
        if (userinfo != null && userinfo.getEnable()) {
            msg.put("success", false);
            msg.put("msg", "手机号已存在");
            return ERROR;
        }
        String sendCode = getRandCode();
        try {
            JSONObject jsonObject = aliYunAccountSvc.sendSMSCode(phoneNum, sendCode);
            if (jsonObject.containsKey("alibaba_aliqin_fc_sms_num_send_response")) {
                msg.put("backPhone", phoneNum);
                msg.put("code", sendCode);
                msg.put("time", System.currentTimeMillis());
                msg.put("success", true);
                msg.put("msg", "短信已经发送");
                return SUCCESS;
            } else if (jsonObject.containsKey("error_response")) {
                msg.put("success", false);
                msg.put("msg", jsonObject.getJSONObject("error_response").getString("sub_msg"));
                return ERROR;
            }
        } catch (ApiException e) {
            msg.put("success", false);
            msg.put("msg", "短信发送失败");
            return ERROR;
        }
        msg.put("success", false);
        msg.put("msg", "未知错误");
        return ERROR;
    }

    /*找回密码发送验证码*/
    public String findPwdForSecond() {
        msg.clear();
        String sendCode = getRandCode();
        try {
            JSONObject jsonObject = aliYunAccountSvc.sendSMSCode(phoneNum, sendCode);
            if (jsonObject.containsKey("alibaba_aliqin_fc_sms_num_send_response")) {
                msg.put("code", sendCode);
                msg.put("time", System.currentTimeMillis());
                msg.put("success", true);
                msg.put("msg", "短信已经发送");
                return SUCCESS;
            } else if (jsonObject.containsKey("error_response")) {
                msg.put("success", false);
                msg.put("msg", jsonObject.getJSONObject("error_response").getString("sub_msg"));
                return ERROR;
            }

        } catch (ApiException e) {
            e.printStackTrace();
        }
        msg.put("success", false);
        msg.put("msg", "系统错误");
        return ERROR;
    }

    /**
     * 生成随机七位验证码
     */
    private static String getRandCode() {
        Random random = new Random();
        int len = s.length;
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int location = random.nextInt(len);
            buffer.append(s[location]);
        }
        return buffer.toString();
    }

    /*web端注册校验*/
    public String webValidate() {
        msg.clear();
        RegisterBean bean = JSON.parseObject(registerBean, RegisterBean.class);
        if (validateReg(bean, msg)) {
            String reductionPwd = new String(MD5Util.decode(bean.getPassword()));
            if ("undefined".equals(reductionPwd.toLowerCase())) {
                msg.put("success", false);
                msg.put("msg", "密码不能为undefined");
                return ERROR;
            }
            Userinfo userinfo = new Userinfo();
            userinfo.setNickname(bean.getNickname());
            userinfo.setRegistered(new Date());
            userinfo.setPassword(MD5Util.getMD5Str(reductionPwd));
            userinfo.setPhone(bean.getPhoneNum());
            if (bean.getSuperiornum() != null) {
                String superiornum = new String(MD5Util.decode(bean.getSuperiornum()));
                Userinfo userinfo1 = userinfoSvc.findByPhone(superiornum);
                if (userinfo1 != null) {
                    userinfo.setParent(userinfo1);
                }
            }
            ServletActionContext.getRequest().getSession().setAttribute("registerUser", userinfo);
            return SUCCESS;
        }
        return ERROR;
    }

    public String regActivate() {
        msg.clear();
        RegisterBean bean = JSON.parseObject(registerBean, RegisterBean.class);
        if (validateReg(bean, msg)) {
            Userinfo userinfo = userinfoSvc.findByPhone(bean.getBackPhone());
            if (userinfo != null) {
                msg.put("nickName", userinfo.getNickname());
                msg.put("uid", userinfo.getUid());
                msg.put("success", true);
                return SUCCESS;
            } else {
                msg.put("success", false);
                msg.put("msg", "用户不存在");
            }
        }

        return ERROR;
    }

}
