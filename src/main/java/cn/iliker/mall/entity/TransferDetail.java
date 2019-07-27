package cn.iliker.mall.entity;

/**
 * TransferDetail entity. @author MyEclipse Persistence Tools
 */

public class TransferDetail implements java.io.Serializable {

    // Fields

    private Integer transferDetailId;
    private Transfer transfer;
    private TransferRecord transferRecord;
    private String serialNo;
    private Boolean stateTag;
    private String remark;

    // Constructors

    /**
     * default constructor
     */
    public TransferDetail() {
    }

    /**
     * full constructor
     */
    public TransferDetail(Transfer transfer, TransferRecord transferRecord,
                          String serialNo, String remark) {
        this.transfer = transfer;
        this.transferRecord = transferRecord;
        this.serialNo = serialNo;
        this.remark = remark;
    }

    // Property accessors

    public Integer getTransferDetailId() {
        return this.transferDetailId;
    }

    public void setTransferDetailId(Integer transferDetailId) {
        this.transferDetailId = transferDetailId;
    }

    public Transfer getTransfer() {
        return this.transfer;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public TransferRecord getTransferRecord() {
        return this.transferRecord;
    }

    public void setTransferRecord(TransferRecord transferRecord) {
        this.transferRecord = transferRecord;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Boolean getStateTag() {
        return stateTag;
    }

    public void setStateTag(Boolean stateTag) {
        this.stateTag = stateTag;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}