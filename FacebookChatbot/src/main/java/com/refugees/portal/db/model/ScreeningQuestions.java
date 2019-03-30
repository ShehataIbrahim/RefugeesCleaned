package com.refugees.portal.db.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "screening_q")
public class ScreeningQuestions {
	@Id
	@Column(name = "screening_q_id")
	private int questionId;
	@Column(name = "screening_q_text")
	private String questionKey;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestionKey() {
		return questionKey;
	}

	public void setQuestionKey(String questionKey) {
		this.questionKey = questionKey;
	}

}
