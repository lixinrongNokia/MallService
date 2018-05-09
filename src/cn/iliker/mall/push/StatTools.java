package cn.iliker.mall.push;

import com.aliyuncs.push.model.v20150827.*;
import com.aliyuncs.utils.ParameterHelper;

import java.util.Date;
import java.util.List;

/**
 * 推送统计分组测试
 */
public final class StatTools extends PushConfig {

    /**
     * 查询某次推送统计
     */
    public static List<QueryPushStatResponse.PushStat> testQueryPushStat() throws Exception {
        QueryPushStatRequest request = new QueryPushStatRequest();
        request.setAppKey(APPKEY);
        request.setMessageId("_MessageId_");// 此处填写推送成功后返回的ResponseId
        return client.getAcsResponse(request).getPushStats();
    }

    /**
     * 查询多次推送统计
     */
    public static List<QueryAppPushStatResponse.AppPushStat> testQueryAppPushStat() throws Exception {
        QueryAppPushStatRequest request = new QueryAppPushStatRequest();
        request.setAppKey(APPKEY);
        request.setGranularity("DAY");
        final Date startDate = new Date(System.currentTimeMillis() - 3 * 24 * 3600 * 1000);
        final String startTime = ParameterHelper.getISO8601Time(startDate);
        final Date endDate = new Date(System.currentTimeMillis());
        final String endTime = ParameterHelper.getISO8601Time(endDate);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        return client.getAcsResponse(request).getAppPushStats();
    }

    /**
     * 查询设备统计
     */
    public static List<QueryDeviceStatResponse.AppDeviceStat> testQueryDeviceStat() throws Exception {
        QueryDeviceStatRequest request = new QueryDeviceStatRequest();
        request.setAppKey(APPKEY);
        request.setQueryType("Total");
        request.setDeviceType("All");
        final Date startDate = new Date(System.currentTimeMillis() - 7 * 24 * 3600 * 1000);
        final String startTime = ParameterHelper.getISO8601Time(startDate);
        final Date endDate = new Date(System.currentTimeMillis());
        final String endTime = ParameterHelper.getISO8601Time(endDate);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        return client.getAcsResponse(request).getAppDeviceStats();
    }

    /**
     * 查询去重设备统计
     */
    public static List<QueryUniqueDeviceStatResponse.AppDeviceStat> testQueryUniqueDeviceStat() throws Exception {
        QueryUniqueDeviceStatRequest request = new QueryUniqueDeviceStatRequest();
        request.setAppKey(APPKEY);
        final Date startDate = new Date(System.currentTimeMillis() - 24 * 3600 * 1000);
        final String startTime = ParameterHelper.getISO8601Time(startDate);
        final Date endDate = new Date(System.currentTimeMillis());
        final String endTime = ParameterHelper.getISO8601Time(endDate);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        return client.getAcsResponse(request).getAppDeviceStats();
    }

}
