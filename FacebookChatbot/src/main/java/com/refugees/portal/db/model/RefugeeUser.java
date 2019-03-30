package com.refugees.portal.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="nammin_user")
public class RefugeeUser {
	@Id
	@Column(name="nammin_id")
	private Integer id;
	@Column(name="nammin_name")
	private String name;
	@Column(name="facebook_user_id")
	private String facebookUserId;
	@Column(name="facebook_info")
	private String facebookInfo;
	@Column(name="mail_address")
	private String mailAddress;
	@Column(name="pass_hash")
	private String passwordHash;
	@Column(name="pass_salt")
	private String passwordSalt;
	@Column(name="translate_lang_code")
	private String translateLangCode;
	@Column(name="last_screening_time")
	private Date lastScreeningTime;
	@Column(name="last_screening_request_time")
	private Date lastScreeningRequestTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFacebookUserId() {
		return facebookUserId;
	}
	public void setFacebookUserId(String facebookUserId) {
		this.facebookUserId = facebookUserId;
	}
	public String getFacebookInfo() {
		return facebookInfo;
	}
	public void setFacebookInfo(String facebookInfo) {
		this.facebookInfo = facebookInfo;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	public String getTranslateLangCode() {
		return translateLangCode;
	}
	public void setTranslateLangCode(String translateLangCode) {
		this.translateLangCode = translateLangCode;
	}
	public Date getLastScreeningTime() {
		return lastScreeningTime;
	}
	public void setLastScreeningTime(Date lastScreeningTime) {
		this.lastScreeningTime = lastScreeningTime;
	}
	public Date getLastScreeningRequestTime() {
		return lastScreeningRequestTime;
	}
	public void setLastScreeningRequestTime(Date lastScreeningRequestTime) {
		this.lastScreeningRequestTime = lastScreeningRequestTime;
	}
	@Override
	public String toString() {
		return "RefugeeUser [id=" + id + ", name=" + name + ", facebookUserId=" + facebookUserId + ", facebookInfo="
				+ facebookInfo + ", mailAddress=" + mailAddress + ", passwordHash=" + passwordHash + ", passwordSalt="
				+ passwordSalt + ", translateLangCode=" + translateLangCode + ", lastScreeningTime=" + lastScreeningTime
				+ ", lastScreeningRequestTime=" + lastScreeningRequestTime + "]";
	}
	

}
