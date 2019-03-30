package com.refugees.portal.db.health.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mst_interview", schema = "public", catalog = "health")
@IdClass(InterviewPK.class)
public class Interview {
    private int interviewCategoryId;
    private int interviewCategoryVersion;
    private int interviewId;
    private String interviewItem;
    private String answerType;
    private Timestamp createDate;
    private Timestamp updateDate;

    @Id
    @Column(name = "interview_category_id")
    public int getInterviewCategoryId() {
        return interviewCategoryId;
    }

    public void setInterviewCategoryId(int interviewCategoryId) {
        this.interviewCategoryId = interviewCategoryId;
    }

    @Id
    @Column(name = "interview_category_version")
    public int getInterviewCategoryVersion() {
        return interviewCategoryVersion;
    }

    public void setInterviewCategoryVersion(int interviewCategoryVersion) {
        this.interviewCategoryVersion = interviewCategoryVersion;
    }

    @Id
    @Column(name = "interview_id")
    public int getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(int interviewId) {
        this.interviewId = interviewId;
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
    @Column(name = "create_date")
    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "update_date")
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
        Interview interview = (Interview) o;
        return interviewCategoryId == interview.interviewCategoryId &&
                interviewCategoryVersion == interview.interviewCategoryVersion &&
                interviewId == interview.interviewId &&
                Objects.equals(interviewItem, interview.interviewItem) &&
                Objects.equals(answerType, interview.answerType) &&
                Objects.equals(createDate, interview.createDate) &&
                Objects.equals(updateDate, interview.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interviewCategoryId, interviewCategoryVersion, interviewId, interviewItem, answerType, createDate, updateDate);
    }
}
