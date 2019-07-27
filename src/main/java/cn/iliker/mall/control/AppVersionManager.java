package cn.iliker.mall.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.iliker.mall.entity.ApkVersion;
import cn.iliker.mall.service.IApkVerSionSvc;
import cn.iliker.mall.utils.UrlTools;

public class AppVersionManager extends ActionSupport implements
        ModelDriven<ApkVersion> {
    private IApkVerSionSvc apksvc;
    private File data;
    private String dataFileName;
    private ApkVersion apkversion;

    public void setApksvc(IApkVerSionSvc apksvc) {
        this.apksvc = apksvc;
    }

    public File getData() {
        return data;
    }

    public void setData(File data) {
        this.data = data;
    }

    public String getDataFileName() {
        return dataFileName;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }


    public String uploadFile() {
        if (data != null) {
            if (apksvc.updateApkVersion(apkversion)) {
                File savefile = new File(UrlTools.APPLOAD, dataFileName);
                if (savefile.exists()) {
                    savefile.delete();
                }
                try {
                    FileUtils.copyFile(data, savefile);
                    ActionContext.getContext().put("message", "上传成功！路径:" + UrlTools.APPLOAD.getAbsolutePath());
                    data = null;
                    dataFileName = null;
                    return SUCCESS;
                } catch (IOException e) {
                }
            }
        }
        ActionContext.getContext().put("error", "上传失败");
        return ERROR;
    }

    private JSONObject msg = new JSONObject();

    public JSONObject getMsg() {
        return msg;
    }

    public String queryVersion() throws IOException {
        msg.clear();
        ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
        ApkVersion apkversion = apksvc.find();
        msg.put("id", apkversion.getId());
        msg.put("versionCode", apkversion.getVersionCode());
        msg.put("versionName", apkversion.getVersionName());
        msg.put("content", apkversion.getContent());
        msg.put("appName", apkversion.getAppName());
        msg.put("url", apkversion.getUrl());
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(msg.toJSONString());
        writer.flush();
        writer.close();
        return NONE;
    }

    @Override
    public ApkVersion getModel() {
        if (apkversion == null) {
            apkversion = new ApkVersion();
        }
        return apkversion;
    }

}
