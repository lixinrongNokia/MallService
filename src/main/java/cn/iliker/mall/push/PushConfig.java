package cn.iliker.mall.push;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * 推送的OpenAPI文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/openapi.html
 */
public class PushConfig {
    protected static final long APPKEY = 23542620;
    final static DefaultAcsClient client;
    protected static final String ACCESSKEYSECRET = "gqpGetMvHEN0Pihb4lLh8pKNvVquu1";
    protected static final String ACCESSKEYID = "LTAI1jSDTav3Y4qh";
    protected static final String REGIONID = "cn-hangzhou";
    protected static final String HOST = "http://cloudpush.aliyuncs.com";

    static {
        IClientProfile profile = DefaultProfile.getProfile(REGIONID, ACCESSKEYID, ACCESSKEYSECRET);
        client = new DefaultAcsClient(profile);
    }

}
