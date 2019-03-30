package com.refugees.db.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScreeningQuestion {
	private final static List<Integer> TEXT_QUESTIONS=new ArrayList<>();
	private final static List<Integer> NUMERIC_QUESTIONS=new ArrayList<>();
	static {
		TEXT_QUESTIONS.add(5);
		TEXT_QUESTIONS.add(6);
		TEXT_QUESTIONS.add(9);
		TEXT_QUESTIONS.add(11);
	}
	private int questionId;
	private int categoryId;
	private String questionTextKey;
	
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
		return TEXT_QUESTIONS.contains(questionId);
	}
	public boolean isNumeric()
	{
		return NUMERIC_QUESTIONS.contains(questionId);
	}
	public boolean isYesNo()
	{
		return !(isNumeric()||isText());
	}
}
