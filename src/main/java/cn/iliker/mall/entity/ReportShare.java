package cn.iliker.mall.entity;

import java.sql.Timestamp;
import java.util.Arrays;

import cn.iliker.mall.entity.stateattr.ReportInfo;

//举报分享类
public class ReportShare {
    private Integer reportId;
    private Timestamp reportTime;//举报时间
    private String reportInfo;//举报原因
    private Userinfo userinfo;//举报人
    private Share share;//举报对应的分享
    private String handleResult;//后台处理结果
    private Timestamp handleTime;//后台处理时间
    private int mark;
    private int[] type = new int[]{0, 1, 2, 3, 4};

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public Timestamp getReportTime() {
        return reportTime;
    }

    public void setReportTime(Timestamp reportTime) {
        this.reportTime = reportTime;
    }

    public void setReportInfo(String reportInfo) {
        this.reportInfo = reportInfo;
    }

    public String getReportInfo() {
        return reportInfo;
    }

    public Userinfo getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(Userinfo userinfo) {
        this.userinfo = userinfo;
    }

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Timestamp getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Timestamp handleTime) {
        this.handleTime = handleTime;
    }

    public void setMark(int mark) {
        if (Arrays.binarySearch(type, mark) > -1) {
            reportInfo = ReportInfo.reportShareType(mark);
        } else {
            reportInfo = ReportInfo.reportShareType(0);
        }
    }
}
