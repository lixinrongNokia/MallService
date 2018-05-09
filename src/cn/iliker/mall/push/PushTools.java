package cn.iliker.mall.push;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidRequest;
import com.aliyuncs.push.model.v20150827.PushMessageToAndroidResponse;
import com.aliyuncs.push.model.v20150827.PushNoticeToAndroidRequest;
import com.aliyuncs.push.model.v20150827.PushNoticeToAndroidResponse;

/**
 * 推送的OpenAPI文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/openapi.html
 */
public final class PushTools extends PushConfig {
    /**
     * 推送通知给全部用户android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/notice-to-android.html
     */
    public static PushNoticeToAndroidResponse pushNoticeToAndroid_toAll(String title, String content) throws Exception {
        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        androidRequest.setAppKey(APPKEY);
        androidRequest.setTarget("all");
        androidRequest.setTargetValue("all");
        androidRequest.setTitle(title);
        androidRequest.setSummary(content);
        androidRequest.setAndroidExtParameters("{\"key1\":\"value1\",\"api_name\":\"PushNoticeToAndroidRequest\"}");
        return client.getAcsResponse(androidRequest);
    }

    /**
     * 推送通知给设备--android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/notice-to-android.html
     */
    public static PushNoticeToAndroidResponse pushNoticeToAndroid_toDevice(String deviceIds, String title, String content) throws Exception {
        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        androidRequest.setAppKey(APPKEY);
        androidRequest.setTarget("device");
        androidRequest.setTargetValue(deviceIds);
        androidRequest.setTitle(title);
        androidRequest.setSummary(content);
        return client.getAcsResponse(androidRequest);
    }

    /**
     * 推送通知给账号--android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/notice-to-android.html
     */
    public static PushNoticeToAndroidResponse pushNoticeToAndroid_toAccount(String account, String title, String content) throws Exception {
        PushNoticeToAndroidRequest androidRequest = new PushNoticeToAndroidRequest();
        androidRequest.setAppKey(APPKEY);
        androidRequest.setTarget("account");
        androidRequest.setTargetValue(account);
        androidRequest.setTitle(title);
        androidRequest.setSummary(content);
        return client.getAcsResponse(androidRequest);
    }

    /**
     * 推送消息给android
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/message-to-android.html
     */
    public static String pushMessageToAndroid_toAll(String accounts, String content) {
        PushMessageToAndroidRequest androidRequest = new PushMessageToAndroidRequest();
        androidRequest.setAppKey(APPKEY);
        androidRequest.setTarget("account");
        androidRequest.setTargetValue(accounts);
        androidRequest.setMessage(content);
        PushMessageToAndroidResponse pushMessageToAndroidResponse = null;
        try {
            pushMessageToAndroidResponse = client.getAcsResponse(androidRequest);
            return pushMessageToAndroidResponse.getResponseId();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 推送通知给iOS
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/notice-to-ios.html
     */
    /*public static void testPushNoticeToIOS_toAll() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss");
        PushNoticeToiOSRequest iOSRequest = new PushNoticeToiOSRequest();
        iOSRequest.setAppKey(appKey);
        // iOS的通知是通过APNS中心来发送的，需要填写对应的环境信息. DEV:表示开发环境, PRODUCT: 表示生产环境
        iOSRequest.setEnv("DEV");
        iOSRequest.setTarget("all");
        iOSRequest.setTargetValue("all");
        iOSRequest.setSummary(dateFormat.format(new Date()));
        iOSRequest.setiOSExtParameters("{\"k1\":\"v1\",\"k2\":\"v2\"}");
        iOSRequest.setExt("{\"sound\":\"default\", \"badge\":\"42\"}");

        PushNoticeToiOSResponse pushNoticeToiOSResponse = client.getAcsResponse(iOSRequest);
        System.out.printf("RequestId: %s, ResponseId: %s\n",
                pushNoticeToiOSResponse.getRequestId(), pushNoticeToiOSResponse.getResponseId());
    }*/

    /**
     * 推送消息给iOS
     * <p>
     * 参见文档 https://help.aliyun.com/document_detail/mobilepush/api-reference/message-to-ios.html
     */
    /*public void testPushMessageToIOS_toAll() throws Exception {
        PushMessageToiOSRequest iOSRequest = new PushMessageToiOSRequest();
        iOSRequest.setAppKey(appKey);
        iOSRequest.setTarget("all");
        iOSRequest.setTargetValue("all");
        iOSRequest.setMessage("message");
        iOSRequest.setSummary("summary");

        PushMessageToiOSResponse pushMessageToiOSResponse = client.getAcsResponse(iOSRequest);
        System.out.printf("RequestId: %s, ResponseId: %s\n",
                pushMessageToiOSResponse.getRequestId(), pushMessageToiOSResponse.getResponseId());
    }*/
}
