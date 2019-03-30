package com.refugees.portal.db.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="inprogress_facebook_users")
public class FacebookUser {
	@Id
	@Column(name="sender_id")
	private String senderId;
	@Column(name="user_name")
	private String userName;
	@Column(name="scanning_date")
	private Date scanningDate;
	@Column(name="user_id")
	private int userId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getSenderId() {
		return senderId;
	}
	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getScanningDate() {
		return scanningDate;
	}
	public void setScanningDate(Date scanningDate) {
		this.scanningDate = scanningDate;
	}
	

}
