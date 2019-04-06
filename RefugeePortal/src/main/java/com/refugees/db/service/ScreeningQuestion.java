package com.refugees.db.service;

import com.refugees.consolidate.model.AllowedAnswer;
import com.refugees.db.model.AnswerTypesEnum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ScreeningQuestion {
	private int questionId;
	private int categoryId;
	private String questionTextKey;
	private String qId;
	private AnswerTypesEnum type;
	private List<AllowedAnswer> allowedAnswers;
	private String answerValue;

	public String getAnswerValue() {
		return answerValue;
	}

	public void setAnswerValue(String answerValue) {
		this.answerValue = answerValue;
	}

	public String getqId() {
		return qId;
	}

	public void setqId(String qId) {
		this.qId = qId;
	}

	public AnswerTypesEnum getType() {
		return type;
	}

	public void setType(AnswerTypesEnum type) {
		this.type = type;
	}

	public List<AllowedAnswer> getAllowedAnswers() {
		return allowedAnswers;
	}

	public void setAllowedAnswers(List<AllowedAnswer> allowedAnswers) {
		this.allowedAnswers = allowedAnswers;
	}

	public ScreeningQuestion() {
		super();
	}
	public ScreeningQuestion(ResultSet r) throws SQLException {
		questionId=r.getInt("screening_q_id");
		categoryId=r.getInt("category_id");
		questionTextKey=r.getString("screening_q_text");
	}
	public int getQuestionId() {
		return questionId;
	}
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getQuestionTextKey() {
		return questionTextKey;
	}
	public void setQuestionTextKey(String questionTextKey) {
		this.questionTextKey = questionTextKey;
	}
	public boolean isText()
	{
		return type==AnswerTypesEnum.FREE;
	}
	public boolean isYesNo()
	{
		return type==AnswerTypesEnum.LIST;
	}
}
