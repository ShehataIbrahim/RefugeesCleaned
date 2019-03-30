package com.refugees.portal.db.health.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mst_answer", schema = "public", catalog = "health")
@IdClass(InterviewAnswerPK.class)
public class InterviewAnswer {
    private int interviewCategoryId;
    private int interviewCategoryVersion;
    private int interviewId;
    private int answerId;
    private String answerItem;
    private Timestamp createDate;
    private Timestamp updateDate;

    @Id
    @Column(name = "interview_category_id", nullable = false)
    public int getInterviewCategoryId() {
        return interviewCategoryId;
    }

    public void setInterviewCategoryId(int interviewCategoryId) {
        this.interviewCategoryId = interviewCategoryId;
    }

    @Id
    @Column(name = "interview_category_version", nullable = false)
    public int getInterviewCategoryVersion() {
        return interviewCategoryVersion;
    }

    public void setInterviewCategoryVersion(int interviewCategoryVersion) {
        this.interviewCategoryVersion = interviewCategoryVersion;
    }

    @Id
    @Column(name = "interview_id", nullable = false)
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
    }

    @Id
    @Column(name = "answer_id", nullable = false)
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    @Basic
    @Column(name = "answer_item", nullable = true, length = 1000)
    public String getAnswerItem() {
        return answerItem;
    }

    public void setAnswerItem(String answerItem) {
        this.answerItem = answerItem;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "update_date", nullable = true)
    public Timestamp getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Timestamp updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterviewAnswer that = (InterviewAnswer) o;
        return interviewCategoryId == that.interviewCategoryId &&
                interviewCategoryVersion == that.interviewCategoryVersion &&
                interviewId == that.interviewId &&
                answerId == that.answerId &&
                Objects.equals(answerItem, that.answerItem) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interviewCategoryId, interviewCategoryVersion, interviewId, answerId, answerItem, createDate, updateDate);
    }
    public String interviewIdentifier()
    {
        return (interviewCategoryId+"_"+interviewCategoryVersion+"_"+interviewId);
    }
}
