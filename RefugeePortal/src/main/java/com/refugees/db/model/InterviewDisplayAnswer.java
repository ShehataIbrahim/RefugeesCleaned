package com.refugees.db.model;

import java.util.List;

public class InterviewDisplayAnswer {
	private int interviewId;
	private int answerId;
	private String answerItem;
	private String interviewItem;
	private String answerType;
	private Integer categoryId;
	private List<String> allowedAnswers;
	private AnswerTypesEnum type;
	public AnswerTypesEnum getType() {
		return type;
	}
	public List<String> getAllowedAnswers() {
		return allowedAnswers;
	}
	public void setAllowedAnswers(List<String> allowedAnswers) {
		this.allowedAnswers = allowedAnswers;
	}
	public Integer getCategoryId() {
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
	private int categoryVersion;
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
	public String getAnswerItem() {
		return answerItem;
	}
	public void setAnswerItem(String answerItem) {
		this.answerItem = answerItem;
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
		try {
		if(answerType!=null && !answerType.trim().isEmpty())
		type=AnswerTypesEnum.valueOf(answerType.trim());
		}catch(Exception e)
		{
			
		}
	}
	@Override
	public int hashCode() {
		
		return (String.valueOf(categoryId)+"_"+String.valueOf(categoryVersion)+"_"+String.valueOf(interviewId)).hashCode();
	}
	
}
