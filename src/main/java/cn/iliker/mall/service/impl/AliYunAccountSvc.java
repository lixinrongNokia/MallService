package cn.iliker.mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.Userinfos;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.request.OpenimImmsgPushRequest;
import com.taobao.api.request.OpenimUsersAddRequest;
import com.taobao.api.request.OpenimUsersDeleteRequest;
import com.taobao.api.request.OpenimUsersGetRequest;
import com.taobao.api.request.OpenimUsersUpdateRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.taobao.api.response.OpenimImmsgPushResponse;
import com.taobao.api.response.OpenimUsersAddResponse;
import com.taobao.api.response.OpenimUsersDeleteResponse;
import com.taobao.api.response.OpenimUsersGetResponse;
import com.taobao.api.response.OpenimUsersUpdateResponse;

import cn.iliker.mall.service.IAliYunAccountSvc;

public class AliYunAccountSvc implements IAliYunAccountSvc {
    private TaobaoClient openIMClient;
    private TaobaoClient smsClient;
    private String signName;
    private String singleTemplate;
    private String multiTemplate;

    public void setSingleTemplate(String singleTemplate) {
        this.singleTemplate = singleTemplate;
    }

    public void setMultiTemplate(String multiTemplate) {
        this.multiTemplate = multiTemplate;
    }

    public void setOpenIMClient(TaobaoClient openIMClient) {
        this.openIMClient = openIMClient;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public void setSmsClient(TaobaoClient smsClient) {
        this.smsClient = smsClient;
    }

    @Override
    public boolean addAccount(String userID, String password, String nickName) throws ApiException {
        OpenimUsersAddRequest req = new OpenimUsersAddRequest();
        List<Userinfos> list2 = new ArrayList<>();
        Userinfos obj3 = new Userinfos();
        obj3.setUserid(userID);
        obj3.setPassword(password);
        obj3.setNick(nickName);
        list2.add(obj3);
        req.setUserinfos(list2);
        OpenimUsersAddResponse rsp = openIMClient.execute(req);
        return handleRespose(1, rsp.getBody());
    }

    @Override
    public boolean updateAccount(String userID, String nickName, String password, String headimg) throws ApiException {
        OpenimUsersUpdateRequest req = new OpenimUsersUpdateRequest();
        List<Userinfos> list2 = new ArrayList<>();
        Userinfos obj3 = new Userinfos();
        obj3.setUserid(userID);
        if (nickName != null)
            obj3.setNick(nickName);
        if (password != null)
            obj3.setPassword(password);
        if (headimg != null)
            obj3.setIconUrl(headimg);
        list2.add(obj3);
        req.setUserinfos(list2);
        OpenimUsersUpdateResponse rsp = openIMClient.execute(req);
        return handleRespose(2, rsp.getBody());
    }

    @Override
    public boolean getAccount(String userID) throws ApiException {
        OpenimUsersGetRequest req = new OpenimUsersGetRequest();
        req.setUserids(userID);
        OpenimUsersGetResponse rsp = openIMClient.execute(req);
        return handleRespose(3, rsp.getBody());
    }

    /*{"openim_users_delete_response":{"result":{"string":["ok"]}}}*/
    @Override
    public boolean delAccount(String userID) throws ApiException {
        OpenimUsersDeleteRequest req = new OpenimUsersDeleteRequest();
        req.setUserids(userID);
        OpenimUsersDeleteResponse rsp = openIMClient.execute(req);
        return handleRespose(0, rsp.getBody());
    }

    @Override
    public boolean pushMessage(List<String> openid, String message) throws ApiException {
        OpenimImmsgPushRequest req = new OpenimImmsgPushRequest();
        OpenimImmsgPushRequest.ImMsg obj1 = new OpenimImmsgPushRequest.ImMsg();
        obj1.setFromUser("alq85115612@163.com");
        obj1.setToUsers(openid);
        obj1.setMsgType(0L);
        obj1.setContext(message);
        req.setImmsg(obj1);
        OpenimImmsgPushResponse rsp = openIMClient.execute(req);
        return handleRespose(4, rsp.getBody());
    }

    private boolean handleRespose(int actionID, String entityBody) {
        JSONObject jsonObject = JSONObject.parseObject(entityBody);
        switch (actionID) {
            case 1:
                JSONObject addMsg = jsonObject.getJSONObject("openim_users_add_response");
                return addMsg.getJSONObject("uid_succ").containsKey("string");
            case 0:
                JSONObject delMsg = jsonObject.getJSONObject("openim_users_delete_response");
                return delMsg.containsKey("result") && delMsg.getJSONObject("result").getJSONArray("string").get(0).equals("ok");
            case 2:
                JSONObject updateMsg = jsonObject.getJSONObject("openim_users_update_response");
                return updateMsg.getJSONObject("uid_succ").containsKey("string");
            case 3:
                JSONObject getMsg = jsonObject.getJSONObject("openim_users_get_response");
                return getMsg.getJSONObject("userinfos").containsKey("userinfos");
            case 4:
                return jsonObject.containsKey("openim_immsg_push_response");
        }
        return false;
    }

    /*发送短信验证码*/
    @Override
    public JSONObject sendSMSCode(String phone, String sendCode) throws ApiException {
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName(signName);
        req.setSmsParamString("{\"appName\":\"\\[爱内秀\\]\",\"smsCode\":\"" + sendCode + "\"}");
        req.setRecNum(phone);
        req.setSmsTemplateCode(singleTemplate);
        AlibabaAliqinFcSmsNumSendResponse rsp = smsClient.execute(req);
        return JSONObject.parseObject(rsp.getBody());
        /*{"alibaba_aliqin_fc_sms_num_send_response":{"result":{"msg":"OK","success":true,"err_code":"0","model":"263209312527404714^0"},"request_id":"3ez2idl42ycn"}}*/
        /*if (rootJson.containsKey("alibaba_aliqin_fc_sms_num_send_response")) {
            JSONObject jsonObject = rootJson.getJSONObject("alibaba_aliqin_fc_sms_num_send_response");
            JSONObject object = jsonObject.getJSONObject("result");
            return object.getBoolean("success");
        }*/
//        return false;
    }

    /*发送带网页链接短信通知*/
    @Override
    public void sendSMSNotify(String phones) throws ApiException {
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setSmsType("normal");
        req.setSmsFreeSignName(signName);
        req.setRecNum(phones);
        req.setSmsTemplateCode(multiTemplate);
        smsClient.execute(req);
    }
}
