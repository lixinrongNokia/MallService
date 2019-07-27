package cn.iliker.mall.push;

import java.util.List;

import com.aliyuncs.push.model.v20150827.BindTagRequest;
import com.aliyuncs.push.model.v20150827.BindTagResponse;
import com.aliyuncs.push.model.v20150827.ListTagsRequest;
import com.aliyuncs.push.model.v20150827.ListTagsResponse;
import com.aliyuncs.push.model.v20150827.QueryTagsRequest;
import com.aliyuncs.push.model.v20150827.QueryTagsResponse;
import com.aliyuncs.push.model.v20150827.UnbindTagRequest;
import com.aliyuncs.push.model.v20150827.UnbindTagResponse;

/**
 * 标签操作Demo
 */
public class TagTools extends PushConfig {

    public static List<ListTagsResponse.TagInfo> ListTags() throws Exception {
        ListTagsRequest request = new ListTagsRequest();
        request.setAppKey(APPKEY);
        ListTagsResponse response = client.getAcsResponse(request);
        return response.getTagInfos();
    }

    //查询设备绑定的标签
    public static List<QueryTagsResponse.TagInfo> QueryTag(String deviceIds) throws Exception {
        QueryTagsRequest request = new QueryTagsRequest();
        request.setAppKey(APPKEY);
        request.setClientKey(deviceIds);
        request.setKeyType(1);// 1 : device 2 : account 3 : alias
        QueryTagsResponse response = client.getAcsResponse(request);
        return response.getTagInfos();
    }

    //绑定标签
    public static BindTagResponse BindTag(String deviceIds) throws Exception {
        BindTagRequest request = new BindTagRequest();
        request.setAppKey(APPKEY);
        request.setClientKey(deviceIds);
        request.setKeyType(1);// 1 : device 2 : account 3 : alias
        request.setTagName("tag1");
        return client.getAcsResponse(request);
    }

    //解绑标签
    public static UnbindTagResponse UnbindTag(String deviceIds) throws Exception {
        UnbindTagRequest request = new UnbindTagRequest();
        request.setAppKey(APPKEY);
        request.setClientKey(deviceIds);
        request.setKeyType(1);// 1 : device 2 : account 3 : alias
        request.setTagName("tag1");
        return client.getAcsResponse(request);
    }

}
