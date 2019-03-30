package com.refugees.db.model;

import java.util.Date;

public class InterviewAnswer {
	private int categoryId;
	private int interviewId;
	private int answerId;
	private int categoryVersion;
	private String answerItem;
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

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}

	public int getCategoryVersion() {
		return categoryVersion;
	}

	public void setCategoryVersion(int categoryVersion) {
		this.categoryVersion = categoryVersion;
	}

	public String getAnswerItem() {
		return answerItem;
	}

	public void setAnswerItem(String answerItem) {
		this.answerItem = answerItem;
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
