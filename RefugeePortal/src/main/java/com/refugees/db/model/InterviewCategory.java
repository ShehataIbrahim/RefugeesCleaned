package com.refugees.db.model;

import java.util.Date;

public class InterviewCategory {
	private int categoryId;
	private int categoryVersion;
	private String categoryName;
	private Date creationDate;
	private Date updateDate;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getCategoryVersion() {
		return categoryVersion;
	}

	public void setCategoryVersion(int categoryVersion) {
		this.categoryVersion = categoryVersion;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
