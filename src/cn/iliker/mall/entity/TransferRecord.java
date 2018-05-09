package cn.iliker.mall.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TransferRecord entity. @author MyEclipse Persistence Tools
 */

public class TransferRecord implements java.io.Serializable {

    // Fields

    private String batchNo;
    private String payDate;
    private Double batchFee;
    private Integer batchNum;
    private String detailData;
    private Set<TransferDetail> transferDetails = new HashSet<>();

    // Constructors

    /**
     * default constructor
     */
    public TransferRecord() {
    }

    /**
     * minimal constructor
     */
    public TransferRecord(String payDate, Double batchFee, Integer batchNum,
                          String detailData) {
        this.payDate = payDate;
        this.batchFee = batchFee;
        this.batchNum = batchNum;
        this.detailData = detailData;
    }

    /**
     * full constructor
     */
    public TransferRecord(String payDate, Double batchFee, Integer batchNum,
                          String detailData, Set<TransferDetail> transferDetails) {
        this.payDate = payDate;
        this.batchFee = batchFee;
        this.batchNum = batchNum;
        this.detailData = detailData;
        this.transferDetails = transferDetails;
    }

    // Property accessors

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Double getBatchFee() {
        return this.batchFee;
    }

    public void setBatchFee(Double batchFee) {
        this.batchFee = batchFee;
    }

    public Integer getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(Integer batchNum) {
        this.batchNum = batchNum;
    }

    public String getDetailData() {
        return this.detailData;
    }

    public void setDetailData(String detailData) {
        this.detailData = detailData;
    }

    public Set<TransferDetail> getTransferDetails() {
        return this.transferDetails;
    }

    public void setTransferDetails(Set<TransferDetail> transferDetails) {
        this.transferDetails = transferDetails;
    }
}