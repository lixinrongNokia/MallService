package cn.iliker.mall.entity;

/**
 * CourierCompany entity. @author MyEclipse Persistence Tools
 */

public class CourierCompany implements java.io.Serializable {

	// Fields

	private Integer id;
	private String companyCode;
	private String companyName;
	// Constructors

	/** default constructor */
	public CourierCompany() {
	}

	/** minimal constructor */
	public CourierCompany(String companyCode, String companyName) {
		this.companyCode = companyCode;
		this.companyName = companyName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

}