package com.refugees.portal.db.health.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "interview_questions_view", schema = "public", catalog = "health")
public class InterviewQuestionsView {
    private Long id;
    private Integer interviewCategoryId;
    private Integer interviewCategoryVersion;
    private Integer interviewId;
    private Integer answerId;
    private String interviewItem;
    private String answerType;
    private String answerItem;

    @Id
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "interview_category_id")
    public Integer getInterviewCategoryId() {
        return interviewCategoryId;
    }

    public void setInterviewCategoryId(Integer interviewCategoryId) {
        this.interviewCategoryId = interviewCategoryId;
    }

    @Basic
    @Column(name = "interview_category_version")
    public Integer getInterviewCategoryVersion() {
        return interviewCategoryVersion;
    }

    public void setInterviewCategoryVersion(Integer interviewCategoryVersion) {
        this.interviewCategoryVersion = interviewCategoryVersion;
    }

    @Basic
    @Column(name = "interview_id")
    public Integer getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Integer interviewId) {
        this.interviewId = interviewId;
    }

    @Basic
    @Column(name = "answer_id")
    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    @Basic
    @Column(name = "interview_item")
    public String getInterviewItem() {
        return interviewItem;
    }

    public void setInterviewItem(String interviewItem) {
        this.interviewItem = interviewItem;
    }

    @Basic
    @Column(name = "answer_type")
    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    @Basic
    @Column(name = "answer_item")
    public String getAnswerItem() {
        return answerItem;
    }

    public void setAnswerItem(String answerItem) {
        this.answerItem = answerItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewQuestionsView that = (InterviewQuestionsView) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(interviewCategoryId, that.interviewCategoryId) &&
                Objects.equals(interviewCategoryVersion, that.interviewCategoryVersion) &&
                Objects.equals(interviewId, that.interviewId) &&
                Objects.equals(answerId, that.answerId) &&
                Objects.equals(interviewItem, that.interviewItem) &&
                Objects.equals(answerType, that.answerType) &&
                Objects.equals(answerItem, that.answerItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, interviewCategoryId, interviewCategoryVersion, interviewId, answerId, interviewItem, answerType, answerItem);
    }
    public String objectId()
    {
        return String.valueOf(interviewCategoryId)+"_"+String.valueOf(interviewCategoryVersion)+"_"+String.valueOf(interviewId);
    }
}
