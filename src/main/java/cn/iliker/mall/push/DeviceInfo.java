package cn.iliker.mall.push;

import com.aliyuncs.push.model.v20150827.GetDeviceInfosRequest;
import com.aliyuncs.push.model.v20150827.GetDeviceInfosResponse;

public class DeviceInfo extends PushConfig {
    public void testGetDeviceInfos() throws Exception {
        GetDeviceInfosRequest getDeviceInfosRequest = new GetDeviceInfosRequest();
        getDeviceInfosRequest.setAppKey(APPKEY);
        getDeviceInfosRequest.setDevices("_YOUR_DEVICE_IDS_HERE");

        GetDeviceInfosResponse getDeviceInfosResponse = client.getAcsResponse(getDeviceInfosRequest);
        for (GetDeviceInfosResponse.DeviceInfo deviceInfo : getDeviceInfosResponse.getDeviceInfos()) {
            System.out.printf("deviceId: %s, isOnline: %s\n", deviceInfo.getDeviceId(), deviceInfo.getIsOnline());
        }
    }
}
