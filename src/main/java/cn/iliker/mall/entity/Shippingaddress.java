package cn.iliker.mall.entity;

/**
 * Shippingaddress entity. @author MyEclipse Persistence Tools
 */

public class Shippingaddress implements java.io.Serializable {

	// Fields

	private Integer id;
	private Userinfo userinfo;
	private String consigneeName;
	private String phone;
	private String address;

	// Constructors

	/** default constructor */
	public Shippingaddress() {
	}

	/** minimal constructor */
	public Shippingaddress(String consigneeName, String phone, String address) {
		this.consigneeName = consigneeName;
		this.phone = phone;
		this.address = address;
	}

	/** full constructor */
	public Shippingaddress(Userinfo userinfo, String consigneeName,
			String phone, String address) {
		this.userinfo = userinfo;
		this.consigneeName = consigneeName;
		this.phone = phone;
		this.address = address;
	}

	// Property accessors


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Userinfo getUserinfo() {
		return userinfo;
	}

	public void setUserinfo(Userinfo userinfo) {
		this.userinfo = userinfo;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}