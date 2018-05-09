package cn.iliker.mall.entity;

/**
 * Logistics entity. @author MyEclipse Persistence Tools
 */

public class Logistics implements java.io.Serializable {

    // Fields

    private Integer id;
    private TOrder TOrder;
    private String logisticscode;
    private String companyCode;

    // Constructors

    /**
     * default constructor
     */
    public Logistics() {
    }

    /**
     * full constructor
     */
    public Logistics(String companyCode, TOrder TOrder,
                     String logisticscode) {
        this.companyCode = companyCode;
        this.TOrder = TOrder;
        this.logisticscode = logisticscode;
    }

    // Property accessors


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public cn.iliker.mall.entity.TOrder getTOrder() {
        return TOrder;
    }

    public void setTOrder(cn.iliker.mall.entity.TOrder TOrder) {
        this.TOrder = TOrder;
    }

    public String getLogisticscode() {
        return logisticscode;
    }

    public void setLogisticscode(String logisticscode) {
        this.logisticscode = logisticscode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
}