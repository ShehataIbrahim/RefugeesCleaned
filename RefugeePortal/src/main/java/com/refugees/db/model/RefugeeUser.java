package com.refugees.db.model;

import java.util.Date;

import net.hitachifbbot.DB;
import net.hitachifbbot.NamminUser;
import net.hitachifbbot.model.NamminUserData;

public class RefugeeUser extends NamminUserData {
	public NamminUser dbUserData;
	//@Id
	//@Column(name = "nammin_id")
	private Integer userId	;

	//@Column(name = "nammin_name")
	private String userName	;

	//@Column(name = "facebook_user_id")
	private String facebookId	;

	//@Column(name = "facebook_info")
	private String facebookInfo	;

	//@Column(name = "mail_address")
	private String email	;

	//@Column(name = "pass_hash")
	private String password	;

	//@Column(name = "pass_salt")
	private String passwordSalt	;

	//@Column(name = "translate_lang_code")
	private String defaultLanguage	;

	//@Column(name = "last_screening_time")
	private Date lastScreeningDate	;

	//@Column(name = "last_screening_request_time")
	private Date lastScreeningRequest;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getFacebookInfo() {
		return facebookInfo;
	}

	public void setFacebookInfo(String facebookInfo) {
		this.facebookInfo = facebookInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSalt() {
		return passwordSalt;
	}

	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public Date getLastScreeningDate() {
		return lastScreeningDate;
	}

	public void setLastScreeningDate(Date lastScreeningDate) {
		this.lastScreeningDate = lastScreeningDate;
	}

	public Date getLastScreeningRequest() {
		return lastScreeningRequest;
	}

	public void setLastScreeningRequest(Date lastScreeningRequest) {
		this.lastScreeningRequest = lastScreeningRequest;
	}
	public NamminUser getDBUserData() {
		if(dbUserData==null)
			dbUserData=new NamminUser(userId, userName, facebookInfo, email, password, passwordSalt, defaultLanguage, lastScreeningDate, lastScreeningRequest);
		return dbUserData;
	}

	public static RefugeeUser build(NamminUserData u) {
		RefugeeUser user=new RefugeeUser();
		user.setUserId(u.getDbUserData().namminID);
		user.setDefaultLanguage(u.getDbUserData().translateLangCode);
		user.setEmail(u.getDbUserData().mailAddress);
		user.setFacebookId(u.getDbUserData().facebookInfo);
		user.setLastScreeningDate(u.getDbUserData().lastScreeningTime);
		user.setLastScreeningRequest(u.getDbUserData().lastScreeningRequestTime);
		user.setPassword(u.getDbUserData().passHash);
		user.setPasswordSalt(u.getDbUserData().passSalt);
		user.setUserName(u.getDbUserData().namminName);
		return user;
	}
	
}
