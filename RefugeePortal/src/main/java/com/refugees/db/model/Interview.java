package com.refugees.db.model;

import java.util.Date;

public class Interview {
	private int categoryId;
	private int interviewId;
	private int categoryVersion;
	private String interviewItem;
	private String answerType;
	private Date creationDate;
	private Date updateDate;

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(int interviewId) {
		this.interviewId = interviewId;
	}

	public int getCategoryVersion() {
		return categoryVersion;
	}

	public void setCategoryVersion(int categoryVersion) {
		this.categoryVersion = categoryVersion;
	}

	public String getInterviewItem() {
		return interviewItem;
	}

	public void setInterviewItem(String interviewItem) {
		this.interviewItem = interviewItem;
	}

	public String getAnswerType() {
		return answerType;
	}

	public void setAnswerType(String answerType) {
		this.answerType = answerType;
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
