package com.refugees.portal.db.health.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "mst_interview_category", schema = "public", catalog = "health")
@IdClass(InterviewCategoryPK.class)
public class InterviewCategory {
    private int interviewCategoryId;
    private int interviewCategoryVersion;
    private String interviewCategoryName;
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

    @Basic
    @Column(name = "interview_category_name")
    public String getInterviewCategoryName() {
        return interviewCategoryName;
    }

    public void setInterviewCategoryName(String interviewCategoryName) {
        this.interviewCategoryName = interviewCategoryName;
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
        InterviewCategory that = (InterviewCategory) o;
        return interviewCategoryId == that.interviewCategoryId &&
                interviewCategoryVersion == that.interviewCategoryVersion &&
                Objects.equals(interviewCategoryName, that.interviewCategoryName) &&
                Objects.equals(createDate, that.createDate) &&
                Objects.equals(updateDate, that.updateDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interviewCategoryId, interviewCategoryVersion, interviewCategoryName, createDate, updateDate);
    }
}
