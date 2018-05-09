package cn.iliker.mall.service.impl;

import cn.iliker.mall.dao.IReportObjDAO;
import cn.iliker.mall.entity.ReportShare;
import cn.iliker.mall.service.IReportObjSvc;
import cn.iliker.mall.service.IYoutuService;
import cn.iliker.mall.utils.NetSteamUtil;
import cn.iliker.mall.utils.ReadStreamUtil;
import cn.iliker.mall.youtu.Base64Util;
import cn.iliker.mall.youtu.YoutuSign;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public class ReportObjSvcImpl implements IReportObjSvc, IYoutuService {
    private IReportObjDAO reportObjDAO;

    public void setReportObjDAO(IReportObjDAO reportObjDAO) {
        this.reportObjDAO = reportObjDAO;
    }

    @Override
    public void addReportShare(ReportShare reportShare) throws Exception {
        reportObjDAO.addReportShare(reportShare);
    }

    @Override
    public void updateReportShare(ReportShare reportShare) throws Exception {
        reportObjDAO.updateReportShare(reportShare);
    }

    @Override
    public List<ReportShare> queryMyReportForShare(int uid, int startPage, int pageSize) {
        return reportObjDAO.queryMyReportForShare(uid, startPage, pageSize);
    }

    @Override
    public void imageRecognition(List<File> files) throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("D:\\c.jpg"));
        String code = Base64Util.encode(ReadStreamUtil.readStream(inputStream));
        StringBuffer mySign = new StringBuffer("");
        int back = YoutuSign.appSign("10105687", "AKID0DG8GF0efaLuHSjissgIiw0GMGE0RMJV", "SltpaEhy3zjy79zd5smaQ8fGk9PTLFxC",
                System.currentTimeMillis() / 1000 + 2592000,
                "603239210", mySign);
        if (back == 0) {
            HttpPost httpPost = new HttpPost("http://api.youtu.qq.com/youtu/imageapi/imageporn");
            httpPost.setHeader("Host", "api.youtu.qq.com");
            httpPost.setHeader("Content-Type", "text/json");
            httpPost.setHeader("Authorization", mySign.toString());
            StringEntity entity = new StringEntity("{\"app_id\":\"10105687\",\"image\":\"" + code + "\"}");
            httpPost.setEntity(entity);
            try (CloseableHttpClient client = HttpClients.createDefault()) {
                CloseableHttpResponse response = client.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                    JSONObject jsonObject = JSON.parseObject(NetSteamUtil.readInputSteam(response.getEntity().getContent()));
                    if (0 == jsonObject.getIntValue("errorcode")) {
                        JSONObject tag = jsonObject.getJSONArray("tags").getJSONObject(2);

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
