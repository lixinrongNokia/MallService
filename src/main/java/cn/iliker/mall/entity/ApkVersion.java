package cn.iliker.mall.entity;

/**
 * ApkVersion entity. @author MyEclipse Persistence Tools
 */

public class ApkVersion implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer versionCode;
	private String versionName;
	private String content;
	private String appName;
	private String url;

	// Constructors

	/** default constructor */
	public ApkVersion() {
	}

	/** full constructor */
	public ApkVersion(Integer versionCode, String versionName, String content,
			String appName, String url) {
		this.versionCode = versionCode;
		this.versionName = versionName;
		this.content = content;
		this.appName = appName;
		this.url = url;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersionCode() {
		return this.versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return this.versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}