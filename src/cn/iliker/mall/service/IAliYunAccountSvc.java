package cn.iliker.mall.service;

import com.alibaba.fastjson.JSONObject;
import com.taobao.api.ApiException;

import java.util.List;

public interface IAliYunAccountSvc {
    boolean addAccount(String userID, String password, String nickName) throws ApiException;

    boolean updateAccount(String userID, String nickName, String password, String headimg) throws ApiException;

    boolean getAccount(String userID) throws ApiException;

    boolean delAccount(String userID) throws ApiException;

    boolean pushMessage(List<String> openid, String message) throws ApiException;

    JSONObject sendSMSCode(String phone, String sendCode) throws ApiException;

    void sendSMSNotify(String phones) throws ApiException;
}
